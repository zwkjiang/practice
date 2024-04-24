package com.example.common.jni;

public class TextJni {

    static {
        System.loadLibrary("jni-test");
    }

    public static void main(String args[]){
        TextJni textJni = new TextJni();
        System.out.println(textJni.get());
        textJni.set("hello word");
    }

    public native String get();
    public native void set(String str);
}
