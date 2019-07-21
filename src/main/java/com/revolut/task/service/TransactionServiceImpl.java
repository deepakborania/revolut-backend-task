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

    @Override
    public List<Transactions> fetchAllTransactions(int accountID) {
        LOGGER.info("Fetching all transactions for account# {}", accountID);
        return transactionsRepository.fetchAllTransactions(accountID);
    }

    @Override
    public boolean deposit(int id, String curr, BigDecimal amount) throws Exception {
        LOGGER.info("Depositing {} {} amount in account# {}", curr, amount, id);
        lockService.runInLock(id,
                () -> transactionsRepository.depositMoneyInAccount(id, curr.toUpperCase(), amount).get());
        return true;
    }

    @Override
    public boolean transfer(int fromAccountID, int toAccountID, String currency, BigDecimal amount) throws Exception {
        LOGGER.info("Transferring {} {} from account {} to {}", currency, amount, fromAccountID, toAccountID);
        return lockService.runInLock(fromAccountID, toAccountID,
                () -> transactionsRepository.transfer(fromAccountID, toAccountID, currency.toUpperCase(), amount));
    }
}
