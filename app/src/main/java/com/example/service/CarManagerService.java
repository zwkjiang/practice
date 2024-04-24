package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.aidl.CarBean;
import com.example.aidl.ICarManager;
import com.example.aidl.IOnNewCarArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class CarManagerService extends Service {

    private static final String TAG = "CMS";

    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);

    // 支持并发读写的一个集合
    private CopyOnWriteArrayList<CarBean> writeArrayList = new CopyOnWriteArrayList<>();

    private RemoteCallbackList<IOnNewCarArrivedListener> listenerList = new RemoteCallbackList<>();

    private ICarManager.Stub carManager = new ICarManager.Stub() {
        @Override
        public void addCar(CarBean car) throws RemoteException {
            writeArrayList.add(car);
        }

        @Override
        public List<CarBean> getCar() throws RemoteException {
            return writeArrayList;
        }

        @Override
        public void registerListener(IOnNewCarArrivedListener listener) throws RemoteException {
            listenerList.register(listener);
            Log.i("ZWK","registerListener size=="+listenerList.getRegisteredCallbackCount());
        }

        @Override
        public void unregisterListener(IOnNewCarArrivedListener listener)throws RemoteException {
           listenerList.unregister(listener);
            Log.i("ZWK","unregisterListener size=="+listenerList.getRegisteredCallbackCount());
        }

    };

    @Override
    public void onCreate() {
        super.onCreate();
        writeArrayList.add(new CarBean("公路车",10000,true));
        writeArrayList.add(new CarBean("公交车",20000,false));
        new Thread(new ServiceWork()).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return carManager;
    }

    private void onNewCarArrived(CarBean carBean) throws RemoteException{
        writeArrayList.add(carBean);
        Log.i("ZWK","onNewCarArrived size" + writeArrayList.size());
        int i1 = listenerList.beginBroadcast();
        for (int i = 0; i < i1; i++) {
            IOnNewCarArrivedListener iOnNewCarArrivedListener = listenerList.getBroadcastItem(i);
            if (iOnNewCarArrivedListener != null){
                iOnNewCarArrivedListener.onNewCarArrived(carBean);
            }
        }
        listenerList.finishBroadcast();
    }

    private class ServiceWork implements Runnable {

        @Override
        public void run() {
            while (!mIsServiceDestroyed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int i = writeArrayList.size() + 1;
                CarBean carBean = new CarBean("车" + i, 10000, false);
                try {
                    onNewCarArrived(carBean);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed.set(true);
        super.onDestroy();
    }
}
