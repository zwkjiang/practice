package com.example.textview;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.adapter.TextAdapter;
import com.example.common.Contons;
import com.example.customview.MyListView;

import java.util.ArrayList;

@Route(path = Contons.SLIDE)
public class ScrollActivity extends BaseActivity {

    private MyListView list;

    @Override
    public void initView() {
        list = (MyListView) findViewById(R.id.list);
        ArrayList<String> name = new ArrayList<>();
        name.add("王同学");
        name.add("张同学");
        name.add("李同学");
        name.add("广同学");
        name.add("刘同学");
        name.add("王同学");
        name.add("王同学");
        name.add("张同学");
        name.add("李同学");
        name.add("广同学");
        name.add("刘同学");
        name.add("王同学");
        name.add("王同学");
        name.add("张同学");
        name.add("李同学");
        name.add("广同学");
        name.add("刘同学");
        name.add("王同学");
        TextAdapter textAdapter = new TextAdapter(name, R.layout.item_text, this);
        list.setAdapter(textAdapter);
        Animation animation = AnimationUtils.loadAnimation(ScrollActivity.this, com.example.custom.R.anim.animation_set);
        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation);
        layoutAnimationController.setDelay(0.5f);
        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
        list.setLayoutAnimation(layoutAnimationController);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_slide;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onClick(View v) {

    }
}
