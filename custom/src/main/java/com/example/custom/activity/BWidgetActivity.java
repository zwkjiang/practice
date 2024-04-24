package com.example.custom.activity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.custom.CustomViewActivity;
import com.example.custom.R;

public class BWidgetActivity extends AppCompatActivity{
    private Button bt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_widget);
        bt = findViewById(R.id.bt1);
        bt.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"RemoteViewLayout","UnspecifiedImmutableFlag"})
            @Override
            public void onClick(View v) {
                RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.remote_layout);
                remoteViews.setTextViewText(R.id.remote_tv,"你好啊A");
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ff);
//                remoteViews.setImageViewBitmap(R.id.remote_img,bitmap);
                PendingIntent activity = PendingIntent.getActivity(
                        BWidgetActivity.this,
                        0,
                        new Intent(BWidgetActivity.this, CustomViewActivity.class),
                        PendingIntent.FLAG_CANCEL_CURRENT);
                remoteViews.setOnClickPendingIntent(R.id.remote_img,activity);
                Intent intent = new Intent();
                intent.putExtra("remoteView",remoteViews);
                intent.setAction("com.view.click.to");
                sendBroadcast(intent);
            }
        });
    }

}
