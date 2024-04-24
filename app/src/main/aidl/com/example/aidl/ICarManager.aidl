// ICarManager.aidl
package com.example.aidl;

// Declare any non-default types here with import statements
import com.example.aidl.CarBean;
import com.example.aidl.IOnNewCarArrivedListener;
interface ICarManager {
     void addCar(in CarBean car);
     List<CarBean> getCar();
     void registerListener(IOnNewCarArrivedListener listener);
     void unregisterListener(IOnNewCarArrivedListener listener);
}