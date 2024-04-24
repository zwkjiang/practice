package com.example.textview;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import com.example.common.view.BaseApi;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public abstract class BaseActivity extends AppCompatActivity implements BaseApi, View.OnClickListener {
    private MyHandler myHandler;

    protected Boolean isBindingView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        if (!isBindingView) {
            setContentView(getLayoutId());
        }
        initView();
        initListener();
        initData();
    }

    protected void setBinding() {

    }

    private void createHandler(){
        myHandler = new MyHandler(this);
    }

    public synchronized MyHandler getMyHandler() {
        if (myHandler == null) {
            createHandler();
        }
        return myHandler;
    }

    public void handleMessage(Message msg) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(this);
        }
    }

    public static class MyHandler extends Handler {
        private final WeakReference<BaseActivity> mContext;

        private MyHandler(BaseActivity context) {
            super();
            mContext = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (mContext.get() != null) {
                mContext.get().handleMessage(msg);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    protected abstract @LayoutRes int getLayoutId();
}