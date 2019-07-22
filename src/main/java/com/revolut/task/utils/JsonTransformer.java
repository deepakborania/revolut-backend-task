package com.revolut.task.utils;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * JsonTransformer is common transformer for all the responses returned from the Application Routes
 */
public class JsonTransformer implements ResponseTransformer {
    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }

}
