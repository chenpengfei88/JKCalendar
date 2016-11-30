package com.fe.jkcalendar.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.DimenRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
        mBarHelper = new BarHelper(initToolBar());
        mFlContent = (FrameLayout) findViewById(R.id.fl_content);
        int layoutResId = getLayoutResID();
        if(layoutResId == 0) return;
        mFlContent.addView(View.inflate(this, layoutResId, null));
    }

    private Toolbar initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_bar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.base_toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.action_today) {
                    onRightOneClick();
                }
                if(item.getItemId() == R.id.action_select) {
                    onRightTwoClick();
                }
                return true;
            }
        });
        return toolbar;
    }

    protected void onRightOneClick() {
    }

    protected void onRightTwoClick() {
    }

    protected String[] getStringContentArray(@ArrayRes int id) {
        return getResources().getStringArray(id);
    }

    protected String getStringContent(@StringRes int id) {
        return getResources().getString(id);
    }

    protected int getDimensionPixelSizeContent(@DimenRes int id) {
        return getResources().getDimensionPixelSize(id);
    }
}
