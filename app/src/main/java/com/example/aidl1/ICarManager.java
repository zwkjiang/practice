package com.example.aidl1;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import com.example.aidl.CarBean;

import java.util.List;

public interface ICarManager extends IInterface {
    static final String DESCRIPTOR = "com.example.aidl1.ICarManager";
    static final int TRANSACTION_getCarList = IBinder.FIRST_CALL_TRANSACTION + 1;
    static final int TRANSACTION_addCar = IBinder.FIRST_CALL_TRANSACTION + 2;

    public List<CarBean> getCarList() throws RemoteException;
    public void addCar(CarBean carBean) throws  RemoteException;
}
