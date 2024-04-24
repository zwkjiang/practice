package com.example.textview;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.aidl.CarBean;
import com.example.aidl.ICarManager;
import com.example.aidl.IOnNewCarArrivedListener;
import com.example.common.Contons;
import com.example.contentProvider.BookProvider;
import com.example.service.CarManagerService;
import com.example.service.ManagerService;

import java.util.List;

@Route(path = Contons.MESSAGE)
public class MessageActivity extends BaseActivity implements View.OnClickListener {

    private Button bt;
    private Messenger messenger;

    private Messenger mGetMessenger = new Messenger(new MessengerHandler());

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt:
                Message obtain = Message.obtain(null, 2);
                Bundle bundle = new Bundle();
                bundle.putString("message","请更新内容");
                obtain.setData(bundle);
                obtain.replyTo = mGetMessenger;
                try {
                    messenger.send(obtain);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle data = msg.getData();
            switch (msg.what){
                case 1:
                    String message = data.getString("message");
                    Log.i("ZWK","客户端收到信息:"+message);
                    break;
                case 2:
                    String update = data.getString("update");
                    Log.i("ZWK","客户端收到信息:"+update);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            Message obtain = Message.obtain(null, 1);
            Bundle bundle = new Bundle();
            bundle.putString("name","张三");
            obtain.setData(bundle);
            obtain.replyTo=mGetMessenger;
            try {
                messenger.send(obtain);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

//--------------------------------------------------------------------------------------------------------------
   private ICarManager iCarManager;

   private Handler mHandler = new Handler(){
       @SuppressLint("HandlerLeak")
       @Override
       public void handleMessage(@NonNull Message msg) {
           super.handleMessage(msg);
           switch (msg.what){
               case 1:
                   Log.i("ZWK",msg.obj.toString());
                   break;
               default:break;
           }
       }
   };

    private ServiceConnection serviceConnection1 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ICarManager carManager = ICarManager.Stub.asInterface(service);
            try {
                iCarManager = carManager;
                List<CarBean> car = carManager.getCar();
                Log.i("ZWK",car.toString());
                carManager.addCar(new CarBean("山地车",5000,false));
                List<CarBean> car1 = carManager.getCar();
                Log.i("ZWK",car1.toString());
                iCarManager.registerListener(iOnNewCarArrivedListener);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iCarManager = null;
        }
    };

    private IOnNewCarArrivedListener iOnNewCarArrivedListener = new IOnNewCarArrivedListener.Stub(){

        @Override
        public void onNewCarArrived(CarBean newCarBean) throws RemoteException {
            mHandler.obtainMessage(1,newCarBean).sendToTarget();
        }
    };

    @SuppressLint("Recycle")
    @Override
    public void initView() {
        bt = findViewById(R.id.bt);
        Intent intent = new Intent(this, ManagerService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
        Intent intent1 = new Intent(this, CarManagerService.class);
        bindService(intent1,serviceConnection1, Context.BIND_AUTO_CREATE);
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",6);
        contentValues.put("name","程序艺术设计");
        Cursor query = getContentResolver().query(BookProvider.BOOK_CONTENT, new String[]{"id", "name"}, null, null, null);
        while (query.moveToNext()){
            int anInt = query.getInt(0);
            String string = query.getString(1);
            Log.i("ZWK","book query id = "+anInt+" name = "+string);
        }
        query.close();
        Cursor queryUser = getContentResolver().query(BookProvider.USER_CONTENT, new String[]{"id", "name","sex"}, null, null, null);
        while (queryUser.moveToNext()){
            int anInt = queryUser.getInt(0);
            String string = queryUser.getString(1);
            int anInt1 = queryUser.getInt(2);
            Log.i("ZWK","user query id = "+anInt+" name = "+string + "sex = " +anInt1);
        }
        queryUser.close();
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        bt.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onDestroy() {
        if (iCarManager!=null &&iCarManager.asBinder().isBinderAlive()){
            try {
                Log.i("ZWK","unregisterListener == " +iOnNewCarArrivedListener);
                iCarManager.unregisterListener(iOnNewCarArrivedListener);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        unbindService(serviceConnection);
        unbindService(serviceConnection1);
        super.onDestroy();
    }

    class MyAsyncTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }
    }
}
