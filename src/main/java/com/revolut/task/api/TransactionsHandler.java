package com.revolut.task.api;

import com.google.inject.Inject;
import com.revolut.task.di.InjectorProvider;
import com.revolut.task.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;

public class TransactionsHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionsHandler.class);

    private final TransactionService transactionService;

    @Inject
    public TransactionsHandler() {
        this.transactionService = InjectorProvider.provide().getInstance(TransactionService.class);
    }

    public Route fetchTransactions() {
        return (req, res) -> {
            String id = req.params(":id");
            return transactionService.fetchAllTransactions(Integer.parseInt(id));
        };
    }
}
