package com.revolut.task.api;

import com.revolut.task.AppServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TransactionsHandlerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionsHandlerTest.class);

    private static AppServer appServer = new AppServer();

    @BeforeEach
    void setup() {
        appServer.startServer();
        Spark.awaitInitialization();
    }

    @AfterEach
    void teardown() {
        appServer.stopServer();
    }

    @Test
    void multiTransferAmount() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 50; i++) {
            executorService.submit(() -> {
                given()
                        .post("/api/txn/transfer/10006/10005/GBP/1")
                        .then()
                        .log().body()
                        .assertThat().contentType("application/json")
                        .assertThat().statusCode(200);
            });
            executorService.submit(() -> {
                given()
                        .post("/api/txn/transfer/10004/10003/INR/1")
                        .then()
                        .log().body()
                        .assertThat().contentType("application/json")
                        .assertThat().statusCode(200);
            });
        }
        try {
            executorService.shutdown();
            executorService.awaitTermination(200, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
        given()
                .get("/api/accounts/10005")
                .then().log().body()
                .assertThat().contentType("application/json")
                .assertThat().statusCode(200)
                .assertThat().body("balance", equalTo("GBP 50"));
        given()
                .get("/api/accounts/10003")
                .then().log().body()
                .assertThat().contentType("application/json")
                .assertThat().statusCode(200)
                .assertThat().body("balance", equalTo("INR 50"));
    }
}