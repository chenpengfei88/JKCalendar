package com.fe.jkcalendar.widget.helper;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;

/**
 * Created by chenpengfei on 2016/11/25.
 */
public class BarHelper {

    private Toolbar mToolBar;

    public BarHelper(Toolbar toolbar) {
        mToolBar = toolbar;
        mToolBar.setTitleTextColor(Color.WHITE);
        mToolBar.setTitle("111111");
    }

    public void setTitle(String title) {
        mToolBar.setTitle(title);
    }
}
