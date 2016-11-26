package com.fe.jkcalendar.activity;

import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.DimenRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import com.fe.jkcalendar.R;
import com.fe.jkcalendar.widget.helper.BarHelper;

/**
 * Created by chenpengfei on 2016/11/25.
 *  基类activity
 */
public abstract class BaseActivity extends AppCompatActivity  {

    protected BarHelper mBarHelper;
    private FrameLayout mFlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initView();
    }


    /**
     *  得到布局Layout 资源ID
     */
    protected abstract int getLayoutResID();


    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_bar);
        mBarHelper = new BarHelper(toolbar);
        mFlContent = (FrameLayout) findViewById(R.id.fl_content);
        int layoutResId = getLayoutResID();
        if(layoutResId == 0) return;
        mFlContent.addView(View.inflate(this, layoutResId, null));
    }

    protected String[] getStringContentArray(@ArrayRes int id) {
        return getResources().getStringArray(id);
    }

    protected int getDimensionPixelSizeContent(@DimenRes int id) {
        return getResources().getDimensionPixelSize(id);
    }
}
