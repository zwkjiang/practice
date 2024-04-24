package com.example.daggertest;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

@Module
public class GsonModule {
    @Provides
    public Gson providerGson(){
        return new Gson();
    }
}
