package com.example.common.lock;

public class MyLockThread extends Thread{

    private MyLockService myLockService;


    public MyLockThread(MyLockService service) {
        this.myLockService = service;
    }

    @Override
    public void run() {
        super.run();
        myLockService.await();
    }
}
