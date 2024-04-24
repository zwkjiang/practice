package com.example.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;

import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

public class UiUtils {

    private UiUtils() {

    }

    public static class Holder {
        private static final UiUtils uiUtils = new UiUtils();

        public static UiUtils getInstance() {
            return uiUtils;
        }
    }

    public void setStatusBarImmerse(Window window, Boolean isLight) {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = window.getDecorView();
            int option;
            if (isLight) {
                option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            }
            decorView.setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void setNavigationBarImmerse(Window window) {
        if (Build.VERSION.SDK_INT>= 21) {
            View decorView = window.getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    public void setNavigationBarPop(Window window) {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = window.getDecorView();
            int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    public void setStatusBarColor(Window window, int color) {
        window.setStatusBarColor(color);
    }

    public int getStatusBarHeight(Window window) {
        Rect rect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    @SuppressLint("PrivateApi")
    public int getStatusBarHeight2(Context context) {
        Class c;
        int barHeight;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int height = Integer.parseInt(field.get(o).toString());
            barHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return barHeight;
    }

    @SuppressLint({"InternalInsetResource", "DiscouragedApi"})
    public int getStatusBarHeight3(Context context) {
        int height = 0;
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            height = context.getResources().getDimensionPixelSize(identifier);
        }
        return height;
    }

}
