package com.example.administrator.androidcharts.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class ColumnView extends BaseGraphView implements View.OnTouchListener {
    private static final String TAG = "ColumnView";


    private Context mContext;
    private boolean touchEnable = false; //触摸开关
    private float originalX, originalY; //手指按下时点的坐标

    public ColumnView(Context context) {
        this(context, null);
    }

    public ColumnView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ColumnView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public boolean isTouchEnable() {
        return touchEnable;
    }

    public void setTouchEnable(boolean touchEnable) {
        this.touchEnable = touchEnable;
        if (touchEnable) {
            setOnTouchListener(this);
        }
    }

    private int mTouchPosition;
    private boolean mTouched;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                originalX = event.getX();
                originalY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                float endX = event.getX();
                float endY = event.getY();
                float columnLeft = originX + padding;
                float columnRight = padding + padding;
                float cellWidth = (width - (columnLeft + columnRight)) / columnInfo.length;
                for (int i = 0; i < columnInfo.length; i++) {

                    float leftTopY = originY - (originY - (getTop() + padding)) / axisDivideSizeY * columnInfo[i][0];

                    if (originalX >= columnLeft + cellWidth * (i) && endX <= columnLeft + cellWidth * (i + 1)) {
                        //有效触摸区
                        Toast.makeText(mContext, "有效触摸区", Toast.LENGTH_SHORT).show();
                        mTouchPosition = i;
                        mTouched = true;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                mTouched = false;
                break;
        }
        return true;
    }


    @Override
    protected void drawLabel(Canvas canvas, Paint paint) {

    }

    @Override
    protected void drawColumnCurve(Canvas canvas, Paint paint) {
        if (columnInfo == null) {
            return;
        }
        float columnLeft = originX + padding;
        float columnRight = padding + padding;
        float cellWidth = (width - (columnLeft + columnRight)) / columnInfo.length;
        for (int i = 0; i < columnInfo.length; i++) {
            paint.setColor(columnInfo[i][1]);
            if (mTouchPosition == i && mTouched) {
                //添加触摸事件，当时触摸，添加透明度
                paint.setAlpha(128);
            }
            float leftTopY = originY - (originY - (getTop() + padding)) / axisDivideSizeY * columnInfo[i][0];
            Log.d(TAG, "drawColumnCurve: " + leftTopY);
            canvas.drawRect(columnLeft + cellWidth * (i), leftTopY,
                    columnLeft + cellWidth * (i + 1), originY, paint);
        }
    }
}
