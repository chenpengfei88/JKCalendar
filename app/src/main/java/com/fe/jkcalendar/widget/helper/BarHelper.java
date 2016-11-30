package com.fe.jkcalendar.widget.helper;

import android.support.v7.widget.Toolbar;

/**
 * Created by chenpengfei on 2016/11/25.
 */
public class BarHelper {

    private Toolbar mToolBar;

    public BarHelper(Toolbar toolbar) {
        mToolBar = toolbar;
    }

    public void setTitle(String title) {
        mToolBar.setTitle(title);
    }

    public void setRightTitle(String rightTitle) {
        mToolBar.getMenu().getItem(0).setTitle(rightTitle);
    }

}
