// IOnNewCarArrivedListener.aidl
package com.example.aidl;

// Declare any non-default types here with import statements
import com.example.aidl.CarBean;
interface IOnNewCarArrivedListener {
void onNewCarArrived(in CarBean newCarBean);
}