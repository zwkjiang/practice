package com.example.common;

import android.util.Log;

public class TeamAMember implements TeamMember{

    String name;

    public TeamAMember(String name) {
        this.name = name;
    }

    @Override
    public void reviewCode() {
        Log.i("ZHANG","A组成员"+name+"代码看过了");
    }
}
