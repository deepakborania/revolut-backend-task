package com.revolut.task.service;

import java.util.concurrent.Callable;

/**
 * Provides the Account level locking service
 */
public interface LockService {

    /**
     * Account locking service for transactions with two accounts involved
     *
     * @param accID1   First account ID to lock
     * @param accID2   Second account ID to lock
     * @param callable {@link Callable} to perform required operation
     * @param <E>      Type return by the callable
     * @return Whatever the callable returns
     * @throws Exception on any exception
     */
    <E> E runInLock(int accID1, int accID2, Callable<E> callable) throws Exception;

    /**
     * Account locking service for transactions with only one account involved/
     *
     * @param accID1 Account ID to lock
     * @param callable {@link Callable} to perform required operation
     * @param <E>      Type return by the callable
     * @return Whatever the callable returns
     * @throws Exception on any exception
     */
    <E> E runInLock(int accID1, Callable<E> callable) throws Exception;
}
