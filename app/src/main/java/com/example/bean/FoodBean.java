package com.example.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

public class FoodBean implements Parcelable {

    private BigDecimal price;

    private String name;

    private String type;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.price);
        dest.writeString(this.name);
        dest.writeString(this.type);
    }

    public void readFromParcel(Parcel source) {
        this.price = (BigDecimal) source.readSerializable();
        this.name = source.readString();
        this.type = source.readString();
    }

    public FoodBean() {
    }

    protected FoodBean(Parcel in) {
        this.price = (BigDecimal) in.readSerializable();
        this.name = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<FoodBean> CREATOR = new Parcelable.Creator<FoodBean>() {
        @Override
        public FoodBean createFromParcel(Parcel source) {
            return new FoodBean(source);
        }

        @Override
        public FoodBean[] newArray(int size) {
            return new FoodBean[size];
        }
    };
}
