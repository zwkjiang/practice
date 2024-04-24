package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ManagerService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private static class MessageHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Log.i("ZWK","服务端收到信息:"+ msg.getData().getString("name"));
                    Messenger replyTo = msg.replyTo;
                    Message obtain = Message.obtain(null,1);
                    Bundle bundle = new Bundle();
                    bundle.putString("message","好的我知道你叫张三了");
                    obtain.setData(bundle);
                    try {
                        replyTo.send(obtain);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:
                    Log.i("ZWK","服务端收到信息:"+msg.getData().getString("message"));
                    Messenger replyTo1 = msg.replyTo;
                    Message obtain1 = Message.obtain(null,2);
                    Bundle bundle1= new Bundle();
                    bundle1.putString("update","已更新");
                    obtain1.setData(bundle1);
                    try {
                        replyTo1.send(obtain1);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                super.handleMessage(msg);
            }
        }
    }

    private final Messenger messenger = new Messenger(new MessageHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
