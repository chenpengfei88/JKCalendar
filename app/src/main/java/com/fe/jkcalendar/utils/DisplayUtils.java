package com.fe.jkcalendar.utils;

import android.content.Context;

/**
 * Created by chenpengfei on 2016/11/26.
 */
public class DisplayUtils {

    /**
     *  得到屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     *  得到屏幕高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
