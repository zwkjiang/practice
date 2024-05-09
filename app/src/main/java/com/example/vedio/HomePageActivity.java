package com.example.vedio;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.example.adapter.HomePageAdapter;
import com.example.aidl.UserImpl;
import com.example.api.MainLifecycle;
import com.example.common.Contons;
import com.example.common.UiUtils;
import com.example.common.view.BaseFragment;
import com.example.daggertest.DaggerGoodComponent;
import com.example.daggertest.GoodModule;
import com.example.daggertest.GoodService;
import com.example.fragment.MainFragment;
import com.example.fragment.MineFragment;
import com.example.login.LoginUser;
import com.example.service.LoginService;
import com.example.textview.BaseActivity;
import com.example.textview.R;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;

import javax.inject.Inject;

@Route(path = Contons.HOME_PAGE)
public class HomePageActivity extends BaseActivity {
    private ViewPager2 mHomeViewPage;
    private TabLayout mHomeTab;

    private HomePageAdapter mFragmentAdapter;

    private ArrayList<BaseFragment> mFragmentList;

    @Inject
    public GoodService mGoodService;

    @Inject
    public Gson gson;

    @Inject
    public Gson gson2;

    private final int MAIN_PAGE = 0;

    private final int MINE_PAGE = 1;
    @Override
    public void initView() {
        UiUtils.Holder.getInstance().setStatusBarImmerse(getWindow(),true);
        mHomeViewPage = findViewById(R.id.home_view_page);
        mHomeTab = findViewById(R.id.home_tab);
        createFragment();
        mHomeViewPage.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mHomeViewPage.setOffscreenPageLimit(mFragmentList.size());
        mHomeViewPage.setAdapter(mFragmentAdapter);
        mHomeViewPage.setUserInputEnabled(false);
        createTab();
        DaggerGoodComponent.builder().goodModule(new GoodModule(this)).build().injcet(this);
        Log.i("HomePageActivity",""+mGoodService.getName());
        Log.i("HomePageActivity","gson = "+gson.hashCode());
        Log.i("HomePageActivity","gson2 = "+gson2.hashCode());

    }

    @SuppressLint("MissingInflatedId")
    private void createTab() {
        mHomeTab.getTabAt(MAIN_PAGE).setCustomView(setItemTab(MAIN_PAGE));
        mHomeTab.getTabAt(MAIN_PAGE).setTag("0");
        mHomeTab.getTabAt(MINE_PAGE).setCustomView(setItemTab(MINE_PAGE));
        mHomeTab.getTabAt(MINE_PAGE).setTag("1");
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private View setItemTab(int position) {
        View homeTab = getLayoutInflater().inflate(R.layout.tab_item_layout, null, false);
        TextView itemTv = homeTab.findViewById(R.id.tab_item_tv);
        ImageView itemIv = homeTab.findViewById(R.id.tab_item_iv);
        if (position == MAIN_PAGE) {
            itemTv.setText("首页");
            Glide.with(this).load(R.drawable.home).into(itemIv);
        } else {
            itemTv.setText("个人");
            Glide.with(this).load(R.drawable.mine1).into(itemIv);
        }
        return homeTab;
    }

    private void createFragment() {
        mFragmentList = new ArrayList<>();
        MainFragment mainFragment = new MainFragment();
        Bundle mainBundle = new Bundle();
        mainFragment.setArguments(mainBundle);
        mFragmentList.add(mainFragment);
        MineFragment mineFragment = new MineFragment();
        Bundle minBundle = new Bundle();
        mineFragment.setArguments(minBundle);
        mFragmentList.add(mineFragment);
        mFragmentAdapter = new HomePageAdapter(getSupportFragmentManager(),getLifecycle(),mFragmentList);
    }

    @Override
    public void initData() {

    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initListener() {
        mHomeViewPage.registerOnPageChangeCallback(mPageCallback);
        mHomeTab.addOnTabSelectedListener(mTabListener);
        getLifecycle().addObserver(new MainLifecycle());
    }

    private final TabLayout.OnTabSelectedListener mTabListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mHomeViewPage.setCurrentItem(tab.getPosition(),true);
            changeTabSelect(tab,true);
            switch (tab.getPosition()) {
                case MAIN_PAGE:
                    break;
                case MINE_PAGE:
                    break;
                default:break;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            changeTabSelect(tab,false);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };


    private void changeTabSelect(TabLayout.Tab tab,Boolean isSelect) {
        View customView = tab.getCustomView();
        TextView itemTv = customView.findViewById(R.id.tab_item_tv);
        ImageView itemIv = customView.findViewById(R.id.tab_item_iv);
        if (isSelect) {
            itemTv.setTextColor(Color.parseColor("#DD2C00"));
            if (tab.getTag() == "0") {
                Glide.with(this).load(R.drawable.home).into(itemIv);
            } else {
                Glide.with(this).load(R.drawable.mine).into(itemIv);
            }
        } else {
            itemTv.setTextColor(getColor(R.color.black));
            if (tab.getTag() == "0") {
                Glide.with(this).load(R.drawable.home1).into(itemIv);
            } else {
                Glide.with(this).load(R.drawable.mine1).into(itemIv);
            }
        }

    }

    private final ViewPager2.OnPageChangeCallback mPageCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            mHomeTab.setScrollPosition(position,positionOffset,true);
            switch (position){
                case MAIN_PAGE:
                    break;
                case MINE_PAGE:
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            mHomeTab.selectTab(mHomeTab.getTabAt(position),false);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }
    };
}
