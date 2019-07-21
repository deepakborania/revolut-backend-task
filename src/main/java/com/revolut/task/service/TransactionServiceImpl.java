package com.revolut.task.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.revolut.task.di.InjectorProvider;
import com.revolut.task.models.tables.pojos.Account;
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
    public boolean deposit(int id, String curr, BigDecimal amount) {
        LOGGER.info("Depositing {} {} amount in account# {}", curr, amount, id);
        try {
            lockService.runInLock(id, () -> transactionsRepository.depositMoneyInAccount(id, curr, amount).get());
            return true;
        } catch (Exception e) {
            LOGGER.error("Error in depositing {} {} amount in account# {}", curr, amount, id);
            return false;
        }
    }
}
