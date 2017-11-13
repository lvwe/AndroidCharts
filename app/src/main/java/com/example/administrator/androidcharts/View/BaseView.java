package com.example.administrator.androidcharts.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.androidcharts.R;

/**
 * Created by Administrator on 2017/11/12 0012.
 */

public abstract class BaseView extends View {

    private Context mContext;
    public Paint mPaint;

    private String mGraphTitle;
    private String mXAxisName;
    private String mYAxisName;
    private float mAxisTextSize;
    private int mAxisTextColor;

    //视图的宽高
    public int width;
    public int height;
    //起点的X，Y坐标值
    public int originalX = 100;
    public int originalY = 800;

    //X,Y轴等轴划分

    //第一个纬度为值，第二个纬度为颜色
    public int[][] columnInfo;

    //X坐标轴最大值
    public float maxAxisValueX = 900;
    //X坐标轴刻度线数量
    public int axisDividedSizeX = 9;
    //Y坐标轴最大值
    public float maxAxisValueY = 700;
    //Y坐标轴刻度线数量
    public int axisDividedSizeY = 7;

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
        mAxisTextColor = typedArray.getInteger(R.styleable.MyGraphStyle_axisTextColor, context.getResources().getColor(android.R.color.black));
        mAxisTextSize = typedArray.getDimension(R.styleable.MyGraphStyle_axisTextSize, 12);

        //取到TypeArray对象时记得判断回收
        if (typedArray != null) {
            typedArray.recycle();
        }

        initPaint();
    }

    private void initPaint() {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setDither(true); //防抖动
        }
    }

    public void setGrapthTitle(String grapthTitle) {
        mGraphTitle = grapthTitle;
    }

    public void setXAxisName(String XAxisName) {
        mXAxisName = XAxisName;
    }

    public void setYAxisName(String YAxisName) {
        mYAxisName = YAxisName;
    }

    public void setAxisTextColor(int axisTextColor) {
        mAxisTextColor = axisTextColor;
    }

    public void setAxisTextSize(float axisTextSize) {
        mAxisTextSize = axisTextSize;
    }

    /**
     * 手动设置X轴最大值及等份数
     * @param maxAxisValueX
     * @param dividedSize
     */
    public void setXAxisValue(float maxAxisValueX,int dividedSize) {
        this.maxAxisValueX = maxAxisValueX;
        this.axisDividedSizeX = dividedSize;
    }

    /**
     * 手动设置Y轴最大值及等份数
     * @param maxAxisValueY
     * @param dividedSize
     */
    public void setYAxisValue(float maxAxisValueY,int dividedSize) {
        this.maxAxisValueY = maxAxisValueY;
        this.axisDividedSizeY = dividedSize;
    }

    /**
     * 传入柱状图数据
     * @param columnInfo
     */
    public void setColumnInfo(int[][] columnInfo) {
        this.columnInfo = columnInfo;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //width = 屏幕的宽 - 起始点的X坐标，再减去右边距
        //height = 就是屏幕的Y坐标点，如果高度小于给定的值则取屏幕的高度  再减去距上边距
        width = getWidth() - originalX;
        height = (originalY > getHeight() ? getHeight() : originalY) - 400;

        drawXAxis(canvas, mPaint);
        drawYAxis(canvas, mPaint);
        drawTitle(canvas, mPaint);
        drawXAxisScale(canvas, mPaint);
        drawXAxisScaleValue(canvas, mPaint);
        drawYAxisScale(canvas, mPaint);
        drawYAxisScaleValue(canvas, mPaint);
        drawXAxisArrow(canvas, mPaint);
        drawYAxisArrow(canvas, mPaint);
        drawColumn(canvas, mPaint);

    }

    protected abstract void drawColumn(Canvas canvas, Paint paint);

    private void drawYAxisArrow(Canvas canvas, Paint paint) {

        Path mPathY = new Path();
        mPathY.moveTo(originalX,originalY - height - 30);
        mPathY.lineTo(originalX - 10,originalY - height);
        mPathY.lineTo(originalX + 10, originalY - height);
        mPathY.close();
        canvas.drawPath(mPathY,paint);
        canvas.drawText(mYAxisName,originalX - 30, originalY - height - 30,paint);
    }


    private void drawXAxisArrow(Canvas canvas, Paint paint) {

        Path mPathX = new Path();
        mPathX.moveTo(originalX + width + 30, originalY);
        mPathX.lineTo(originalX + width, originalY + 10);
        mPathX.lineTo(originalX + width, originalY - 10);
        mPathX.close();
        canvas.drawPath(mPathX, paint);
        canvas.drawText(mXAxisName, originalX + width, originalX + 50,paint);
    }

    /**
     * 画Y轴的刻度值
     *
     * @param canvas
     * @param paint
     */
    protected abstract void drawYAxisScaleValue(Canvas canvas, Paint paint);

    /**
     * 画Y轴的刻度
     *
     * @param canvas
     * @param paint
     */
    protected abstract void drawYAxisScale(Canvas canvas, Paint paint);


    /**
     * 画X轴的刻度值
     *
     * @param canvas
     * @param paint
     */
    protected abstract void drawXAxisScaleValue(Canvas canvas, Paint paint);

    /**
     * 画X轴的刻度
     *
     * @param canvas
     * @param paint
     */
    protected abstract void drawXAxisScale(Canvas canvas, Paint paint);

    /**
     * 画图标的标题
     *
     * @param canvas
     * @param paint
     */
    private void drawTitle(Canvas canvas, Paint paint) {

        if (!TextUtils.isEmpty(mGraphTitle)) {
            paint.setTextSize(mAxisTextSize);
            paint.setColor(mAxisTextColor);
            paint.setFakeBoldText(true);  //设置字体为粗体

            canvas.drawText(mGraphTitle, (getWidth() / 2) -
                    (paint.measureText(mGraphTitle) / 2), originalY + 40, paint);
        }
    }

    /**
     * 画Y轴
     *
     * @param canvas
     * @param paint
     */
    protected abstract void drawYAxis(Canvas canvas, Paint paint);

    /**
     * 画X轴
     *
     * @param canvas
     * @param paint
     */
    protected abstract void drawXAxis(Canvas canvas, Paint paint);
}
