package com.revolut.task.api;

import com.google.gson.Gson;
import com.revolut.task.di.InjectorProvider;
import com.revolut.task.models.tables.pojos.Account;
import com.revolut.task.service.AccountService;
import com.revolut.task.service.TransactionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Route;

import java.math.BigDecimal;

/**
 * AccountsHandler contains the HTTP handler for all the requests related to accounts.
 * It hands off the request handling to corresponding services.
 */
public class AccountsHandler {

    private final AccountService accountService;
    private final TransactionService transactionService;

    private static final Gson gson = new Gson();
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsHandler.class);

    public AccountsHandler() {
        this.accountService = InjectorProvider.provide().getInstance(AccountService.class);
        this.transactionService = InjectorProvider.provide().getInstance(TransactionService.class);
    }

    /**
     * Handler for getting an account
     *
     * @return Account Details
     */
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

    /**
     * Handles account creation
     *
     * @return CreateAccountResponse with details of newly created account
     */
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

    /**
     * handler for clsoing an account
     *
     * @return Success or failures
     */
    public Route closeAccount() {
        return (req, res) -> {
            String accountID = req.params(":id");
            if (StringUtils.isBlank(accountID) || !StringUtils.isNumeric(accountID)) {
                throw new Exception("Invalid account number");
            }
            boolean status = accountService.closeAccount(Integer.parseInt(accountID));
            res.status(200);
            res.type("application/json");
            return BaseResponse.Builder.aBaseResponse().withMessage("Deleted");
        };
    }

    /**
     * Handles amount deposit in the account
     *
     * @return Status of transaction
     */
    public Route depositAmount() {
        return (req, resp) -> {
            validateDepositRequest(req);

            boolean status = transactionService.deposit(Integer.parseInt(req.params(":id")),
                    req.params(":currency"),
                    NumberUtils.createBigDecimal(req.params(":amount")));
            if (!status) {
                resp.status(500);
                resp.type("application/json");
                return new BaseResponse("Could not transfer");
            }
            resp.status(200);
            resp.type("application/json");
            return new BaseResponse("Transferred");

        };
    }

    /**
     * Handles amount withdrawal in the account
     *
     * @return Status of transaction
     */
    public Route withdrawAmount() {
        return (req, resp) -> {
            validateDepositRequest(req);
            boolean status = transactionService.deposit(Integer.parseInt(req.params(":id")),
                    req.params(":currency"),
                    NumberUtils.createBigDecimal(req.params(":amount")).negate());
            if (!status) {
                resp.status(500);
                resp.type("application/json");
                return new BaseResponse("Could not transfer");
            }
            resp.status(200);
            resp.type("application/json");
            return new BaseResponse("Transferred");
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
