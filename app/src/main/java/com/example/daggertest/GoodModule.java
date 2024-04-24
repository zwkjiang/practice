package com.example.daggertest;

import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GoodModule {

    Context context;

    public GoodModule(Context context){
        this.context = context;
    }

    @Provides
    public Context provideContext(){
        return context;
    }

    @Provides
    @Singleton
    public Gson provideGson(){
        return new Gson();
    }

    @Provides
    public GoodService provideGoodService(Gson gson,Context context){
        return new GoodService(gson,context);
    }
}
