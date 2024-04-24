package com.example.common;

public class MyArrayQueueRing {
    private String[] item;

    private int head = 0;

    private int tail = 0;

    private int size = 0;

    public MyArrayQueueRing(int size) {
        item = new String[size];
        this.size = size;
    }

    public boolean enqueue(String str){
        if ((tail + 1) % size == head) {
            // 队列已满
            return false;
        }
        item[tail] = str;
        tail = (tail + 1) % size;
        return true;
    }

    public String dequeue() {
        if (head == tail) {
            // 队列为空
            return null;
        }
        String str = item[head];
        head = (head + 1) % size;
        return str;
    }
}
