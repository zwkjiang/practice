package com.example.common;

import android.util.Log;

public class LogUtils {

    private static final String TAG = "ZWK";
    private static final boolean isDebug = BuildConfig.DEBUG;

    public static void i(String str) {
        if (isDebug) {
            Log.i(TAG,str);
        }
    }
}
