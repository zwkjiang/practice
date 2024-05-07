package com.example.aidl1;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.aidl.CarBean;

import java.util.List;

public class CarManagerImpl extends Binder implements ICarManager {

    public CarManagerImpl() {
        this.attachInterface(this, DESCRIPTOR);
    }


    public static ICarManager asInterface(IBinder obj) {
        if (obj == null) {
            return null;
        }
        IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if ((iin != null) && (iin instanceof ICarManager)) {
            return (ICarManager) iin;
        }
        return null;
    }

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags)
            throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION:
                assert reply != null;
                reply.writeString(DESCRIPTOR);
                return true;
            case TRANSACTION_getCarList:
                data.enforceInterface(DESCRIPTOR);
                List<CarBean> carList = this.getCarList();
                assert reply != null;
                reply.writeNoException();
                reply.writeTypedList(carList);
                return true;
            case TRANSACTION_addCar:
                data.enforceInterface(DESCRIPTOR);
        }
        return super.onTransact(code, data, reply, flags);
    }

    @Override
    public List<CarBean> getCarList() throws RemoteException {
        return null;
    }

    @Override
    public void addCar(CarBean carBean) throws RemoteException {

    }

    @Override
    public IBinder asBinder() {
        return null;
    }

    private static class Proxy implements ICarManager {
        private IBinder mRemote;

        public Proxy(IBinder mRemote) {
            this.mRemote = mRemote;
        }

        public String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }

        @Override
        public List<CarBean> getCarList() throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            List<CarBean> result;
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getCarList, data, reply, 0);
                reply.readException();
                result = reply.createTypedArrayList(CarBean.CREATOR);
            } finally {
                reply.recycle();
                data.recycle();
            }
            return result;
        }

        @Override
        public void addCar(CarBean carBean) throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                if (carBean != null) {
                    data.writeInt(1);
                    carBean.writeToParcel(data, 0);
                } else {
                    data.writeInt(0);
                }
                mRemote.transact(TRANSACTION_addCar, data, reply, 0);
                reply.readException();
            } finally {
                reply.recycle();
                data.recycle();
            }

        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }
    }
}
