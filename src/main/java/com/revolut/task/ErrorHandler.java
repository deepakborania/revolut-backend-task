package com.revolut.task;

import com.google.gson.Gson;
import com.revolut.task.api.BaseResponse;
import com.revolut.task.exceptions.AccountNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

class ErrorHandler implements ExceptionHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);
    private final Gson gson = new Gson();

    @Override
    public void handle(Exception e, Request request, Response response) {
        BaseResponse resp = new BaseResponse();
        resp.setMessage(e.getMessage());
        LOGGER.error("Server exception", e);
        response.status(500);
        response.body(gson.toJson(resp));
    }

}
