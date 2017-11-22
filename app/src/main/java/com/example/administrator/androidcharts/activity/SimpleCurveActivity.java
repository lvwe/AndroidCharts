package com.example.administrator.androidcharts.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.example.administrator.androidcharts.R;
import com.example.administrator.androidcharts.view.CurveView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleCurveActivity extends BaseActivity {
    private static final String TAG = "SimpleCurveActivity";

    private CurveView mCurveView;
    private List<Integer> mDataList;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_simple_curve;
    }

    @Override
    public void onContentChanged() {
        mCurveView = (CurveView) findViewById(R.id.curve_view);
    }

    @Override
    protected void initParams() {
        setTitle(getResources().getString(R.string.title_simple_curve));
        mDataList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            mDataList.add(random.nextInt(10)*100);
            Log.d(TAG, "initParams: random.nextInt(10)*100--"+random.nextInt(10)*100);
        }
        mCurveView.setMaxAxisValueX(12,12);
        mCurveView.setMaxAxisValueY(getMax(mDataList.toArray(new Integer[mDataList.size()])),6);
        mCurveView.setDataList(mDataList);
        mCurveView.setFill(true);
        mCurveView.invalidate();
    }

    public void showHideShader(View view){
        ToggleButton toggleButton = (ToggleButton) view;
        boolean checked = toggleButton.isChecked();
         if (checked){
             mCurveView.setFill(false);
         }else {
             mCurveView.setFill(true);
         }
        mCurveView.invalidate();
    }

    /**
     * 取出数组中最大的数
     */
    public static int getMax(Integer[] arr){
        int max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max){
                max = arr[i];
            }
        }
        return max;
    }
}
