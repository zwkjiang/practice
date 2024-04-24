package com.example.aidl;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CarBean implements Parcelable {
    private String name;
    private int price;
    private boolean isTram;
    protected CarBean(Parcel in) {
         name = in.readString();
         price = in.readInt();
         isTram = in.readInt() == 1;
    }

    public CarBean(String name, int price, boolean isTram) {
        this.name = name;
        this.price = price;
        this.isTram = isTram;
    }

    public static final Creator<CarBean> CREATOR = new Creator<CarBean>() {
        @Override
        public CarBean createFromParcel(Parcel in) {
            return new CarBean(in);
        }

        @Override
        public CarBean[] newArray(int size) {
            return new CarBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(price);
        dest.writeInt(isTram?0 :1);
    }

    @Override
    public String toString() {
        return "CarBean{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", isTram=" + isTram +
                '}';
    }
}
