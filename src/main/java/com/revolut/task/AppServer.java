package com.revolut.task;

import com.beust.jcommander.JCommander;
import com.revolut.task.api.AccountsHandler;
import com.revolut.task.api.TransactionsHandler;
import com.revolut.task.utils.JsonTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import static spark.Spark.*;

/**
 * AppsServer is the starting point for the whole application.
 */
public class AppServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppServer.class);
    private static final JsonTransformer json = new JsonTransformer();

    private AccountsHandler accountHandlers;
    private TransactionsHandler transactionsHandlers;

    /**
     * startServer reads in the command line arguments and sets up the routes and starts the server on the provided port.
     * @param args Command line arguments
     */
    public void startServer(String... args) {
        Configuration configuration = new Configuration();
        new JCommander(configuration, args);
        System.getProperties().setProperty("org.jooq.no-logo", "true");
        port(configuration.getPort());

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

    /**
     * Register various account related handlers
     */
    private void registerAccountHandlers() {
        path("/accounts", () -> {
            get("/:id", this.accountHandlers.getAccountById(), json);
            post("", this.accountHandlers.createAccount(), json);
            delete("/:id", this.accountHandlers.closeAccount(), json);
            put("/:id/deposit/:currency/:amount", this.accountHandlers.depositAmount(), json);
            put("/:id/withdraw/:currency/:amount", this.accountHandlers.withdrawAmount(), json);
        });
    }

    /**
     * Register various transaction related handlers
     */
    private void registerTransactionHandlers() {
        path("/txn", () -> {
            get("/:id", this.transactionsHandlers.fetchTransactions(), json);
            post("/transfer/:fromID/:toID/:currency/:amount", this.transactionsHandlers.transfer(), json);
        });
    }

    public static void main(String[] args) {
        AppServer app = new AppServer();
        app.startServer(args);
    }
}
