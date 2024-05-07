package com.example.common.lock;

import android.util.Log;

import com.example.common.R;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyLockService {

    public boolean isFair;
    public ReentrantLock reentrantLock;

    public ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public Condition condition;
    public Condition conditionN;

    private String name;

    public MyLockService(){
        reentrantLock = new ReentrantLock();
        condition = reentrantLock.newCondition();
        conditionN = reentrantLock.newCondition();
    }

    public MyLockService(boolean isFair) {
        reentrantLock = new ReentrantLock(false);
        condition = reentrantLock.newCondition();
        conditionN = reentrantLock.newCondition();
        this.isFair = isFair;
    }

    public void await() {
        reentrantLock.lock();
        try {
            Log.i("ZWK", "await的时间为" + System.currentTimeMillis());
            condition.await();
            Log.i("ZWK", "这是condition.await执行完后，condition.signal()方法我才被执行");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            reentrantLock.unlock();
        }
    }

    public void signal() {
        reentrantLock.lock();
        try {
            Log.i("ZWK", "signal的时间为" + System.currentTimeMillis());
            condition.signal();
            Log.i("ZWK", "这是signal之后的语句");
            name = "刘大哥";
            Log.i("ZWK", "这是给name 赋值完后");
            condition.signal();
            conditionN.signal();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void getName() {
        Log.i("ZWK", "getName");
        try {
            condition.await();
            Log.i("ZWK", "name = " + name);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            reentrantLock.unlock();
        }
    }

    public void signalN() {
        Log.i("ZWK", "signalN");
        reentrantLock.lock();
        try {
            condition.signal();
            Log.i("ZWK", "signalN 释放后");
        } finally {
            reentrantLock.unlock();
        }
    }

    // ----------------------------------------------------------------

    private volatile int nextPrintWo = 1;

    private ReentrantLock lock = new ReentrantLock();

    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();

    public void moreThread() {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    Log.i("ZWK", "ThreadA");
                    while (nextPrintWo != 1) {
                        Log.i("ZWK", "ThreadA wait");
                        conditionA.await();
                    }
                    for (int i = 0; i < 3; i++) {
                        Log.i("ZWK", "ThreadA" + (i + 1));
                    }
                    nextPrintWo = 2;
                    conditionB.signalAll();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    Log.i("ZWK", "ThreadB");
                    while (nextPrintWo != 2) {
                        Log.i("ZWK", "ThreadB wait");
                        conditionB.await();
                    }
                    for (int i = 0; i < 3; i++) {
                        Log.i("ZWK", "ThreadB" + (i + 1));
                    }
                    nextPrintWo = 3;
                    conditionC.signalAll();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread threadC = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    Log.i("ZWK", "ThreadC");
                    while (nextPrintWo != 3) {
                        Log.i("ZWK", "ThreadC wait");
                        conditionC.await();
                    }
                    for (int i = 0; i < 3; i++) {
                        Log.i("ZWK", "ThreadC" + (i + 1));
                    }
                    nextPrintWo = 1;
                    conditionA.signalAll();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread[] aArray = new Thread[5];
        Thread[] bArray = new Thread[5];
        Thread[] cArray = new Thread[5];

        for (int i = 0; i < 5; i++) {
            aArray[i] = new Thread(threadA);
            bArray[i] = new Thread(threadB);
            cArray[i] = new Thread(threadC);

            aArray[i].start();
            bArray[i].start();
            cArray[i].start();
        }
    }

    public void fairLock() {
        try {
            reentrantLock.lock();
            Log.i("ZWK","Thread name "+Thread.currentThread().getName() + " 获得锁定");
        } finally {
            reentrantLock.unlock();
        }
    }

    public void read() {
        try {
            reentrantReadWriteLock.readLock().lock();
            Log.i("ZWK","Thread 获得读锁 "+Thread.currentThread().getName() + " 时间:"+System.currentTimeMillis());
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    public void write() {
        try {
            reentrantReadWriteLock.writeLock().lock();
            Log.i("ZWK","Thread 获得写锁 "+Thread.currentThread().getName() + " 时间:"+System.currentTimeMillis());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }
}
