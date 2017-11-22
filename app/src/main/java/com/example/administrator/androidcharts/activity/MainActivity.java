package com.example.administrator.androidcharts.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.administrator.androidcharts.R;
import com.example.administrator.androidcharts.utils.ActivityRouter;
import com.example.administrator.androidcharts.view.ChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends ListActivity {

    private ChartView mChartView;
    private List<? extends Map<String, ?>> mDatas = new ArrayList<>();
    private String[] mFrom;
    private int[] mTo;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(android.R.id.list);
        initParams();
        mListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice,mFrom));




       /* mChartView = (ChartView) findViewById(R.id.columnView);
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
        mChartView.setColumnInfo(columnInfo);*/
    }

    private void initParams() {
        mFrom = getResources().getStringArray(R.array.chart_array);
        mTo = new int[]{R.id.tv_item_func};
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(MainActivity.this,BarChartActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        ActivityRouter.goToTarget(MainActivity.this, CurveChartActivity.class);
                        break;
                    case 2:
                        ActivityRouter.goToTarget(MainActivity.this,LineChartActivity.class);
                        break;
                    case 3:
                        ActivityRouter.goToTarget(MainActivity.this,KChartActivity.class);
                        break;
                    case 4:
                        ActivityRouter.goToTarget(MainActivity.this,PieChartActivity.class);
                        break;
                    case 5:
                        ActivityRouter.goToTarget(MainActivity.this,RadarChartActivity.class);
                        break;
                    case 6:
                        ActivityRouter.goToTarget(MainActivity.this,SimpleGraphActivity.class);
                        break;
                    case 7:
                        ActivityRouter.goToTarget(MainActivity.this,SimpleColumnActivity.class);
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                }
            }
        });

    }
}
