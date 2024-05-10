package com.example.broadcase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.common.LogUtils;

public class OtherBroadCase extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.i("收到广播了 + OtherBroadCase");
        abortBroadcast();
        Bundle bundle = new Bundle();
        bundle.putString("name","zwk");
        setResult(1,"上一个",bundle);
    }
}
