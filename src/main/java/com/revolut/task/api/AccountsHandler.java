package com.revolut.task.api;

import com.google.gson.Gson;
import com.revolut.task.di.InjectorProvider;
import com.revolut.task.models.tables.pojos.Account;
import com.revolut.task.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Route;

import java.math.BigDecimal;

public class AccountsHandler {

    private final AccountService accountService;

    private static final Gson gson = new Gson();
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsHandler.class);

    public AccountsHandler() {
        this.accountService = InjectorProvider.provide().getInstance(AccountService.class);
    }

    public Route getAccountById() {
        return (req, resp) -> {
            String id = req.params(":id");
            if (id == null || id.length() <= 0) {
                throw new Exception("Empty account id");
            }
            Account acc = accountService.getAccount(Integer.parseInt(id));
            resp.type("application/json");
            if (acc == null) {
                resp.status(404);
                return null;
            } else {
                resp.status(200);
                return CreateAccountResponse.Builder.aCreateAccountResponse()
                        .withAccountId(acc.getId())
                        .withAccountName(acc.getName())
                        .withBalance(String.format("%s %s", acc.getCurrencyCode(), acc.getBalance().toString()))
                        .build();
            }
        };
    }

    public Route createAccount() {
        return (req, res) -> {
            CreateAccountRequest car = gson.fromJson(req.body(), CreateAccountRequest.class);
            if (!car.isValid()) {
                throw new Exception("Invalid request");
            }
            Account acc = accountService.createAccount(car.getName(), car.getCurrencyCode());

            res.status(200);
            res.type("application/json");
            return CreateAccountResponse.Builder.aCreateAccountResponse()
                    .withAccountId(acc.getId())
                    .withAccountName(acc.getName())
                    .withBalance(String.format("%s %d", acc.getCurrencyCode(), 0))
                    .build();
        };
    }

    public Route depositAmount() {
        return (req, resp) -> {
            validateDepositRequest(req);
            return accountService.deposit(Integer.parseInt(req.params(":id")),
                    req.params(":currency"),
                    NumberUtils.createBigDecimal(req.params(":amount")));
        };
    }

    public Route withdrawAmount() {
        return (req, resp) -> {
            validateDepositRequest(req);
            return accountService.deposit(Integer.parseInt(req.params(":id")),
                    req.params(":currency"),
                    NumberUtils.createBigDecimal(req.params(":amount")).negate());
        };
    }

    private void validateDepositRequest(Request req) throws Exception {
        String id = req.params(":id");
        if (StringUtils.isBlank(id) || !StringUtils.isNumeric(id)) {
            throw new Exception("Empty/Invalid account id");
        }
        String curr = req.params(":currency");
        if (StringUtils.isBlank(curr)) {
            throw new Exception("missing currency code");
        }
        String amountStr = req.params(":amount");
        if (StringUtils.isBlank(amountStr) || !NumberUtils.isNumber(amountStr)) {
            throw new Exception("invalid amount");
        }
        BigDecimal amount = NumberUtils.createBigDecimal(amountStr);
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("amount must be positive");
        }
    }
}
