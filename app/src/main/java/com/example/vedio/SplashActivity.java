package com.example.vedio;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.common.Contons;
import com.example.common.UiUtils;
import com.example.custom.runing.LockRunnable;
import com.example.textview.BaseActivity;
import com.example.textview.MainActivity2;
import com.example.textview.R;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity {
    private TextView mCountdown;

    private TextView splashTvJump;

    private final int SEND_COUNTDOWN = 0X11;
    private final int SEND_REFRESH_COUNTDOWN = 0X22;

    private Timer mTimer;
    @Override
    public void initView() {
        UiUtils.Holder.getInstance().setNavigationBarImmerse(getWindow());
        splashTvJump = findViewById(R.id.splash_tv_jump);
        mCountdown = findViewById(R.id.splash_tv_countdown);
        getMyHandler().sendEmptyMessage(SEND_COUNTDOWN);
        Message message = new Message();
        getMyHandler().sendMessage(message);
    }

    @Override
    public void initData() {
        LockRunnable lockRunnable = new LockRunnable();
        new Thread(lockRunnable,"A").start();
        new Thread(lockRunnable,"B").start();
        new Thread(lockRunnable,"C").start();
        new Thread(lockRunnable,"D").start();
        new Thread(lockRunnable,"E").start();
        new Thread(lockRunnable,"F").start();
        new Thread(lockRunnable,"H").start();
        new Thread(lockRunnable,"I").start();
        new Thread(lockRunnable,"J").start();
        new Thread(lockRunnable,"K").start();
        new Thread(lockRunnable,"L").start();
        new Thread(lockRunnable,"M").start();
        new Thread(lockRunnable,"N").start();
        new Thread(lockRunnable,"C").start();
    }

    @Override
    public void initListener() {
        splashTvJump.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.splash_activity;
    }



    private void beginCountdown() {
        mTimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            int count = 4;
            @Override
            public void run() {
                if (count == 0) {
                    ARouter.getInstance().build(Contons.HOME_PAGE).navigation();
                    finish();
                } else {
                    Message obtain = Message.obtain();
                    obtain.obj = count--;
                    obtain.what = SEND_REFRESH_COUNTDOWN;
                    getMyHandler().sendMessage(obtain);
                }
            }
        };
        mTimer.schedule(timerTask,1000,1000);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case SEND_COUNTDOWN:
                beginCountdown();
                break;
            case SEND_REFRESH_COUNTDOWN:
                mCountdown.setText(msg.obj.toString());
                break;
            default:break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.splash_tv_jump:
                ARouter.getInstance().build(Contons.HOME_PAGE).navigation();
                finish();
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);


    }
}
