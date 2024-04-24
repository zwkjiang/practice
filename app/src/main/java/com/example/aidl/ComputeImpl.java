package com.example.aidl;

import android.os.RemoteException;

import com.example.textview.ICompute;

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a+b;
    }
}
