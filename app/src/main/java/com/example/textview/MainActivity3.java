package com.example.textview;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.aidl.CarBean;
import com.example.bean.GoodBean;
import com.example.common.Contons;
import com.example.common.Utils;
import com.example.custom.view.MyTextView;
import com.example.daggertest.DaggerGoodComponent;
import com.example.daggertest.GoodModule;
import com.example.daggertest.GoodService;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.inject.Inject;

@Route(path = Contons.MAIN)
public class MainActivity3 extends BaseActivity implements View.OnTouchListener {

    private Button button;
    private ImageView iv;
    @Autowired
    public String name;

    private MyTextView main3Tv;

    @Inject
    public GoodService goodService;

    @Inject
    public Gson gson;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main3;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        main3Tv =  findViewById(R.id.main3_tv);
        button =  findViewById(R.id.bt3);
        iv = findViewById(R.id.iv);
        button.setText(name);
        ss(new CarBean("",1,false));
        DaggerGoodComponent.builder().goodModule(new GoodModule(this)).build().inject2(this);
        Log.i("MainActivity3",""+goodService.getName());
        Log.i("MainActivity3","gson = "+gson.hashCode());
        
    }

    @Override
    public void initData() {
        Log.i("ZWK","number = "+ Utils.flag);
    }


    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                GoodBean goodBean = null;
                File file = new File(Environment.getExternalStorageDirectory() + "/cache.txt");
                if (file.exists()){
                    ObjectInputStream objectInputStream = null;
                    try {
                        objectInputStream = new ObjectInputStream(new FileInputStream(file));
                        goodBean = (GoodBean) objectInputStream.readObject();
                        button.setText(goodBean.getName());
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }finally {
                        try {
                            objectInputStream.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public void initListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                GoodBean goodBean = new GoodBean("电视",111,false);
                try {
                    File file = new File(Environment.getExternalStorageDirectory().getPath()+"/cache.txt");
                    file.createNewFile();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
                    objectOutputStream.writeObject(goodBean);
                    objectOutputStream.close();

                    ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
                    GoodBean o = (GoodBean) objectInputStream.readObject();
                    objectInputStream.close();
                    Log.i("ZWK",o.toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
//                Glide.with(MainActivity3.this).load("https://pic4.zhimg.com/v2-570aad7dbef96e49ab01780ab7ebc98b_r.jpg").into(iv);
            }
        });

        main3Tv.setOnClickListener(this);
        main3Tv.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main3_tv:
                Log.i("MainActivity3","onClick");
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("MainActivity3","onTouch->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("MainActivity3","onTouch->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("MainActivity3","onTouch->ACTION_UP");
                break;
        }
        return false;
    }

    private void ss(@NonNull CarBean str){
        Log.i("MainActivity3",str.toString());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("MainActivity3","dispatchTouchEvent->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("MainActivity3","dispatchTouchEvent->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("MainActivity3","dispatchTouchEvent->ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("MainActivity3","onTouchEvent->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("MainActivity3","onTouchEvent->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("MainActivity3","onTouchEvent->ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }
}