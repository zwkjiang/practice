package com.example.bean;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class CarBean implements Serializable {
    private static final long serialVersionUID = 9214353423123L;

    private String name;

    private int price;

    public CarBean(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "CarBean{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
