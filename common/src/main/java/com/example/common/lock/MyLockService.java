package com.example.common.lock;

import android.util.Log;

import com.example.common.R;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyLockService {

    public ReentrantLock reentrantLock = new ReentrantLock();

    public Condition condition = reentrantLock.newCondition();
    public Condition conditionN = reentrantLock.newCondition();

    private String name;

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
                    Log.i("ZWK","ThreadA");
                    while (nextPrintWo != 1) {
                        conditionA.await();
                        Log.i("ZWK","ThreadA wait");
                    }
                   for (int i = 0; i < 3; i++) {
                       Log.i("ZWK","ThreadA" + (i + 1));
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
                    Log.i("ZWK","ThreadB");
                    while (nextPrintWo != 2) {
                        conditionB.await();
                        Log.i("ZWK","ThreadB wait");
                    }
                    for (int i = 0; i < 3; i++) {
                        Log.i("ZWK","ThreadB" + (i + 1));
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
                    Log.i("ZWK","ThreadC");
                    while (nextPrintWo != 3) {
                        conditionC.await();
                        Log.i("ZWK","ThreadC wait");
                    }
                    for (int i = 0; i < 3; i++) {
                        Log.i("ZWK","ThreadC" + (i + 1));
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
}
