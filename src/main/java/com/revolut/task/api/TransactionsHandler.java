package com.revolut.task.api;

import com.google.inject.Inject;
import com.revolut.task.di.InjectorProvider;
import com.revolut.task.models.tables.pojos.Transactions;
import com.revolut.task.service.TransactionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;

import java.math.BigDecimal;
import java.util.List;

/**
 * TransactionsHandler contains the HTTP handler for all the requests related to transactions.
 * It hands off the request handling to corresponding services.
 */
public class TransactionsHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionsHandler.class);

    private final TransactionService transactionService;

    @Inject
    public TransactionsHandler() {
        this.transactionService = InjectorProvider.provide().getInstance(TransactionService.class);
    }

    public Route fetchTransactions() {
        return (req, res) -> {
            String id = req.params(":id");
            List<Transactions> txns = transactionService.fetchAllTransactions(Integer.parseInt(id));
            return TransactionsResponse.Builder.aTransactionsResponse()
                    .withTransactions(txns)
                    .build();
        };
    }

    public Route transfer() {
        return (request, response) -> {
            String fromID = request.params("fromID");
            String toID = request.params("toID");
            if (StringUtils.isAnyBlank(fromID, toID) || !StringUtils.isNumeric(fromID)) {
                throw new Exception("Empty/Invalid account ids");
            }

            if (Integer.parseInt(fromID) == Integer.parseInt(toID)) {
                throw new Exception("From and To account should be different");
            }
            String currency = request.params("currency");
            if (StringUtils.isBlank(currency)) {
                throw new Exception("missing currency code");
            }
            String amountStr = request.params("amount");
            if (StringUtils.isBlank(amountStr) || !NumberUtils.isNumber(amountStr)) {
                throw new Exception("invalid amount");
            }
            BigDecimal amount = NumberUtils.createBigDecimal(amountStr);
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                throw new Exception("amount must be positive");
            }
            boolean status = transactionService.transfer(Integer.parseInt(fromID), Integer.parseInt(toID), currency, amount);
            if (!status) {
                response.status(500);
                response.type("application/json");
                return BaseResponse.Builder.aBaseResponse()
                        .withMessage("Could not transfer")
                        .withStatus(1);
            }
            response.status(200);
            response.type("application/json");
            return BaseResponse.Builder.aBaseResponse()
                    .withMessage("Transferred");
        };
    }
}
