package com.example.administrator.androidcharts.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class TransformUtils {

    public static int dp2px(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    public static int sp2px(int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,
        Resources.getSystem().getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 px（像素）的单位转换为dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, int pxValue){
        float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context,float pxValue){
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int getScreenWidth(@NonNull Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(@NonNull Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int[] getScreenWidth_Height(@NonNull Context context){
        Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        return new int[]{width,height};
    }
}
