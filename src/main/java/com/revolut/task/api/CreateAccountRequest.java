package com.revolut.task.api;

import com.revolut.task.Validable;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class CreateAccountRequest implements Serializable, Validable {

    private static final long serialVersionUID = -4969941063130205547L;

    private String name;
    private String currencyCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public boolean isValid() {
        return StringUtils.isNoneBlank(this.currencyCode, this.name);
    }
}
