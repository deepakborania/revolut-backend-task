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
    public <E> E runInLock(int accID1, int accID2, Callable<E> callable) throws Exception {
        locks.putIfAbsent(accID1, new ReentrantLock());
        locks.putIfAbsent(accID2, new ReentrantLock());
        ReentrantLock lock1 = locks.get(accID1);
        ReentrantLock lock2 = locks.get(accID2);
        boolean bothLocksAcquired = false;
        while (!bothLocksAcquired) {
            if (lock1.tryLock()) {
                if (lock2.tryLock()) {
                    bothLocksAcquired = true;
                } else {
                    lock1.unlock();
                }
            }
            Thread.sleep(200);
        }
        try {
            return callable.call();
        } finally {
            lock2.unlock();
            lock1.unlock();
        }
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
