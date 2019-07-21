package com.revolut.task.service;

import com.revolut.task.models.tables.pojos.Account;

import java.math.BigDecimal;

public interface AccountService {

    Account createAccount(String name, String currency) throws Exception;

    Account getAccount(int id) throws Exception;

    boolean closeAccount(int accountID) throws Exception;
}
