package com.example.common;

import android.app.Activity;

public class MyStack {
    private String[] item;

    private int count;

    private int n;

    public MyStack(int size) {
        this.n = size;
        item = new String[n];
        count = 0;
    }

    public boolean push(String activity) {
        if (count == n) {
            return false;
        }
        item[count] = activity;
        ++count;
        return true;
    }

    public String pop() {
        if (count == 0) {
            return null;
        }

        String activity = item[count - 1];
        --count;
        return activity;
    }
}
