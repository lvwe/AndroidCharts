package com.example.administrator.androidcharts.formatter;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class MyAxisValueFormatter implements IAxisValueFormatter {

    private DecimalFormat mDecimalFormat;

    public MyAxisValueFormatter() {
        mDecimalFormat = new DecimalFormat("###,###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mDecimalFormat.format(value) + "$";
    }
}
