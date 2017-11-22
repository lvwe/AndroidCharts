package com.example.administrator.androidcharts.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.androidcharts.constant.MyConstant;
import com.example.administrator.androidcharts.utils.TransformUtils;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class CanvasGraphView extends View {
    private static final String TAG = "CanvasGraphView";

    private Context mContext;
    private Paint mPaint;
    int mPaintColor = Color.BLACK;
    Paint.Style mStyle = Paint.Style.STROKE;
    int mViewStyle = MyConstant.ViewStyle.NONE;

    int width, height;

    int offsetX = 100;
    int offSetY = 100;
    int padding = 35;

    public CanvasGraphView(Context context) {
        this(context, null);
    }

    public CanvasGraphView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CanvasGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    public CanvasGraphView setPaintColor(int mPaintColor){
        this.mPaintColor = mPaintColor;
        return this;
    }

    public CanvasGraphView setStyle(Paint.Style style){
        this.mStyle = style;
        return this;
    }

    public CanvasGraphView setViewSytle(int viewStyle){
        this.mViewStyle = viewStyle;
        return this;
    }

    public void build(){
        if (mPaint != null){
            mPaint.reset();
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int[] screenSize = TransformUtils.getScreenWidth_Height(mContext);
        if (widthMode == MeasureSpec.EXACTLY) {
            //如果父布局已经给定宽度的大小
            //表现形式为：match_parent或具体的值
            width = widthSize;
        } else if (widthMode == MeasureSpec.UNSPECIFIED) {
            //当父控件对子控件没有任何约束条件的时候，子控件为任何值
            width = screenSize[0];
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = screenSize[0];//最多不超过屏幕的宽度
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST) {
            height = screenSize[1];
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        canvas.drawColor(Color.GRAY);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        offsetX = width / 2;
        offSetY = getHeight() / 2;
        mPaint.setColor(mPaintColor);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(mStyle);
        int radius = offsetX / 2;
        switch (mViewStyle) {
            case MyConstant.ViewStyle.ROUND_RECT:
                RectF rectF = new RectF();
                rectF.left = offsetX - radius;
                rectF.top = offSetY - radius / 2;
                rectF.bottom = offSetY + radius / 2;
                rectF.right = offsetX + radius;
                canvas.drawRoundRect(rectF, 20, 20, mPaint);
                break;
            case MyConstant.ViewStyle.TRIANGLE:
                //A点
                int verAx = offsetX - radius;
                int verAy = offSetY + radius;
                //B点
                int verBx = verAx + radius * 2;
                int verBy = verAy;
                //C点
                int verCx = offsetX;
                int verCy = offSetY - radius;
                Path trianglePath = new Path();
                trianglePath.moveTo(verAx, verAy);
                trianglePath.lineTo(verBx, verBy);
                trianglePath.lineTo(verCx, verCy);
                trianglePath.close();
                canvas.drawPath(trianglePath,mPaint);

                break;
            case MyConstant.ViewStyle.BEZIER:
                Path bezierPath = new Path();
                PointF startP = new PointF();
                startP.set(offsetX - radius /2 ,offSetY + radius * 2);
                PointF controlP1 = new PointF();
                controlP1.set(offsetX - radius / 2,offSetY + radius / 4);
                PointF controlP2 = new PointF();
                controlP2.set(offsetX + radius /2 ,offSetY - radius);
                PointF endP = new PointF();
                endP.set(offsetX + radius, offSetY + radius / 2);
                bezierPath.moveTo(startP.x,startP.y);
                bezierPath.cubicTo(controlP1.x,controlP1.y,controlP2.x,controlP2.y,endP.x,endP.y);
                canvas.drawPath(bezierPath,mPaint);
                break;
            case MyConstant.ViewStyle.ARE:
                RectF rect = new RectF();
                rect.left = offsetX - radius;
                rect.right = offsetX + radius;
                rect.top = offSetY - radius;
                rect.bottom = offSetY + radius;
                canvas.drawArc(rect,150,240,true,mPaint);
                break;
            case MyConstant.ViewStyle.NONE:
                break;
            case MyConstant.ViewStyle.CIRCLE:
                canvas.drawCircle(offsetX,offSetY,offsetX /2 ,mPaint);
                break;
            default:
                break;
        }

    }
}
