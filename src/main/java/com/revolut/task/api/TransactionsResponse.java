package com.revolut.task.api;

import com.revolut.task.models.tables.pojos.Transactions;

import java.util.List;

public class TransactionsResponse extends BaseResponse {
    List<Transactions> transactions;

    public static final class Builder {
        List<Transactions> transactions;
        private String message;
        private int status;

        private Builder() {
        }

        public static Builder aTransactionsResponse() {
            return new Builder();
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withStatus(int status) {
            this.status = status;
            return this;
        }

        public Builder withTransactions(List<Transactions> transactions) {
            this.transactions = transactions;
            return this;
        }

        public TransactionsResponse build() {
            TransactionsResponse transactionsResponse = new TransactionsResponse();
            transactionsResponse.setMessage(message);
            transactionsResponse.setStatus(status);
            transactionsResponse.transactions = this.transactions;
            return transactionsResponse;
        }
    }
}
