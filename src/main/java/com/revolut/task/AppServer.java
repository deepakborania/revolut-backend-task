package com.revolut.task;

import com.revolut.task.api.AccountsHandler;
import com.revolut.task.api.TransactionsHandler;
import com.revolut.task.utils.JsonTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import static spark.Spark.*;

public class AppServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppServer.class);
    private static final JsonTransformer json = new JsonTransformer();

    private AccountsHandler accountHandlers;
    private TransactionsHandler transactionsHandlers;

    public void startServer() {
        System.getProperties().setProperty("org.jooq.no-logo", "true");
        port(8080);

        this.accountHandlers = new AccountsHandler();
        this.transactionsHandlers = new TransactionsHandler();

        before((req, res) -> {
            LOGGER.info("Request received. METHOD={}, Path={}, Request={}", req.requestMethod(), req.pathInfo(), req.body());
        });

        path("/api", () -> {

            registerAccountHandlers();
            registerTransactionHandlers();
        });

        after((req, res) -> {
            LOGGER.info("Response => METHOD={}, Path={}, Reponse={}, Content-Type={}", req.requestMethod(), req.pathInfo(), res.body(), res.type());
        });

        exception(Exception.class, new ErrorHandler());
        notFound((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"This route does not exist\"}";
        });
    }

    public void stopServer() {
        Spark.stop();
        Spark.awaitStop();
    }

    private void registerAccountHandlers() {
        path("/accounts", () -> {
            get("/:id", this.accountHandlers.getAccountById(), json);
            post("", this.accountHandlers.createAccount(), json);
            delete("/:id", this.accountHandlers.closeAccount(), json);
            put("/:id/deposit/:currency/:amount", this.accountHandlers.depositAmount(), json);
            put("/:id/withdraw/:currency/:amount", this.accountHandlers.withdrawAmount(), json);
        });
    }

    private void registerTransactionHandlers() {
        path("/txn", () -> {
            get("/:id", this.transactionsHandlers.fetchTransactions(), json);
            post("/transfer/:fromID/:toID/:currency/:amount", this.transactionsHandlers.transfer(), json);
        });
    }

    public static void main(String[] args) {
        AppServer app = new AppServer();
        app.startServer();
    }
}
