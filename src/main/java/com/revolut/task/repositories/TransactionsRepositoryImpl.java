package com.revolut.task.repositories;

import com.google.inject.Inject;
import com.revolut.task.db.DBManager;
import com.revolut.task.di.InjectorProvider;
import com.revolut.task.exceptions.AccountNotFoundException;
import com.revolut.task.exceptions.CurrencyNotFoundException;
import com.revolut.task.exceptions.NegativeBalanceException;
import com.revolut.task.models.tables.pojos.Account;
import com.revolut.task.models.tables.pojos.Transactions;
import com.revolut.task.models.tables.records.AccountRecord;
import com.revolut.task.models.tables.records.CurrencyRecord;
import com.revolut.task.service.LockService;
import org.jooq.Configuration;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.revolut.task.models.Tables.*;

public class TransactionsRepositoryImpl implements TransactionsRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionsRepositoryImpl.class);

    private final DBManager dbManager;
    private final AccountsRepository accountsRepository;

    @Inject
    public TransactionsRepositoryImpl() {
        this.dbManager = InjectorProvider.provide().getInstance(DBManager.class);
        this.accountsRepository = InjectorProvider.provide().getInstance(AccountsRepository.class);
    }

    @Override
    public List<Transactions> fetchAllTransactions(int id) {
        final List<Transactions> result = new ArrayList<>();
        dbManager.getDSL().transaction(configuration -> {
            result.addAll(DSL.using(configuration).selectFrom(TRANSACTIONS)
                    .where(TRANSACTIONS.FROM_ACCOUNT.eq(id).or(TRANSACTIONS.TO_ACCOUNT.eq(id)))
                    .fetchInto(Transactions.class));
        });
        return result;
    }

    @Override
    public Optional<Account> depositMoneyInAccount(int id, String curr, BigDecimal amount) {
        final Account[] result = new Account[1];

        dbManager.getDSL().transaction(configuration -> {
            AccountRecord accountRecord = depositMoney(configuration, id, curr, amount);
            result[0] = accountRecord.into(Account.class);
        });

        return Optional.of(result[0]);
    }

    private AccountRecord depositMoney(Configuration configuration, int id, String curr, BigDecimal amount) {
        CurrencyRecord currencyRecord = DSL.using(configuration).selectFrom(CURRENCY)
                .where(CURRENCY.CODE.eq(curr))
                .fetchOne();
        if (currencyRecord == null) {
            throw new CurrencyNotFoundException(curr);
        }
        AccountRecord accountRecord = DSL.using(configuration).selectFrom(ACCOUNT)
                .where(ACCOUNT.ID.eq(id))
                .fetchOne();
        if (accountRecord == null) {
            throw new AccountNotFoundException(id);
        }
        BigDecimal normAmt = amount;
        if (!currencyRecord.getCode().equalsIgnoreCase(accountRecord.getCurrencyCode())) {
            normAmt = amount.multiply(currencyRecord.getFactor());
            CurrencyRecord accCurrencyRecord = DSL.using(configuration).selectFrom(CURRENCY)
                    .where(CURRENCY.CODE.eq(accountRecord.getCurrencyCode()))
                    .fetchOne();
            normAmt = normAmt.divide(accCurrencyRecord.getFactor(), 2, BigDecimal.ROUND_HALF_EVEN);
        }
        if (accountRecord.getBalance().add(normAmt).compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeBalanceException();
        }
        accountRecord.setBalance(accountRecord.getBalance().add(normAmt));

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            saveTransactionHistory(configuration, id, 0, amount, normAmt, curr);
        } else {
            saveTransactionHistory(configuration, 0, id, amount, normAmt, curr);
        }
        accountRecord.store();
        return accountRecord;
    }

    private void saveTransactionHistory(Configuration configuration, int fromAccount, int toAccount, BigDecimal amount,
                                        BigDecimal convertedAmount, String currency) {
        DSL.using(configuration).insertInto(TRANSACTIONS,
                TRANSACTIONS.FROM_ACCOUNT, TRANSACTIONS.TO_ACCOUNT, TRANSACTIONS.CURRENCY, TRANSACTIONS.AMOUNT, TRANSACTIONS.CONVERTED_AMOUNT)
                .values(fromAccount, toAccount, currency, amount, convertedAmount).execute();
    }
}
