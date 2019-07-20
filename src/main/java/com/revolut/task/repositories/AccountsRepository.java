package com.revolut.task.repositories;

import com.revolut.task.models.tables.pojos.Account;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountsRepository {

    Optional<Account> getAccountByID(int id);

    Optional<Account> createAccount(String name, String currency);

    Optional<Account> depositMoneyInAccount(int id, String curr, BigDecimal amount);
}
