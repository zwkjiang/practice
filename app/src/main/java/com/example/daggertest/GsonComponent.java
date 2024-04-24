package com.example.daggertest;

import com.example.textview.MainActivity2;
import com.example.vedio.HomePageActivity;

import dagger.Component;

@Component(modules = GsonModule.class)
public interface GsonComponent {

    void inject(MainActivity2 homePageActivity);
}
