package com.revolut.task.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.revolut.task.di.InjectorProvider;
import com.revolut.task.models.tables.pojos.Account;
import com.revolut.task.repositories.AccountsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

@Singleton
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountsRepository accountsRepository;
    private final LockService lockService;

    @Inject
    public AccountServiceImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = InjectorProvider.provide().getInstance(AccountsRepository.class);
        this.lockService = InjectorProvider.provide().getInstance(LockService.class);
    }

    @Override
    public Account createAccount(String name, String currency) throws Exception {
        LOGGER.info("Creating new account, Name:{}, Currency: {}", name, currency);
        Account account = accountsRepository.createAccount(name, currency)
                .orElseThrow(() -> new Exception("Could not create account"));
        LOGGER.info("Created new account, Name:{}, Currency:{}, ID:{}", account.getName(), account.getCurrencyCode(), account.getId());
        return account;
    }

    @Override
    public Account getAccount(int id) {
        LOGGER.info("Fetching account, id: {}", id);
        try {
            return lockService.runInLock(id, () -> accountsRepository.getAccountByID(id).orElse(null));
        } catch (Exception e) {
            LOGGER.error("Error in fetching account", e);
        }
        return null;
    }
}
