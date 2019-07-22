package com.revolut.task.repositories;

import com.google.inject.Inject;
import com.revolut.task.db.DBManager;
import com.revolut.task.di.InjectorProvider;
import com.revolut.task.exceptions.AccountInactiveException;
import com.revolut.task.exceptions.AccountNotFoundException;
import com.revolut.task.exceptions.CurrencyNotFoundException;
import com.revolut.task.exceptions.NegativeBalanceException;
import com.revolut.task.models.tables.pojos.Account;
import com.revolut.task.models.tables.pojos.Transactions;
import com.revolut.task.models.tables.records.AccountRecord;
import com.revolut.task.models.tables.records.CurrencyRecord;
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

    /**
     * Fetches all the transactions in which the given account is involved
     *
     * @param id Account ID to look for.
     * @return List of {@link Transactions}
     */
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

    /**
     * This service is called by deposit and withdrawal handlers.
     *
     * @param id     Account ID to deposit in or withdraw from
     * @param curr   Currency of transaction
     * @param amount Amount of transaction
     * @return Optional with updated account
     * @throws com.revolut.task.exceptions.CurrencyNotFoundException if invalid currency
     * @throws com.revolut.task.exceptions.AccountNotFoundException  if the account is not found
     * @throws com.revolut.task.exceptions.AccountInactiveException  if the account has been closed
     * @throws com.revolut.task.exceptions.NegativeBalanceException  if this transaction will result in negative balance for the account
     */
    @Override
    public Optional<Account> depositMoneyInAccount(int id, String curr, BigDecimal amount) {
        final Account[] result = new Account[1];

        dbManager.getDSL().transaction(configuration -> {
            AccountRecord accountRecord = depositMoney(configuration, id, curr, amount, true);
            result[0] = accountRecord.into(Account.class);
        });

        return Optional.of(result[0]);
    }


    /**
     * This repository function is called by deposit and withdrawal handlers.
     *
     * @param fromAccountID Account ID to deposit in or withdraw from
     * @param toAccountID   Currency of transaction
     * @param amount        Amount of transaction
     * @return true if successful else false
     * @throws com.revolut.task.exceptions.CurrencyNotFoundException if invalid currency
     * @throws com.revolut.task.exceptions.AccountNotFoundException  if the account is not found
     * @throws com.revolut.task.exceptions.AccountInactiveException  if the account has been closed
     * @throws com.revolut.task.exceptions.NegativeBalanceException  if this transaction will result in negative balance for the account
     */
    @Override
    public boolean transfer(int fromAccountID, int toAccountID, String currency, BigDecimal amount) {
        dbManager.getDSL().transaction(configuration -> {
            depositMoney(configuration, fromAccountID, currency, amount.negate(), false);
            depositMoney(configuration, toAccountID, currency, amount, false);
            saveTransactionHistory(configuration, fromAccountID, toAccountID, amount, amount, currency);
        });
        return true;
    }

    // This function implements repository function for depositing or withdrawing from an account.
    private AccountRecord depositMoney(Configuration configuration, int id, String curr, BigDecimal amount, boolean writeTXNToLog) {
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
        if (!Boolean.TRUE.equals(accountRecord.getActive())) {
            throw new AccountInactiveException(id);
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

        if (writeTXNToLog) {
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                saveTransactionHistory(configuration, id, 0, amount, normAmt, curr);
            } else {
                saveTransactionHistory(configuration, 0, id, amount, normAmt, curr);
            }
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
