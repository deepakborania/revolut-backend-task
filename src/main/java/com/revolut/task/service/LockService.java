package com.revolut.task.service;

import java.util.concurrent.Callable;

public interface LockService {
    <E> E runInLock(int accID1, int accID2, Callable<E> callable) throws Exception;

    <E> E runInLock(int accID1, Callable<E> callable) throws Exception;
}
