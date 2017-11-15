package com.example.administrator.androidcharts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initParams();
    }

    protected abstract int getLayoutId();

    protected abstract void initParams();

    @Override
    public void onClick(View v) {

    }

    public void setOnViewClickListener(View... views){
        if (views != null && views.length > 0){
            for (View view : views){
                view.setOnClickListener(this);
            }
        }
    }

    public void goTo(@NonNull Class<?> activity){
        Intent intent  = new Intent(this,activity);
        startActivity(intent);
    }
}
