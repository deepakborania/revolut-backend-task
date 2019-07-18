package com.revolut.task.service;

import com.revolut.task.models.tables.pojos.Account;

public interface AccountService {

    Account createAccount(String name, String currency) throws Exception;

    Account getAccount(int id);
}
