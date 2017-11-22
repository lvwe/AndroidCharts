package com.example.administrator.androidcharts.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;

import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.List;

/**
 * Created by Administrator on 2017/11/22 0022.
 */

public class CurveView extends BaseGraphView {

    //坐标单位
    private String[] xLabel;
    private String[] yLabel;
    //曲线数据
    private List<Integer> dataList;
    //默认边距
    private int margin = 20;
    //距离左边偏移量
    private int marginX = 30;
    //原地坐标
    private int xPoint;
    private int yPoint;
    //x,y轴的单位长度
    private int xScale;
    private int yScale;

    private boolean isFill = false;

    public void setFill(boolean fill) {
        isFill = fill;
        invalidate();
    }

    public void setDataList(List<Integer> dataList) {
        this.dataList = dataList;
        invalidate();
    }

    public CurveView(Context context) {
        this(context, null);
    }

    public CurveView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CurveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        xPoint = margin + marginX;
        yPoint = this.getHeight() - margin;

    }

    @Override
    protected void drawLabel(Canvas canvas, Paint paint) {

    }

    @Override
    protected void drawColumnCurve(Canvas canvas, Paint paint) {
        if (paint != null) {
            paint.reset();
            //默认不封闭
            if (isFill) {
                paint.setStyle(Paint.Style.FILL);
            } else {
                paint.setStyle(Paint.Style.STROKE);
            }
            paint.setColor(ColorTemplate.LIBERTY_COLORS[3]);
            //因为画曲线，用CornerPathEffet（）会更圆滑
            CornerPathEffect pathEffect = new CornerPathEffect(25);
            paint.setPathEffect(pathEffect);
            paint.setStrokeWidth(3);
            paint.setDither(true);
            paint.setAntiAlias(true);

            drawCurve(canvas, paint);
        }
    }

    private void drawCurve(Canvas canvas, Paint paint) {
        if (dataList != null && dataList.size() > 0) {
            xScale = (width - originX - padding * 2) / (axisDivideSizeX - 1);
            yScale = getCellWidth();
            Path path = new Path();
            int xAxisMax = width - originX - padding;
            int maxVertexX = originX;
            int maxVertexY = originY;
            for (int i = 0; i < dataList.size() - 1; i++) {
                if (maxAxisValueY == dataList.get(i) && i != 0) {
                    maxVertexX = (originX + i * xScale) > xAxisMax ? (originX + i * xScale) : xAxisMax;
                    maxVertexY = dataList.get(i);
                }
                if (i == 0) {
                    path.moveTo(originX, toY(dataList.get(i)));
                } else {
                    if (originX + i * xScale > xAxisMax) {
                        path.lineTo(xAxisMax, toY(dataList.get(i)));
                    } else {
                        path.lineTo(originX + i * xScale, toY(dataList.get(i)));
                    }
                }
            }
            path.lineTo(originX + (axisDivideSizeX - 1) * xScale, originY);
            path.lineTo(originX, originY);
            /**
             *LinearGradient用来实现线性渐变效果
             * 此类是Shader的子类
             * 通过paint.setShader来设置渐变
             * LinearGradient(float x0, float y0, float x1, float y1,
             * int colors[], float positions[], TileMode tile)
             *
             * 注：Android中计算x,y坐标都是以屏幕左上角为原点，向右为x+，向下为y+
             *第一个参数为线性起点的x坐标，第二个参数为线性起点的y坐标
             *第三个参数为线性终点的x坐标，第四个参数为线性终点的y坐标
             *第五个参数为实现渐变效果的颜色的组合，第六个参数为前面的颜色组合中的各颜色
             *在渐变中占据的位置（比重），如果为空，则表示上述颜色的集合在渐变中均匀出现
             *第七个参数为渲染器平铺的模式，一共有三种
*
             *-CLAMP
             *边缘拉伸
             *-REPEAT
             *在水平和垂直两个方向上重复，相邻图像没有间隙
             *-MIRROR
             *以镜像的方式在水平和垂直两个方向上重复，相邻图像有间隙
             *
             */
            LinearGradient mShader = new LinearGradient(0, 0, originX, originY,
                    new int[]{paint.getColor(), Color.TRANSPARENT}, null, Shader.TileMode.CLAMP);
            paint.setShader(mShader);
            canvas.drawPath(path, paint);
        } else {

        }
    }

    /**
     * 数据按比例转换坐标
     * @param num
     * @return
     */
    private float toY(int num) {
        float y;
        try {
            float a = num / 100.0f;
            y = originY - a * yScale;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return y;
    }

}
