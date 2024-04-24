package com.example.daggertest;

import com.example.textview.MainActivity3;
import com.example.vedio.HomePageActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = GoodModule.class)
@Singleton
public interface GoodComponent {

    void injcet(HomePageActivity homePageActivity);

    void inject2(MainActivity3 mainActivity3);
}
