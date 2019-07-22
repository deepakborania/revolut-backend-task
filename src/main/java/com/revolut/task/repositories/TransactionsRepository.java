package com.revolut.task.repositories;

import com.revolut.task.models.tables.pojos.Account;
import com.revolut.task.models.tables.pojos.Transactions;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Interface for all the transactions DB operations
 */
public interface TransactionsRepository {

    /**
     * Fetches all the transactions in which the given account is involved
     *
     * @param id Account ID to look for.
     * @return List of {@link Transactions}
     */
    List<Transactions> fetchAllTransactions(int id);

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
    Optional<Account> depositMoneyInAccount(int id, String curr, BigDecimal amount);

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
    boolean transfer(int fromAccountID, int toAccountID, String toUpperCase, BigDecimal amount);
}
