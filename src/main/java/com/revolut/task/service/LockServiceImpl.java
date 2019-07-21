package com.revolut.task.service;

import com.google.inject.Singleton;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Singleton
public class LockServiceImpl implements LockService {

    private final Map<Integer, ReentrantLock> locks = new ConcurrentHashMap<>();

    @Override
    public <E> E runInLock(int accID1, int accID2, Callable<E> callable) {
        return null;
    }

    @Override
    public <E> E runInLock(int accID1, Callable<E> callable) throws Exception {
        locks.putIfAbsent(accID1, new ReentrantLock());
        ReentrantLock lock = locks.get(accID1);
        lock.lock();
        try {
            return callable.call();
        } finally {
            lock.unlock();
        }
    }
}
