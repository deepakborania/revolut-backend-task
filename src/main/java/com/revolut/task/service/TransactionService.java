package com.revolut.task.service;

import com.revolut.task.models.tables.pojos.Account;
import com.revolut.task.models.tables.pojos.Transactions;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    List<Transactions> fetchAllTransactions(int accountID);
    Account deposit(int id, String curr, BigDecimal amount);
}
