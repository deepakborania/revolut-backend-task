package com.revolut.task.api;

import java.io.Serializable;
import java.math.BigDecimal;

public class Balance implements Serializable {

    private static final long serialVersionUID = 6775170090147925212L;

    private String currencyCode;
    private BigDecimal amount;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
