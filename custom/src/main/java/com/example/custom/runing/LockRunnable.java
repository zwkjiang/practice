package com.example.custom.runing;

import android.util.Log;

import java.util.concurrent.locks.ReentrantLock;

public class LockRunnable implements Runnable{

    private int number = 10;

    private ReentrantLock reentrantLock = new ReentrantLock();
    private void printNumber(){
        reentrantLock.lock();
        if (number >0) {
            number --;
            Log.i("LockRunning","ThreadName = "+Thread.currentThread().getName()+"  number = "+number);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            finally {
                reentrantLock.unlock();
            }
        }
    }
    @Override
    public void run() {
        for (int i=1;i<=10;i++){
            printNumber();
        }
    }
}
