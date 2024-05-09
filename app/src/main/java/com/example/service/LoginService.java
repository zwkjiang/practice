package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.aidl.UserImpl;

public class LoginService extends Service {
    private UserImpl userIBind;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return userIBind;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        userIBind = new UserImpl();
    }
}
