package com.revolut.task;

import com.revolut.task.api.AccountsHandler;
import com.revolut.task.utils.JsonTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class AppServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppServer.class);
    private static final JsonTransformer json = new JsonTransformer();

    private AccountsHandler accountHandlers;

    public void startServer() {
        System.getProperties().setProperty("org.jooq.no-logo", "true");
        port(8080);

        this.accountHandlers = new AccountsHandler();
        path("/api", () -> {
            before((req, res) -> {
                LOGGER.info("Request received. Path={}, Request={}", req.contextPath(), req.body());
            });
            registerAccountHandlers();
        });

        exception(Exception.class, new ErrorHandler());
        notFound((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"This route does not exist\"}";
        });
    }

    private void registerAccountHandlers() {
        path("/accounts", () -> {
            get("/:id", this.accountHandlers.getAccountById(), json);
            post("/create", this.accountHandlers.createAccount(), json);
            put("/:id/deposit/:currency/:amount", this.accountHandlers.depositAmount(), json);
            put("/:id/withdraw/:currency/:amount", this.accountHandlers.withdrawAmount(), json);
        });
    }

    public static void main(String[] args) {
        AppServer app = new AppServer();
        app.startServer();
    }
}
