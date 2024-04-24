package com.example.daggertest;

import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Inject;

public class GoodService {

    public Gson gson;

    public Context context;

    @Inject
    public GoodService(Gson gson, Context context) {
        this.gson = gson;
        this.context = context;
    }

    public String getName(){
        return "你好";
    }

    public Gson getGson(){
        return gson;
    };
}
