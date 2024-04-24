package com.example.common;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

@Interceptor(priority = 8,name = "text")
public class TextInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        postcard.withString("name","王五");
        callback.onContinue(postcard);
//        callback.onInterrupt(new Throwable("中断跳转"));
    }

    @Override
    public void init(Context context) {

    }
}
