package com.revolut.task.service;

import com.revolut.task.models.tables.pojos.Transactions;

import java.math.BigDecimal;
import java.util.List;

/**
 * TransactionService provides interface to various services related to transactions. These are called by HTTP handlers.
 */
public interface TransactionService {

    /**
     * Fetches all the transactions in which the given account is involved
     *
     * @param accountID Account ID to look for.
     * @return List of {@link Transactions}
     */
    List<Transactions> fetchAllTransactions(int accountID);

    /**
     * This service is called by deposit and withdrawal handlers.
     *
     * @param id     Account ID to deposit in or withdraw from
     * @param curr   Currency of transaction
     * @param amount Amount of transaction
     * @return true if successful else false
     * @throws com.revolut.task.exceptions.CurrencyNotFoundException if invalid currency
     * @throws com.revolut.task.exceptions.AccountNotFoundException  if the account is not found
     * @throws com.revolut.task.exceptions.AccountInactiveException  if the account has been closed
     * @throws com.revolut.task.exceptions.NegativeBalanceException  if this transaction will result in negative balance for the account
     * @throws Exception                                             on any other exception
     */
    boolean deposit(int id, String curr, BigDecimal amount) throws Exception;

    /**
     * This service is called by money transfer handlers.
     *
     * @param fromAccountID Account ID of the sender
     * @param toAccountID   Account ID of the receiver
     * @param currency      Currency of the transaction
     * @param amount        Amount of the transaction
     * @return true if successful else false
     * @throws com.revolut.task.exceptions.CurrencyNotFoundException if invalid currency
     * @throws com.revolut.task.exceptions.AccountNotFoundException  if the account is not found
     * @throws com.revolut.task.exceptions.AccountInactiveException  if the account has been closed
     * @throws com.revolut.task.exceptions.NegativeBalanceException  if this transaction will result in negative balance for the account
     * @throws Exception                                             on any other exception
     */
    boolean transfer(int fromAccountID, int toAccountID, String currency, BigDecimal amount) throws Exception;
}
