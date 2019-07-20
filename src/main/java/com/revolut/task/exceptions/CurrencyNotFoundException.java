package com.revolut.task.exceptions;

public class CurrencyNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -829923019298683283L;

    private String currencyCode;

    public CurrencyNotFoundException(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public String getMessage() {
        return "Cannot find currency " + currencyCode;
    }
}
