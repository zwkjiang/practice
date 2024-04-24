package com.example.common.executor;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorManager {
    private ExecutorManager() {
        createCacheThreadExecutor();
        createFixedThreadExecutor();
        createSingleThreadExecutor();
        createScheduleThreadExecutor();
    }

    private static WeakReference<Context> mContext;

    private static volatile ExecutorManager mExecutorManager;

    public static ExecutorManager getExecutorManager(Context context) {
        mContext = new WeakReference<>(context);
        if (mExecutorManager == null) {
            synchronized (ExecutorManager.class) {
                if (mExecutorManager == null) {
                    mExecutorManager = new ExecutorManager();
                }
            }
        }
        return mExecutorManager;
    }

    public void setFixedThread(Runnable runnable) {
        mFixedThread.execute(runnable);
    }

    public void setScheduleThread(Runnable runnable, long time, TimeUnit unit) {
        mScheduleThread.schedule(runnable, time, unit);
    }

    public void setScheduleThread(Runnable runnable, long initialDelay, long period, TimeUnit unit) {
        mScheduleThread.scheduleAtFixedRate(runnable, initialDelay, period, unit);
    }

    public void setScheduleThread(Runnable runnable) {
        mScheduleThread.execute(runnable);
    }

    private ExecutorService mFixedThread;
    private ExecutorService mCacheThread;

    private ScheduledExecutorService mScheduleThread;

    private ExecutorService mSingleThread;

    private void createFixedThreadExecutor() {
        if (mFixedThread == null) {
            mFixedThread = Executors.newFixedThreadPool(10);
        }
    }

    private void createCacheThreadExecutor() {
        if (mCacheThread == null) {
            mCacheThread = Executors.newCachedThreadPool();
        }
    }

    private void createScheduleThreadExecutor() {
        if (mScheduleThread == null) {
            mScheduleThread = Executors.newScheduledThreadPool(10);
        }
    }

    private void createSingleThreadExecutor() {
        if (mSingleThread == null) {
            mSingleThread = Executors.newSingleThreadExecutor();
        }
    }
}
