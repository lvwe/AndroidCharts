package com.example.administrator.androidcharts.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.administrator.androidcharts.R;
import com.github.mikephil.charting.components.XAxis;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public abstract class BaseGraphView extends View {
    private static final String TAG = "BaseGraphView";

    //各种画笔防止混乱
    private Paint mXYAxisPaint;
    private Paint mTitlePaint;
    private Paint mAxisNamePaint;
    private Paint mPaint;
    private Paint mColumnCurvePaint;

    //用于测量字体的宽高
    private Rect mBoundsRect;
    //自定义Style属性值
    private String mGraphTitle;
    private String mXAxisName;
    private String mYAxisName;
    private int mAxisTextColor;
    private float mAxisTextSize;
    private boolean isShowOrigin;
    private boolean isShowXAxis;
    private boolean isShowYAxis;
    private int axisColor;

    /**
     * X,Y轴坐标最大值，X,Y坐标轴刻度线数量
     */
    public float maxAxisValueX = 900;
    public int axisDivideSizeX = 9;
    public float maxAxisValueY = 700;
    public int axisDivideSizeY = 7;
    //视图的宽高
    int width, height;
    public int originX;
    public int originY;
    public int padding = 30;
    public int mDefMargin = 10;
    public int mDefXValueHeight = 30;
    public int mDefTitleHeight = 30;
    public int mDefLabelHeight = 30;
    private int mCellWidth; //X轴上每一段的宽度
    public int columnInfo[][];//柱状图数据

    public int bgColor = Color.WHITE;


    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public BaseGraphView(Context context) {
        this(context, null);
    }

    public BaseGraphView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BaseGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyGraphStyle);
        mGraphTitle = typedArray.getString(R.styleable.MyGraphStyle_graphTitle);
        mXAxisName = typedArray.getString(R.styleable.MyGraphStyle_xAxisName);
        mYAxisName = typedArray.getString(R.styleable.MyGraphStyle_yAxisName);
        mAxisTextColor = typedArray.getColor(R.styleable.MyGraphStyle_axisTextColor, context.getResources().getColor(android.R.color.black));
        mAxisTextSize = typedArray.getDimension(R.styleable.MyGraphStyle_axisTextSize, 12);
        isShowOrigin = typedArray.getBoolean(R.styleable.MyGraphStyle_showOrigin, true);

        isShowXAxis = typedArray.getBoolean(R.styleable.MyGraphStyle_showXAxis, true);
        isShowYAxis = typedArray.getBoolean(R.styleable.MyGraphStyle_showYAxis, true);
        axisColor = typedArray.getColor(R.styleable.MyGraphStyle_axisColor, Color.BLACK);

        if (typedArray != null) {
            typedArray.recycle();
        }
        mBoundsRect = new Rect();
        initPaint(context);
    }

    public int getCellWidth() {
        mCellWidth = width / axisDivideSizeX;
        return mCellWidth;
    }

    private void initPaint(Context context) {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            mPaint.setTextSize(20);
        }
        mXYAxisPaint = new Paint();
        mXYAxisPaint.setAntiAlias(true);
        mXYAxisPaint.setDither(true);

        mTitlePaint = new Paint();
        mTitlePaint.setAntiAlias(true);
        mTitlePaint.setDither(true);

        mColumnCurvePaint = new Paint();
        mColumnCurvePaint.setAntiAlias(true);
        mColumnCurvePaint.setDither(true);
    }

    public void setGraphTitle(String graphTitle) {
        mGraphTitle = graphTitle;
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
     *
     * @param maxAxisValueX
     * @param axisDivideSizeX
     */
    public void setMaxAxisValueX(float maxAxisValueX, int axisDivideSizeX) {
        this.maxAxisValueX = maxAxisValueX;
        if (columnInfo != null && columnInfo.length > 0) {
            this.axisDivideSizeX = columnInfo.length;
        }
    }

    /**
     * 手动设置Y轴最大值及等份数
     *
     * @param maxAxisValueY
     * @param axisDivideSizeY
     */
    public void setMaxAxisValueY(float maxAxisValueY, int axisDivideSizeY) {
        this.maxAxisValueY = maxAxisValueY;
        this.axisDivideSizeY = axisDivideSizeY;
    }

    /**
     * 设置柱状图数据
     *
     * @param columnInfo
     */
    public void setColumnInfo(int[][] columnInfo) {
        this.columnInfo = columnInfo;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = getWidth();
        height = getHeight();

        canvas.drawColor(bgColor);

        originX = 100;
        originY = getTop() + height - (mDefXValueHeight + mDefTitleHeight + mDefXValueHeight);
        /**
         * 画X，Y轴
         * 画X，Y轴上的箭头
         */
        mXYAxisPaint.setColor(axisColor);
        if (isShowXAxis) {
            drawAxisX(canvas, mXYAxisPaint);
            drawAxisArrowX(canvas, mXYAxisPaint);
        }

        if (isShowYAxis) {
            drawAxisY(canvas, mXYAxisPaint);
            drawAxisArrowY(canvas, mXYAxisPaint);
        }

        /**
         * 画X,Y轴上的分割线
         */
        drawAxisScaleMarkX(canvas, mPaint);
        drawAxisScaleMarkY(canvas, mPaint);

        /**
         * 画X，Y轴上的刻度值
         */
        drawAxisScaleMarkValueX(canvas, mPaint);
        drawAxisScaleMarkValueY(canvas, mPaint);

        /**
         * 画柱条，曲线
         */
        drawColumnCurve(canvas, mColumnCurvePaint);

        /**
         * 画标题
         */
        drawTitle(canvas);

        /**
         * 画颜色标记，如红代表...，绿代表...
         */
        drawLabel(canvas, mPaint);
    }

    protected abstract void drawLabel(Canvas canvas, Paint paint);

    private void drawTitle(Canvas canvas) {
        if (!TextUtils.isEmpty(mGraphTitle)) {
            mTitlePaint.setColor(mAxisTextColor);
            mTitlePaint.setTextSize(mAxisTextSize);
            mPaint.setFakeBoldText(true);
            canvas.drawText(mGraphTitle, (getWidth() / 2) - (mTitlePaint.measureText(mGraphTitle) / 2),
                    originY + mDefTitleHeight + mDefMargin + 10, mTitlePaint);
        }
    }

    protected abstract void drawColumnCurve(Canvas canvas, Paint columnCurvePaint);

    private void drawAxisArrowY(Canvas canvas, Paint paint) {
        Path path = new Path();
        path.moveTo(originX, getTop() + padding);
        path.lineTo(originX - 10, getTop() + padding * 2);
        path.lineTo(originX + 10, getTop() + padding * 2);
        path.close();
        canvas.drawPath(path, paint);
        if (!TextUtils.isEmpty(mYAxisName)) {
            paint.setTextSize(mAxisTextSize);
            paint.getTextBounds(mYAxisName, 0, mYAxisName.length(), mBoundsRect);
            float textWidth = mBoundsRect.width();
            float textHeight = mBoundsRect.height();
            float startX = originX + padding;
            canvas.drawText(mYAxisName, startX, getTop() + padding + textHeight, paint);
        }
    }

    private void drawAxisArrowX(Canvas canvas, Paint paint) {
        Path path = new Path();
        path.moveTo(width - padding, originY);
        path.lineTo(width - padding * 2, originY - 10);
        path.lineTo(width - padding * 2, originY + 10);
        path.close();
        canvas.drawPath(path, paint);
        if (!TextUtils.isEmpty(mXAxisName)) {
            paint.setTextSize(mAxisTextSize);
            paint.getTextBounds(mXAxisName, 0, mXAxisName.length(), mBoundsRect);
            float textWidth = mBoundsRect.width();
            float textHeight = mBoundsRect.height();
            float startX = (width - padding) - textWidth - padding;
            canvas.drawText(mXAxisName, startX, originY + textHeight + padding, paint);

        }

    }

    private void drawAxisScaleMarkY(Canvas canvas, Paint paint) {
        float cellHeight = (originY - (getTop() + padding)) / axisDivideSizeY;
        for (int i = 0; i < axisDivideSizeY - 1; i++) {
            canvas.drawLine(originX, (originY - cellHeight * (i + 1)), originX + 10,
                    (originY - cellHeight * (i + 1)), paint);
        }
    }

    private void drawAxisScaleMarkX(Canvas canvas, Paint paint) {
        float cellWidth = width / axisDivideSizeX;
        for (int i = 0; i < axisDivideSizeX - 1; i++) {
            canvas.drawLine(cellWidth * (i + 1) + originX, height, cellWidth * (i + 1) + originX,
                    height + 10, paint);
        }
    }

    private void drawAxisScaleMarkValueY(Canvas canvas, Paint paint) {
//        float cellWidth = height / axisDivideSizeY;
        float cellWidth = (originY - (getTop() + padding)) / axisDivideSizeY;
        float cellValue = maxAxisValueY / axisDivideSizeY;
        paint.reset();
        paint.setTextSize(20);
        paint.setColor(Color.GRAY);
        for (int i = 0; i < axisDivideSizeY; i++) {
//            canvas.drawText(String.valueOf(i), originX - 30,
//                    height - cellWidth * i + 10, paint);

            canvas.drawText(String.valueOf(i), originX - 30,
                    originY - cellWidth * i + 10, paint);
        }
    }

    private void drawAxisScaleMarkValueX(Canvas canvas, Paint paint) {
        if (columnInfo == null || columnInfo.length == 0) {
            return;
        }
        paint.setColor(Color.GRAY);
        paint.setTextSize(20);
        paint.setFakeBoldText(true);

        float columnLeft = originX + padding;//第一柱条的左边距
        //第一个padding为右边距，第二个padding为箭头的宽度
        float columnRight = padding + padding;
        float cellWidth = (width - (columnLeft + columnRight)) / columnInfo.length;
        float cellValue = maxAxisValueX / columnInfo.length;
        for (int i = 1; i <= columnInfo.length; i++) {
            canvas.drawText(String.valueOf(i), (columnLeft + cellWidth / 2) + (i - 1) * cellWidth,
                    height - (mDefTitleHeight + mDefLabelHeight), paint);
        }
    }

    private void drawAxisY(Canvas canvas, Paint paint) {
        int[] location = new int[2];
        getLocationInWindow(location);
        canvas.drawLine(originX, originY, originX, getTop() + padding, paint);
    }

    private void drawAxisX(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        canvas.drawLine(originX, originY, width - padding, originY, paint);
    }


}
