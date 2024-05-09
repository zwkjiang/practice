package com.example.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.example.textview.MainActivity4;
import com.example.textview.R;


public class NotificationService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        showNotificationChannel();
    }

    public void showNotificationChannel() {
        Intent intent = new Intent(this, MainActivity4.class);
        TaskStackBuilder intents = TaskStackBuilder.create(this);
        intents.addParentStack(MainActivity4.class);
        intents.addNextIntent(intent);
        PendingIntent pendingIntent = intents.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String channelId = "123_Channel";
            String description = "123";
            int importanceHigh = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(channelId, description, importanceHigh);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,100});
            manager.createNotificationChannel(notificationChannel);

            Notification build = new Notification.Builder(this, channelId)
                    .setSmallIcon(R.drawable.ff)
                    .setContentTitle("天气")
                    .setContentText("天气良好")
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build();

            manager.notify((int) System.currentTimeMillis(),build);
            startForeground(20,build);
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"20")
                    .setSmallIcon(R.drawable.ff)
                    .setContentTitle("天气")
                    .setContentText("天气良好");
            builder.setContentIntent(pendingIntent);
            Notification build = builder.build();
            manager.notify(20,build);
            startForeground(20,build);
        }
    }

}
