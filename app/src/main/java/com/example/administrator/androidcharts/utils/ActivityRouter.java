package com.example.administrator.androidcharts.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class ActivityRouter {

    public static void goToTarget(@NonNull Context context,@NonNull Class<?> activity){
        Intent intent = new Intent(context,activity);
        context.startActivity(intent);

    }
}
