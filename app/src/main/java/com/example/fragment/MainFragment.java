package com.example.fragment;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.common.Contons;
import com.example.common.SizeUtils;
import com.example.common.UiUtils;
import com.example.common.view.BaseFragment;
import com.example.textview.MainActivity4;
import com.example.textview.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MainFragment extends BaseFragment{
    private TabLayout mTab;
    private ConstraintLayout mParentCl;

    private ImageView mAdvert;

    private Banner mBanner;

    private ArrayList<String> imgList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_advert_iv:
                Snackbar.make(mAdvert,"你好啊",Snackbar.LENGTH_LONG)
                        .setAction("Action",null)
                        .show();
//                ARouter.getInstance().build(Contons.RN).navigation();
                break;
        }
    }

    @Override
    public void initView() {
        mBanner = mView.findViewById(R.id.main_banner);
        mAdvert = mView.findViewById(R.id.main_advert_iv);
        mParentCl = mView.findViewById(R.id.main_cl);
        mTab = mView.findViewById(R.id.main_tb);
        int statusBarHeight = UiUtils.Holder.getInstance().getStatusBarHeight2(getContext());
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) mParentCl.getLayoutParams();
        lp.topMargin = statusBarHeight+10;
        mParentCl.setLayoutParams(lp);
        mBanner.setBannerAnimation(Transformer.CubeOut);
        mBanner.setDelayTime(1500);

    }

    @SuppressLint("CheckResult")
    @Override
    public void initData() {
        RequestOptions options = RequestOptions.bitmapTransform(new RoundedCorners(SizeUtils.dip2px(getContext(),10)));
        options.placeholder(R.drawable.rexiao);
        Glide.with(this).load(R.drawable.rexiao).apply(options).into(mAdvert);
        imgList = new ArrayList<>();
        imgList.add("http://t15.baidu.com/it/u=1355340936,3001744611&fm=224&app=112&f=JPEG?w=500&h=500");
        imgList.add("https://img2.baidu.com/it/u=1317447644,1784684947&fm=253&fmt=auto&app=138&f=JPEG?w=750&h=500");
        imgList.add("https://img2.baidu.com/it/u=2674476265,593878809&fm=253&fmt=auto?w=610&h=500");
        imgList.add("https://img2.baidu.com/it/u=3244114663,2718499546&fm=253&fmt=auto?w=500&h=410");
        mBanner.setImages(imgList);
        mBanner.start();
    }
    @Override
    public void initListener() {
        mBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });

        mAdvert.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("MainFragment","onDestroy");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MainFragment","onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("MainFragment","onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("MainFragment","onViewCreated");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("MainFragment","onPause");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i("MainFragment","onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("MainFragment","onDetach");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("MainFragment","onResume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("MainFragment","onDestroyView");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("MainFragment","onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("MainFragment","onStop");
        getUserVisibleHint();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}
