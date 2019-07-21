package com.revolut.task.repositories;

import com.revolut.task.models.tables.pojos.Account;
import com.revolut.task.models.tables.pojos.Transactions;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TransactionsRepository {
    List<Transactions> fetchAllTransactions(int id);

    Optional<Account> depositMoneyInAccount(int id, String curr, BigDecimal amount);

    boolean transfer(int fromAccountID, int toAccountID, String toUpperCase, BigDecimal amount);
}
