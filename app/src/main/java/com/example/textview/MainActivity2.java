package com.example.textview;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.bean.GoodBean;
import com.example.common.Contons;
import com.example.common.UiUtils;
import com.example.common.SizeUtils;
import com.example.common.Utils;
import com.example.custom.CustomViewActivity;
import com.example.custom.activity.AWidgetActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity2 extends BaseActivity implements View.OnClickListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private Button bt;
    private Button bt2;
    private Button bt3;

    private Button bt4;
    private Button bt5;

    private Button bt6;
    private Button bt7;
    private Button bt8;
    private Button bt9;
    private Button bt10;

    private GestureDetector gestureDetector;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main2;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void initView() {
        UiUtils.Holder.getInstance().setStatusBarImmerse(getWindow(),false);
        UiUtils.Holder.getInstance().setNavigationBarImmerse(getWindow());
        bt = findViewById(R.id.bt);
        bt2 = findViewById(R.id.bt2);
        bt3 = findViewById(R.id.bt3);
        bt4 = findViewById(R.id.bt4);
        bt5= findViewById(R.id.bt5);
        bt6= findViewById(R.id.bt6);
        bt7= findViewById(R.id.bt7);
        bt8= findViewById(R.id.bt8);
        bt9= findViewById(R.id.bt9);
        bt10= findViewById(R.id.bt10);
        int slop = ViewConfiguration.get(this).getScaledEdgeSlop();
        gestureDetector = new GestureDetector(this);
        ContentResolver contentResolver = getContentResolver();
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        List<String> strings = new ArrayList<>();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                GoodBean goodBean = new GoodBean("苹果", 10, true);
                File file = new File(Environment.getExternalStorageDirectory() + "/test.txt");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File cache = new File(Environment.getExternalStorageDirectory() + "/cache.txt");
                try {
                    cache.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ObjectOutputStream objectInputStream = null;
                try {
                    objectInputStream = new ObjectOutputStream(new FileOutputStream(cache));
                    objectInputStream.writeObject(goodBean);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        objectInputStream.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void initListener() {
        bt.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        bt7.setOnClickListener(this);
        bt8.setOnClickListener(this);
        bt9.setOnClickListener(this);
        bt10.setOnClickListener(this);
    }


    private NavigationCallback navigationCallback = new NavigationCallback() {
        @Override
        public void onFound(Postcard postcard) {
            Log.i("ZWK", "onFound");
        }

        @Override
        public void onLost(Postcard postcard) {
            Log.i("ZWK", "onLost");
        }

        @Override
        public void onArrival(Postcard postcard) {
            Log.i("ZWK", "onArrival");
        }

        @Override
        public void onInterrupt(Postcard postcard) {
            Log.i("ZWK", "onInterrupt");
        }
    };

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
//                ARouter.getInstance().build(Contons.MAIN)
//                    .withString("name","张三")
//                    .navigation(this,navigationCallback);
                Utils.flag = 2;
                Intent intent = new Intent("android.intent.action.main3");
                ResolveInfo resolveInfo = getBaseContext().getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if (resolveInfo != null) {
                    startActivity(intent);
                }
                break;
            case R.id.bt2:
                ARouter.getInstance().build(Contons.MESSAGE)
                        .navigation();
                break;
            case R.id.bt3:
                ARouter.getInstance().build(Contons.SOCKET)
                        .navigation();
                break;
            case R.id.bt4:
                ARouter.getInstance().build(Contons.AIDL)
                        .navigation();
                break;
            case R.id.bt5:
                ARouter.getInstance().build(Contons.SLIDE)
                        .navigation();
                break;
            case R.id.bt6:
                ARouter.getInstance().build(Contons.CUSTOM)
                        .navigation();
                break;
            case R.id.bt8:
//                ARouter.getInstance().build(Contons.A_WIDGET)
//                        .navigation();
                Intent intent2 = new Intent(MainActivity2.this, AWidgetActivity.class);
                startActivity(intent2);
                overridePendingTransition(0,0);
                break;
            case R.id.bt7:
                NotificationManager systemService = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    NotificationChannel channel = new NotificationChannel("001", "中国", NotificationManager.IMPORTANCE_LOW);
                    channel.setDescription("描述");
                    channel.setShowBadge(true);
                    systemService.createNotificationChannel(channel);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Intent intent1 = new Intent(this, CustomViewActivity.class);
                    PendingIntent activity = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
                    RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notifytext_layout);
                    remoteViews.setOnClickPendingIntent(R.id.bt1,activity);
                    Notification build = new Notification.Builder(this, "001")
                            .setContentTitle("普通文本")
                            .setContentText("普通文本通知")
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setCustomContentView(remoteViews)
                            .setContentIntent(activity)
                            .setAutoCancel(true)
                            .setWhen(System.currentTimeMillis())
                            .build();
                    systemService.notify(1,build);
                }

                break;
            case R.id.bt9:
                ARouter.getInstance().build(Contons.IMAGE_S)
                        .navigation();
                break;
            case R.id.bt10:
                throw new RuntimeException("这是我自己制造的异常");
            default:
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean b = gestureDetector.onTouchEvent(event);
        VelocityTracker obtain = VelocityTracker.obtain();
        obtain.addMovement(event);
        obtain.computeCurrentVelocity(1000);
        float xVelocity = obtain.getXVelocity();
        float yVelocity = obtain.getYVelocity();
        Log.i("ZWK","xVelocity == "+xVelocity);
        Log.i("ZWK","yVelocity == "+yVelocity);
        obtain.clear();
        obtain.recycle();
        return b;
    }

    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        Log.i("gestureDetector","onDown");
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent e) {
        Log.i("gestureDetector","onShowPress");
    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(0,0);
    }
}