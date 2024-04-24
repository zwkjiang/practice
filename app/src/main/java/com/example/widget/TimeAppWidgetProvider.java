package com.example.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.textview.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeAppWidgetProvider extends AppWidgetProvider {
    public static final String CLICK_ACTION = "com.time.click.action";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(CLICK_ACTION)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("(HH:mm:ss)");
                        String format = simpleDateFormat.format(new Date(System.currentTimeMillis()));
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.time_now);
                        remoteViews.setTextViewText(R.id.tv_time,format);
                        AppWidgetManager instance = AppWidgetManager.getInstance(context);
                        instance.updateAppWidget(new ComponentName(context,TimeAppWidgetProvider.class),remoteViews);
                    }
                }
            }).start();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId :appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.time_text);
            Intent intent = new Intent();
            intent.setPackage(context.getPackageName());
            intent.setAction(CLICK_ACTION);
            PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, 0);
            remoteViews.setOnClickPendingIntent(R.id.iv_time,broadcast);
            appWidgetManager.updateAppWidget(appWidgetId,remoteViews);
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }


}
