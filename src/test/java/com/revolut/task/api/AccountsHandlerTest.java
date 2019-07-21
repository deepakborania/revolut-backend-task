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
import static org.hamcrest.Matchers.*;

public class AccountsHandlerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsHandlerTest.class);

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
    void createAccountTest() {
        given()
                .log().all()
                .body("{\"name\": \"Savings Account\", \"currencyCode\": \"GBP\"}")
                .post("/api/accounts")
                .then()
                .log().body()
                .assertThat().contentType("application/json")
                .assertThat().body("accountId", notNullValue())
                .assertThat().body("accountId", greaterThan(0));
    }

    @Test
    void multiCreateAccountTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 70; i++) {
            executorService.execute(() -> {

                given()
//                        .log().all()
                        .body("{\"name\": \"Savings Account\", \"currencyCode\": \"GBP\"}")
                        .post("/api/accounts")
                        .then()
                        .log().body()
                        .assertThat().statusCode(200)
                        .assertThat().contentType("application/json")
                        .assertThat().body("accountId", notNullValue())
                        .assertThat().body("accountId", greaterThan(0));

            });
        }
        try {
            executorService.shutdown();
            executorService.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    @Test
    void multiDepositAmount() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 70; i++) {
            executorService.submit(() -> {
                given()
                        .put("/api/accounts/10001/deposit/GBP/100")
                        .then()
                        .log().body()
                        .assertThat().contentType("application/json")
                        .assertThat().statusCode(200);
            });
        }
        try {
            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
        given()
                .get("/api/accounts/10001")
                .then().log().body()
                .assertThat().contentType("application/json")
                .assertThat().statusCode(200)
                .assertThat().body("balance", equalTo("GBP 7000"));
    }

    @Test
    void multiWithdrawAmount() {

        given()
                .put("/api/accounts/10001/deposit/GBP/100")
                .then()
                .log().body()
                .assertThat().contentType("application/json")
                .assertThat().statusCode(200);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 101; i++) {
            executorService.submit(() -> {
                given()
                        .put("/api/accounts/10001/withdraw/GBP/1")
                        .then()
                        .log().body()
                        .assertThat().contentType("application/json")
                        .assertThat().statusCode(200);
            });
        }
        try {
            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
        given()
                .get("/api/accounts/10001")
                .then().log().body()
                .assertThat().contentType("application/json")
                .assertThat().statusCode(200)
                .assertThat().body("balance", equalTo("GBP 0"));
    }
}