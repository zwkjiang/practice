package com.example.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

public class MyTextView extends androidx.appcompat.widget.AppCompatTextView {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("MyTextView","dispatchTouchEvent->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("MyTextView","dispatchTouchEvent->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("MyTextView","dispatchTouchEvent->ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("MyTextView","onTouchEvent->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("MyTextView","onTouchEvent->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("MyTextView","onTouchEvent->ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }
}
