package com.example.administrator.androidcharts.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.androidcharts.R;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/12 0012.
 */

public abstract class BaseView extends View {

    private Context mContext;
    private Paint mPaint;

    private String mGraphTitle;
    private String mXAxisName;
    private String mYAxisName;
    private float mAxisTextSize;
    private int mAxisTextColor;

    //视图的宽高
    private int width;
    private int height;
    //起点的X，Y坐标值
    private int originalX = 100;
    private int originalY = 800;

    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        //获取自定义样式
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyGraphStyle);

        mGraphTitle = typedArray.getString(R.styleable.MyGraphStyle_graphTitle);
        mXAxisName = typedArray.getString(R.styleable.MyGraphStyle_xAxisName);
        mYAxisName = typedArray.getString(R.styleable.MyGraphStyle_yAxisName);
        mAxisTextSize = typedArray.getInteger(R.styleable.MyGraphStyle_axisTextSize, context.getResources().getColor(android.R.color.black));
        mAxisTextSize = typedArray.getDimension(R.styleable.MyGraphStyle_axisTextSize, 12);

        //取到TypeArray对象时记得判断回收
        if (typedArray != null) {
            typedArray.recycle();
        }

        initPaint();
    }

    private void initPaint() {
        if (mPaint != null){
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setDither(true); //防抖动
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //width = 屏幕的宽 - 起始点的X坐标，再减去右边距
        //height = 就是屏幕的Y坐标点，如果高度小于给定的值则取屏幕的高度  再减去距上边距
        width = getWidth() - originalX;
        height = (originalY > getHeight() ? getHeight() : originalY) - 400;

        drawXAxis(canvas,mPaint);
        drawYAxis(canvas,mPaint);
        drawTitle(canvas,mPaint);
        drawXAxisScale(canvas,mPaint);
        drawXAxisScaleValue(canvas,mPaint);
        drawYAxisScale(canvas,mPaint);
        drawYAxisScaleValue(canvas,mPaint);
        drawXAxisArrow(canvas, mPaint);
        drawYAxisArrow(canvas, mPaint);
        drawColumn(canvas,mPaint);

    }

    protected abstract void drawColumn(Canvas canvas, Paint paint);

    private void drawYAxisArrow(Canvas canvas, Paint paint) {

    }


    private void drawXAxisArrow(Canvas canvas, Paint paint) {

    }

    /**
     * 画Y轴的刻度值
     * @param canvas
     * @param paint
     */
    protected abstract void drawYAxisScaleValue(Canvas canvas, Paint paint);

    /**
     * 画Y轴的刻度
     * @param canvas
     * @param paint
     */
    protected abstract void drawYAxisScale(Canvas canvas, Paint paint);


    /**
     * 画X轴的刻度值
     * @param canvas
     * @param paint
     */
    protected abstract void drawXAxisScaleValue(Canvas canvas, Paint paint);

    /**
     * 画X轴的刻度
     * @param canvas
     * @param paint
     */
    protected abstract void drawXAxisScale(Canvas canvas, Paint paint);

    /**
     * 画图标的标题
     * @param canvas
     * @param paint
     */
    private void drawTitle(Canvas canvas, Paint paint) {

    }

    /**
     * 画Y轴
     * @param canvas
     * @param paint
     */
    protected abstract void drawYAxis(Canvas canvas, Paint paint);

    /**
     * 画X轴
     * @param canvas
     * @param paint
     */
    protected abstract void drawXAxis(Canvas canvas, Paint paint);
}
