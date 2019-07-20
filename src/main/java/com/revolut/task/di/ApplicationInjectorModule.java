package com.revolut.task.di;

import com.google.inject.AbstractModule;
import com.revolut.task.db.DBManager;
import com.revolut.task.db.DBManagerImpl;
import com.revolut.task.repositories.AccountsRepository;
import com.revolut.task.repositories.AccountsRepositoryImpl;
import com.revolut.task.repositories.TransactionsRepository;
import com.revolut.task.repositories.TransactionsRepositoryImpl;
import com.revolut.task.service.AccountService;
import com.revolut.task.service.AccountServiceImpl;
import com.revolut.task.service.TransactionService;
import com.revolut.task.service.TransactionServiceImpl;

public class ApplicationInjectorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DBManager.class).to(DBManagerImpl.class);
        bind(AccountsRepository.class).to(AccountsRepositoryImpl.class);
        bind(TransactionsRepository.class).to(TransactionsRepositoryImpl.class);
        bind(AccountService.class).to(AccountServiceImpl.class);
        bind(TransactionService.class).to(TransactionServiceImpl.class);
    }
}
