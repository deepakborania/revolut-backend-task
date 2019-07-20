package com.revolut.task.exceptions;

public class NegativeBalanceException extends RuntimeException {

    private static final long serialVersionUID = -8605137079186351797L;

    @Override
    public String getMessage() {
        return "balance cannot be negative";
    }
}
