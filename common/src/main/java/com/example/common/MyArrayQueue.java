package com.example.common;

public class MyArrayQueue {

    private String[] item;
    private int head;
    private int tail = 0;
    private int size = 0;

    public MyArrayQueue(int size) {
        item = new String[size];
        this.size = size;
    }

    public boolean enqueue(String str) {
        // 需要迁移
        if (tail == size) {
            // 队列已满
            if (head == 0) {
                return false;
            } else {
                for (int i = head; i < tail; i++) {
                    item[i-head] = item[i];
                }
                tail -=head;
                head = 0;
            }
        }

        item[tail] = str;
        ++tail;
        return true;
    }

    public String dequeue() {
        // 队列为空
        if (head == tail) {
            return null;
        }
        String str = item[head];
        head++;
        return str;
    }
}
