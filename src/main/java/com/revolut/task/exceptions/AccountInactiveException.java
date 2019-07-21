package com.revolut.task.exceptions;

public class AccountInactiveException extends RuntimeException {

    private static final long serialVersionUID = 1266313367974047127L;

    private int accountID;

    public AccountInactiveException(int accountID) {
        this.accountID = accountID;
    }

    public int getAccountID() {
        return accountID;
    }

    @Override
    public String getMessage() {
        return "Inactive account " + accountID;
    }
}
