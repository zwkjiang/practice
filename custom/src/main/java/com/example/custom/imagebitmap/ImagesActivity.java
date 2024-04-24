package com.example.custom.imagebitmap;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.GridView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common.Contons;
import com.example.custom.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

@Route(path = Contons.IMAGE_S)
public class ImagesActivity extends AppCompatActivity {

    private GridView imageGv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        imageGv = (GridView) findViewById(R.id.image_gv);
        ArrayList<String> strings = new ArrayList<>();
        strings.add("https://p.qqan.com/up/2023-12/17035715029409041.jpg");
        strings.add("https://p.qqan.com/up/2023-12/17035715029409041.jpg");
        ImageAdapter imageAdapter = new ImageAdapter(strings,R.layout.item_image,this);
        imageGv.setAdapter(imageAdapter);
        Log.i("ImagesActivity","onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("ImagesActivity","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ImagesActivity","onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("ImagesActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ImagesActivity","onDestroy");
    }
}
