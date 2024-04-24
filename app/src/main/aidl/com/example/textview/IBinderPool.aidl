// IBinderPool.aidl
package com.example.textview;

// Declare any non-default types here with import statements

interface IBinderPool {
    IBinder queryBinder(int binderCode);
}