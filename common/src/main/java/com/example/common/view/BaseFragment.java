package com.example.common.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import java.lang.ref.WeakReference;

public abstract class BaseFragment extends Fragment implements BaseApi, View.OnClickListener {
    private MyHandler myHandler;

    protected View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(),container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mView = view;
        initView();
        initListener();
        initData();
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
    public void onDestroy() {
        super.onDestroy();
        if (myHandler != null) {
            myHandler = null;
        }
    }

    public static class MyHandler extends Handler {
        private final WeakReference<BaseFragment> mContext;

        private MyHandler(BaseFragment context) {
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


    protected abstract int getLayoutId();

}
