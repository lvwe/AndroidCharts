package com.example.administrator.androidcharts.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.androidcharts.R;
import com.example.administrator.androidcharts.constant.MyConstant;
import com.example.administrator.androidcharts.view.ColumnView;
import com.github.mikephil.charting.utils.ColorTemplate;
import static com.github.mikephil.charting.utils.ColorTemplate.LIBERTY_COLORS;


public class SimpleColumnActivity extends BaseActivity {


    private ColumnView mColumnView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_simple_column;
    }

    @Override
    public void onContentChanged() {
        mColumnView = (ColumnView) findViewById(R.id.column_view);
    }

    @Override
    protected void initParams() {

        setTitle(getString(R.string.title_simple_column));

        mColumnView.setMaxAxisValueX(10,9);
        mColumnView.setMaxAxisValueY(10,7);

        int columnInfo[][] = new int[][]{
                {6, ColorTemplate.MATERIAL_COLORS[0]},
                {5, LIBERTY_COLORS[1]},
                {4, LIBERTY_COLORS[3]},
                {3, LIBERTY_COLORS[4]},
                {5, LIBERTY_COLORS[0]},
                {3, LIBERTY_COLORS[2]},
                {2, LIBERTY_COLORS[0]},
        };
        mColumnView.setAxisTextSize(20);
        mColumnView.setColumnInfo(columnInfo);
        mColumnView.setTouchEnable(true);
        mColumnView.invalidate();
    }
}
