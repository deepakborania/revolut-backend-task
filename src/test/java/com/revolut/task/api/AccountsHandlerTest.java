package com.revolut.task.api;

import com.revolut.task.AppServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

class AccountsHandlerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsHandlerTest.class);

    private static AppServer appServer = new AppServer();

    @BeforeAll
    public static void setup() {
        Spark.stop();
        appServer.startServer();
        Spark.awaitInitialization();
    }

    @Test
    void createAccountTest() {
        given()
                .log().all()
                .body("{\"name\": \"Savings Account\", \"currencyCode\": \"GBP\"}")
                .post("/api/accounts/create")
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
                        .post("/api/accounts/create")
                        .then()
                        .log().body()
                        .assertThat().statusCode(200)
                        .assertThat().contentType("application/json")
                        .assertThat().body("accountId", notNullValue())
                        .assertThat().body("accountId", greaterThan(0));

            });
        }
        try {
            executorService.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

}