package com.revolut.task.api;

import java.io.Serializable;

public class CreateAccountResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = -1284518031671369305L;

    private int accountId;
    private String accountName;
    private String balance;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public static final class Builder {
        private String message;
        private int accountId;
        private String accountName;
        private String balance;

        private Builder() {
        }

        public static Builder aCreateAccountResponse() {
            return new Builder();
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withAccountId(int accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder withAccountName(String accountName) {
            this.accountName = accountName;
            return this;
        }

        public Builder withBalance(String balance) {
            this.balance = balance;
            return this;
        }

        public CreateAccountResponse build() {
            CreateAccountResponse createAccountResponse = new CreateAccountResponse();
            createAccountResponse.setMessage(message);
            createAccountResponse.setAccountId(accountId);
            createAccountResponse.setAccountName(accountName);
            createAccountResponse.setBalance(balance);
            return createAccountResponse;
        }
    }
}
