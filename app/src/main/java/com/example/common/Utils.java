package com.example.common;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

public class Utils {
    private static float sNoncompatDensity;
    public static int flag = 1;
    private static float sNoncompatScaledDensity ;

    public static void screenAdaptation(Activity activity){
        Application application = activity.getApplication();
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        sNoncompatDensity = displayMetrics.density;
        sNoncompatScaledDensity = displayMetrics.scaledDensity;
        application.registerComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(@NonNull Configuration newConfig) {
                if (newConfig != null && newConfig.fontScale >0){
                    sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                }
            }

            @Override
            public void onLowMemory() {

            }
        });
        float targetDensity = displayMetrics.widthPixels / 160;
        float targetScaleDensity = targetDensity  * (sNoncompatScaledDensity / sNoncompatDensity);
        int targetDensityDpi  = (int) (160 * targetDensity);
        displayMetrics.density  = targetDensity;
        displayMetrics.scaledDensity = targetScaleDensity;
        displayMetrics.densityDpi = targetDensityDpi;

        DisplayMetrics displayMetrics1 = activity.getResources().getDisplayMetrics();
        displayMetrics1.density = targetDensity;
        displayMetrics1.scaledDensity = targetScaleDensity;
        displayMetrics1.densityDpi = targetDensityDpi;
    }
}
