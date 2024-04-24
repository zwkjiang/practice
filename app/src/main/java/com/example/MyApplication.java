package com.example;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common.BuildConfig;
import com.example.common.exception.CrashHandler;
import com.example.daggertest.DaggerGoodComponent;
import com.example.daggertest.GoodComponent;
import com.example.daggertest.GoodModule;

import me.jessyan.autosize.AutoSizeConfig;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.openDebug();
        ARouter.openLog();
        ARouter.init(this);
        AutoSizeConfig.getInstance().setExcludeFontScale(false);
//        AutoSizeConfig.getInstance().setPrivateFontScale(3);
        AutoSizeConfig.getInstance().setLog(true);
//        AutoSizeConfig.getInstance().setBaseOnWidth(true);
//        AutoSizeConfig.getInstance().stop(this);
//        AutoSizeConfig.getInstance().restart();
//        AutoSizeConfig.getInstance().setUseDeviceSize(true);
            CrashHandler.getInstance().init(this);
            if (BuildConfig.DEBUG) {
                // 开启线程模式
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
                // 开启虚拟机模式
                StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
//                BlockCanary.install(this,new AppBlockCanary()).start();
            }
        GoodComponent build = DaggerGoodComponent.builder().goodModule(new GoodModule(this)).build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
