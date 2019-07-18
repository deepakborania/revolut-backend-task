package com.revolut.task;

import java.util.Map;

@FunctionalInterface
public interface RequestHandler<V extends Validable, R> {
    R process(V value, Map<String, String> urlParams);
}
