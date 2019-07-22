package com.revolut.task.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.revolut.task.di.InjectorProvider;
import com.revolut.task.models.tables.pojos.Transactions;
import com.revolut.task.repositories.TransactionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

@Singleton
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final TransactionsRepository transactionsRepository;
    private final LockService lockService;

    @Inject
    public TransactionServiceImpl() {
        this.lockService = InjectorProvider.provide().getInstance(LockService.class);
        this.transactionsRepository = InjectorProvider.provide().getInstance(TransactionsRepository.class);
    }

    /**
     * Fetches all the transactions in which the given account is involved
     *
     * @param accountID Account ID to look for.
     * @return List of {@link Transactions}
     */
    @Override
    public List<Transactions> fetchAllTransactions(int accountID) {
        LOGGER.info("Fetching all transactions for account# {}", accountID);
        return transactionsRepository.fetchAllTransactions(accountID);
    }

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
    @Override
    public boolean deposit(int id, String curr, BigDecimal amount) throws Exception {
        LOGGER.info("Depositing {} {} amount in account# {}", curr, amount, id);
        lockService.runInLock(id,
                () -> transactionsRepository.depositMoneyInAccount(id, curr.toUpperCase(), amount).get());
        return true;
    }

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
    @Override
    public boolean transfer(int fromAccountID, int toAccountID, String currency, BigDecimal amount) throws Exception {
        LOGGER.info("Transferring {} {} from account {} to {}", currency, amount, fromAccountID, toAccountID);
        return lockService.runInLock(fromAccountID, toAccountID,
                () -> transactionsRepository.transfer(fromAccountID, toAccountID, currency.toUpperCase(), amount));
    }
}
