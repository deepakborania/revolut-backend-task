package com.revolut.task.service;

import com.revolut.task.models.tables.pojos.Transactions;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    List<Transactions> fetchAllTransactions(int accountID);

    boolean deposit(int id, String curr, BigDecimal amount);

    boolean transfer(int fromAccountID, int toAccountID, String currency, BigDecimal amount);
}
