package com.example.custom.activity;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common.Contons;
import com.example.common.executor.ExecutorManager;
import com.example.custom.R;

import org.xmlpull.v1.XmlPullParser;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Route(path = Contons.A_WIDGET)
public class AWidgetActivity extends AppCompatActivity {
    private LinearLayout ly;

    private Button bt;
    private Button bt2;
    private ImageView iv;
    private TextView tv;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;
    private Button bt3;
    private Button bt4;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            RemoteViews remoteView = intent.getParcelableExtra("remoteView");
            if (remoteView != null){
                View apply = remoteView.apply(AWidgetActivity.this, ly);
                ly.addView(apply);
            }
        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_widget);
        bt = (Button) findViewById(R.id.bt);
        ly = (LinearLayout) findViewById(R.id.ly);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.view.click.to");
        registerReceiver(mBroadcastReceiver,intentFilter);
        tv = (TextView) findViewById(R.id.tv);
        bt2 = (Button) findViewById(R.id.bt2);
        iv = (ImageView) findViewById(R.id.iv);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv4 = (ImageView) findViewById(R.id.iv4);
        iv5 = (ImageView) findViewById(R.id.iv5);
        bt3 = (Button) findViewById(R.id.bt3);
        bt4 = (Button) findViewById(R.id.bt4);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Settings.canDrawOverlays(AWidgetActivity.this)){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    startActivity(intent);
                }
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionDrawable background = (TransitionDrawable) tv.getBackground();
                background.startTransition(10000);
                Animation animation = AnimationUtils.loadAnimation(AWidgetActivity.this, R.anim.animation_set);
                animation.setDuration(2000);
                iv.startAnimation(animation);
                AnimationDrawable background1 = (AnimationDrawable) iv2.getBackground();
                background1.start();
                ObjectAnimator.ofFloat(iv3,"translationY",-iv3.getHeight()).start();
                ObjectAnimator backgroundColor = ObjectAnimator.ofInt(iv4,
                        "backgroundColor",
                        /*Red*/0xFFF8080,
                        /*Blue*/0xFF8080ff);
                backgroundColor.setDuration(3000);
                backgroundColor.setEvaluator(new ArgbEvaluator());
                backgroundColor.setRepeatCount(ValueAnimator.INFINITE);
                backgroundColor.setRepeatMode(ValueAnimator.RESTART);
                backgroundColor.start();
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(
                        ObjectAnimator.ofFloat(iv5,"rotationY",0,360),
                        ObjectAnimator.ofFloat(iv5,"rotationX",0,360),
                        ObjectAnimator.ofFloat(iv5,"rotation",0,-90),
                        ObjectAnimator.ofFloat(iv5,"translationX",0,90),
                        ObjectAnimator.ofFloat(iv5,"translationY",0,90),
                        ObjectAnimator.ofFloat(iv5,"scaleX",1,0.5f),
                        ObjectAnimator.ofFloat(iv5,"scaleY",1,0.5f),
                        ObjectAnimator.ofFloat(iv5,"alpha",1,0.25f,1)
                );
                animatorSet.setDuration(5000).start();
                com.nineoldandroids.animation.ValueAnimator valueAnimator = com.nineoldandroids.animation.ValueAnimator.ofInt(0, 100);
                valueAnimator.addUpdateListener(new com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener() {
                    private IntEvaluator intEvaluator = new IntEvaluator();
                    @Override
                    public void onAnimationUpdate(com.nineoldandroids.animation.ValueAnimator animation) {
                        int animatedValue = (int) animation.getAnimatedValue();
                        float animatedFraction = animation.getAnimatedFraction();
                        bt2.getLayoutParams().width  = intEvaluator.evaluate(animatedFraction, bt2.getWidth(), 500);
                        bt2.requestLayout();
                    }
                });
                valueAnimator.setDuration(3000);
                valueAnimator.start();
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AWidgetActivity.this, BWidgetActivity.class);
                startActivity(intent);
            }
        });

    }

    private Button button;
    private void initView() {
        button = new Button(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View inflate = getLayoutInflater().inflate(R.layout.activity_custom, null,false);
                inflate.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
                PopupWindow popupWindow = new PopupWindow(inflate,2000,2000);
//                popupWindow.setContentView(inflate);
//                popupWindow.setWidth(500);
//                popupWindow.setHeight(500);
                popupWindow.showAtLocation(ly,Gravity.BOTTOM,100,100);
            }
        });

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                0,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
                PixelFormat.TRANSPARENT);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type =WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.x = 100;
        layoutParams.y = 300;
        WindowManager windowManager = getWindow().getWindowManager();
        button.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        layoutParams.x = x;
                        layoutParams.y = y;
                        windowManager.updateViewLayout(button,layoutParams);
                        break;
                }
                return false;
            }
        });
        windowManager.addView(button,layoutParams);
        ThreadLocal<Boolean> booleanThreadLocal = new ThreadLocal<>();
        booleanThreadLocal.set(true);
        Boolean aBoolean = booleanThreadLocal.get();
        new Thread(new Runnable() {
            @Override
            public void run() {

                booleanThreadLocal.set(false);
                Boolean aBoolean1 = booleanThreadLocal.get();
                Log.i("ZWK","ThreadName"+Thread.currentThread().getName()+"--value"+aBoolean1);
            }
        },"ZWK").start();
        Log.i("ZWK","ThreadName"+Thread.currentThread().getName()+"--value"+aBoolean);
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("123", "456", "789");
        HandlerThread zwk = new HandlerThread("Zwk");
        zwk.start();
        Handler handler = new Handler(zwk.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == 1){
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Log.i("ZWK","ZZZZZ");
                }
                return true;
            }
        });
        handler.sendEmptyMessage(1);
        zwk.quit();
        Intent intent = new Intent(this,MyServiceIntent.class);
        intent.putExtra("zwk","真好");
        startService(intent);
        Intent intent2 = new Intent(this,MyServiceIntent.class);
        intent2.putExtra("zwk","真好2");
        startService(intent2);
        Intent intent3 = new Intent(this,MyServiceIntent.class);
        intent3.putExtra("zwk","真好3");
        startService(intent3);
        EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Log.i("ZWK","--threadName"+Thread.currentThread().getName());
            }
        });
        EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Log.i("ZWK","--threadName"+Thread.currentThread().getName());
            }
        });
        EXECUTOR.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Log.i("ZWK","--threadName"+Thread.currentThread().getName());
            }
        });
        ExecutorManager.getExecutorManager(this).setFixedThread(new Runnable() {
            @Override
            public void run() {
                Log.i("ZWK","--FixedThreadName"+Thread.currentThread().getName());
            }
        });
        ExecutorManager.getExecutorManager(this).setScheduleThread(new Runnable() {
            @Override
            public void run() {
                Log.i("ZWK","--ScheduleThreadName"+Thread.currentThread().getName());
            }
        },10000,TimeUnit.MILLISECONDS);
        ExecutorManager.getExecutorManager(this).setScheduleThread(new Runnable() {
            @Override
            public void run() {
                Log.i("ZWK","--ScheduleThreadName"+Thread.currentThread().getName());
            }
        },100,1000,TimeUnit.MILLISECONDS);
    }


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        button.setVisibility(View.GONE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        button.setVisibility(View.VISIBLE);
    }

    static class MyAsyncTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Log.i("ZWK",""+o.toString());
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(Object o) {
            super.onCancelled(o);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            return objects;
        }
    }

    public static class MyServiceIntent extends IntentService{



        public MyServiceIntent(){
            super("");
        }

        /**
         * Creates an IntentService.  Invoked by your subclass's constructor.
         *
         * @param name Used to name the worker thread, important only for debugging.
         *
         */
        public MyServiceIntent(String name) {
            super(name);
        }

        @Override
        public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        protected void onHandleIntent(@Nullable Intent intent) {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            assert intent != null;
            Log.i("ZWK",""+intent.getStringExtra("zwk"));
        }
    }

    private final int CORE_POOL_SIZE = 5;
    private final int MAX_POOL_SIZE = 20;
    private final int KEEP_ALIVE_SECONDS = 3;

    private ThreadFactory mThreadFactory = new ThreadFactory() {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"AWidgetActivity"+atomicInteger.getAndIncrement());
        }
    };


    private BlockingQueue<Runnable> mBlockingQueue = new LinkedBlockingQueue<>();

    private ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_SECONDS,
            TimeUnit.SECONDS,
            mBlockingQueue,
            mThreadFactory
    );
}
