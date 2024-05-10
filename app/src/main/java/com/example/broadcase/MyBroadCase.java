package com.example.broadcase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.common.LogUtils;

public class MyBroadCase extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.i("收到广播了 + MyBroadCase");
        String resultData = getResultData();
        LogUtils.i("收到广播了 + MyBroadCase ==" +resultData);
    }
}
