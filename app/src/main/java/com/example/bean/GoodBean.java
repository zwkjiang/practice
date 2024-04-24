package com.example.bean;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class GoodBean implements Serializable {

    private static final long serialVersionUID = 1312124214131214L;

    private String name;
    private int price;
    private boolean isFood;

    public GoodBean(String name, int price, boolean isFood) {
        this.name = name;
        this.price = price;
        this.isFood = isFood;
    }


    @Override
    public String toString() {
        return "GoodBean{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", isFood=" + isFood +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public boolean isFood() {
        return isFood;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}
