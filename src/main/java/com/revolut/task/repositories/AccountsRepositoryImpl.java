package com.revolut.task.repositories;

import com.google.inject.Inject;
import com.revolut.task.exceptions.AccountNotFoundException;
import com.revolut.task.exceptions.CurrencyNotFoundException;
import com.revolut.task.db.DBManager;
import com.revolut.task.di.InjectorProvider;
import com.revolut.task.exceptions.NegativeBalanceException;
import com.revolut.task.models.tables.pojos.Account;
import com.revolut.task.models.tables.records.AccountRecord;
import com.revolut.task.models.tables.records.CurrencyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Optional;

import static com.revolut.task.models.Tables.ACCOUNT;
import static com.revolut.task.models.Tables.CURRENCY;

public class AccountsRepositoryImpl implements AccountsRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsRepositoryImpl.class);

    private final DBManager dbManager;

    @Inject
    public AccountsRepositoryImpl() {
        this.dbManager = InjectorProvider.provide().getInstance(DBManager.class);
    }

    @Override
    public Optional<Account> getAccountByID(int id) {
        final Account[] result = new Account[1];
        try {
            dbManager.getDSL().transaction(configuration -> {
                AccountRecord accRecord = dbManager.getDSL().selectFrom(ACCOUNT)
                        .where(ACCOUNT.ID.eq(id))
                        .fetchOne();

                if (accRecord == null) {
                    throw new AccountNotFoundException(id);
                } else {
                    result[0] = accRecord.into(Account.class);
                }
            });
        } catch (Exception e) {
            LOGGER.error("Error in getting account", e);
            return Optional.empty();
        }
        return Optional.of(result[0]);
    }

    @Override
    public Optional<Account> createAccount(String name, String currencyCode) {
        final Account[] result = new Account[1];

        try {
            dbManager.getDSL().transaction(configuration -> {
                result[0] = dbManager.getDSL()
                        .insertInto(ACCOUNT, ACCOUNT.NAME, ACCOUNT.CURRENCY_CODE, ACCOUNT.BALANCE, ACCOUNT.ACTIVE)
                        .values(name, currencyCode.toUpperCase(), BigDecimal.ZERO, true)
                        .returning(ACCOUNT.ID, ACCOUNT.NAME, ACCOUNT.CURRENCY_CODE, ACCOUNT.BALANCE)
                        .fetchOne()
                        .into(Account.class);
            });
        } catch (Exception e) {
            LOGGER.error("Error in creating account", e);
            return Optional.empty();
        }
        return Optional.of(result[0]);
    }

    @Override
    public Optional<Account> depositMoneyInAccount(int id, String curr, BigDecimal amount) {
        final Account[] result = new Account[1];

        dbManager.getDSL().transaction(configuration -> {
            AccountRecord accountRecord = depositMoney(id, curr, amount);
            result[0] = accountRecord.into(Account.class);
        });

        return Optional.of(result[0]);
    }

    private AccountRecord depositMoney(int id, String curr, BigDecimal amount) {
        CurrencyRecord currencyRecord = dbManager.getDSL().selectFrom(CURRENCY)
                .where(CURRENCY.CODE.eq(curr))
                .fetchOne();
        if (currencyRecord == null) {
            throw new CurrencyNotFoundException(curr);
        }
        AccountRecord accountRecord = dbManager.getDSL().selectFrom(ACCOUNT)
                .where(ACCOUNT.ID.eq(id))
                .fetchOne();
        if (accountRecord == null) {
            throw new AccountNotFoundException(id);
        }
        BigDecimal normAmt = amount;
        if (!currencyRecord.getCode().equalsIgnoreCase(accountRecord.getCurrencyCode())) {
            normAmt = amount.multiply(currencyRecord.getFactor());
            CurrencyRecord accCurrencyRecord = dbManager.getDSL().selectFrom(CURRENCY)
                    .where(CURRENCY.CODE.eq(accountRecord.getCurrencyCode()))
                    .fetchOne();
            normAmt = normAmt.divide(accCurrencyRecord.getFactor(), 2, BigDecimal.ROUND_HALF_EVEN);
        }
        if (accountRecord.getBalance().add(normAmt).compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeBalanceException();
        }
        accountRecord.setBalance(accountRecord.getBalance().add(normAmt));
        accountRecord.store();
        return accountRecord;
    }
}
