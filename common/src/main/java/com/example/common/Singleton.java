package com.example.common;

import android.annotation.SuppressLint;
import android.content.Context;

public class Singleton {

    private Context context;

    private static volatile Singleton singleton;

    public static Singleton getInstance(Context context) {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton(context);
                }
            }
        }
        return singleton;
    }

    private Singleton(Context context){
        this.context = context;
    }
}
