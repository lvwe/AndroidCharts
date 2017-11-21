package com.example.administrator.androidcharts.activity;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.androidcharts.MyConstant;
import com.example.administrator.androidcharts.R;
import com.example.administrator.androidcharts.view.CanvasGraphView;
import com.github.mikephil.charting.utils.ColorTemplate;

public class SimpleGraphActivity extends BaseActivity {

private CanvasGraphView mCanvasGraphView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_simple_graph;
    }

    @Override
    protected void initParams() {
        setTitle(getResources().getString(R.string.title_simple_graph));

        mCanvasGraphView = (CanvasGraphView) findViewById(R.id.base_graph);
    }

    /**
     * 画圆
     * @param view
     */
    public void drawCircle(View view){
        mCanvasGraphView.setStyle(Paint.Style.FILL)
                .setPaintColor(ColorTemplate.JOYFUL_COLORS[3])
                .setViewSytle(MyConstant.ViewStyle.CIRCLE)
                .build();
    }

    /**
     * 画三角形
     * @param view
     */
    public void drawTriangle(View view){
        mCanvasGraphView.setStyle(Paint.Style.STROKE)
                .setPaintColor(ColorTemplate.getHoloBlue())
                .setViewSytle(MyConstant.ViewStyle.TRIANGLE)
                .build();
    }

    /**
     * 画圆角矩形
     * @param view
     */
    public void drawRect(View view){
        mCanvasGraphView.setStyle(Paint.Style.FILL_AND_STROKE)
                .setPaintColor(ColorTemplate.JOYFUL_COLORS[0])
                .setViewSytle(MyConstant.ViewStyle.ROUND_RECT)
                .build();
    }

    /**
     * 画弧形
     * @param view
     */
    public void drawArc(View view){
        mCanvasGraphView.setStyle(Paint.Style.FILL)
                .setPaintColor(ColorTemplate.JOYFUL_COLORS[0])
                .setViewSytle(MyConstant.ViewStyle.ARE)
                .build();
    }
    /**
     * 画贝塞尔曲线
     * @param view
     */
    public void drawBezier(View view){
        mCanvasGraphView.setStyle(Paint.Style.STROKE)
                .setPaintColor(ColorTemplate.JOYFUL_COLORS[0])
                .setViewSytle(MyConstant.ViewStyle.BEZIER)
                .build();
    }
}
