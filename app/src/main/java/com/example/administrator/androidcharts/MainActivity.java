package com.example.administrator.androidcharts;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.androidcharts.View.ChartView;

public class MainActivity extends AppCompatActivity {

    private ChartView mChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChartView = (ChartView) findViewById(R.id.columnView);
        int[][] columnInfo = new int[][]{

                {3, Color.BLUE},
                {5, Color.GREEN},
                {6, Color.LTGRAY},
                {1, Color.YELLOW},
                {2, Color.GRAY},
                {5, Color.RED},
                {1, Color.DKGRAY},
        };
        mChartView.setXAxisValue(10, 8);
        mChartView.setYAxisValue(10,7);
        mChartView.setColumnInfo(columnInfo);
    }
}
