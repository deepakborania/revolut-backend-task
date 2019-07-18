package com.revolut.task.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.revolut.task.models.tables.pojos.Account;
import com.revolut.task.repositories.AccountsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountsRepository accountsRepository;

    @Inject
    public AccountServiceImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
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
        Account account = accountsRepository.getAccountByID(id).orElse(null);
        return account;

    }
}
