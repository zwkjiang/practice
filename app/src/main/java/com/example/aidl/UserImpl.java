package com.example.aidl;

import android.os.RemoteException;
import android.util.Log;

import com.example.login.LoginUser;

public class UserImpl extends LoginUser.Stub{
    @Override
    public String login(String name, String password) throws RemoteException {
        Log.i("ZWK","name=" + name + "password="+password);
        return "登录成功";
    }
}
