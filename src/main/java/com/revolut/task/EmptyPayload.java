package com.revolut.task;

public class EmptyPayload implements Validable {
    @Override
    public boolean isValid() {
        return true;
    }
}
