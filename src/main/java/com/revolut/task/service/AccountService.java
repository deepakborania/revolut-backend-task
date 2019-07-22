package com.revolut.task.service;

import com.revolut.task.models.tables.pojos.Account;

import java.math.BigDecimal;

/**
 * AccountService provides interface to different services related to accounts. These are called by HTTP handlers to
 * perform operations on the Accounts.
 */
public interface AccountService {

    /**
     * Create a new account with given name and currency
     *
     * @param name     Account name (e.g. Savings Account)
     * @param currency Currency Code(e.g. GBP, INR, USD)
     * @return A new account if no error
     * @throws Exception Thrown if there is an error in creating the account
     */
    Account createAccount(String name, String currency) throws Exception;

    /**
     * Get an existing account
     *
     * @param id Account ID
     * @return The account if found
     * @throws Exception                                            If no such account exists
     * @throws com.revolut.task.exceptions.AccountNotFoundException if no such account exists.
     */
    Account getAccount(int id) throws Exception;

    /**
     * Marks an account inactive on closure
     *
     * @param accountID AccountID to close
     * @return true on success
     * @throws Exception                                            If there is an error in closing account.
     * @throws com.revolut.task.exceptions.AccountNotFoundException if no such account exists.
     */
    boolean closeAccount(int accountID) throws Exception;
}
