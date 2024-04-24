package com.example.common.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";

    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;

    private Context mContext;

    private static final boolean DEBUG = true;

    private static final String PATH = Environment.getExternalStorageDirectory().getPath()+"/CrashTest/log";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".trace";
    private static class Holder {
        public static CrashHandler crashHandler = new CrashHandler();
    }

    public static CrashHandler getInstance() {
        return Holder.crashHandler;
    }


    public void init(Context context) {
        mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        dumpExceptionToSDCard(e);
        if (mUncaughtExceptionHandler!=null){
            mUncaughtExceptionHandler.uncaughtException(t,e);
        }else {
            Process.killProcess(Process.myPid());
        }
    }

    private void dumpExceptionToSDCard(Throwable e) {
        Log.w(TAG, "dumpExceptionToSDCard");
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (DEBUG) {
                Log.w(TAG, "sdcard unmount  ed,skip dump exception");
            }
        }
        File dir = new File(PATH);
        if (!dir.exists()){
            dir.mkdirs();
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
        File file = new File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX);
        try {
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            printWriter.println(time);
            dumpPhoneInfo(printWriter);
            printWriter.println();
            e.printStackTrace(printWriter);
            printWriter.close();
        } catch (PackageManager.NameNotFoundException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void dumpPhoneInfo(PrintWriter printWriter) throws PackageManager.NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo info = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        printWriter.print("App Version: ");
        printWriter.print(info.versionName);
        printWriter.print('_');
        printWriter.println(info.versionCode);

        printWriter.print("OS Version: ");
        printWriter.print(Build.VERSION.RELEASE);
        printWriter.print("_");
        printWriter.println(Build.VERSION.SDK_INT);

        printWriter.print("Vendor: ");
        printWriter.println(Build.MANUFACTURER);

        printWriter.print("Model: ");
        printWriter.println(Build.MODEL);

        printWriter.print("CPU ABI: ");
        printWriter.println(Arrays.toString(Build.SUPPORTED_ABIS));
    }
}
