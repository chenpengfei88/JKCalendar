package com.fe.jkcalendar.activity.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.fe.jkcalendar.R;
import com.fe.jkcalendar.adapter.home.CalendarAdapter;
import com.fe.jkcalendar.base.BaseActivity;
import com.fe.jkcalendar.utils.Const;
import com.fe.jkcalendar.utils.DateUtils;
import com.fe.jkcalendar.utils.DisplayUtils;
import com.fe.jkcalendar.vo.YMonthVO;
import com.fe.jkcalendar.widget.CalendarLayout;

/**
 * Created by chenpengfei 2016/11/25.
 * 首页
 */
public class MainActivity extends BaseActivity {

    /**
     * 日历布局
     */
    private CalendarLayout mLayoutCalendar;

    /**
     *  周
     */
    private LinearLayout mLlWeek;

    /**
     * 当前年月
     */
    private YMonthVO mYMonth, mNextYMonth, mUpYMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewData();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    private void initViewData() {
        //周视图
        mLlWeek = (LinearLayout) findViewById(R.id.ll_week);
        addWeekViews();

        //日历layout
        mLayoutCalendar = (CalendarLayout) findViewById(R.id.layout_calendar);
        //当前月份，下个月，下下个月的天数
        mYMonth = DateUtils.getCurrentDateDayList();

        mLayoutCalendar.addRvView(this, View.inflate(this, R.layout.activity_gridview, null) , new CalendarAdapter(this));
        mLayoutCalendar.addRvView(this, View.inflate(this, R.layout.activity_gridview, null), new CalendarAdapter(this, mYMonth.getDateVOList()));

        getUpNextMonthDayList();
        mLayoutCalendar.setOnRotationListener(new CalendarLayout.OnRotationListener() {
            @Override
            public void onRotationEnd(boolean up) {
                mYMonth.setYearMonth(!up ? mNextYMonth.getYearMonth() : mUpYMonth.getYearMonth());
                mYMonth.setDateVOList(!up ? mNextYMonth.getDateVOList() : mUpYMonth.getDateVOList());
                setCurrentYMonth();
                getUpNextMonthDayList();
            }

            @Override
            public void onRotationStart(boolean up, RecyclerView recyclerView) {
                CalendarAdapter cAdapter = (CalendarAdapter) recyclerView.getAdapter();
                cAdapter.resetData(up ? mUpYMonth.getDateVOList() : mNextYMonth.getDateVOList());
            }
        });
        setCurrentYMonth();
    }

    private void setCurrentYMonth() {
        //当前日期
        mBarHelper.setTitle(mYMonth.toString());
    }

    private void getUpNextMonthDayList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mNextYMonth = DateUtils.getYearNextOrUpMonthDayList(mYMonth.getYearMonth(), 1);
                mUpYMonth = DateUtils.getYearNextOrUpMonthDayList(mYMonth.getYearMonth(), -1);

            }
        }).start();
    }


    /**
     *  添加周视图
     */
    private void addWeekViews() {
        String[] weekArray = getStringContentArray(R.array.week_array);
        int length = weekArray.length;
        for(int i = 0; i < length; i++) {
            mLlWeek.addView(creatTextView(weekArray[i]));
        }
    }

    private TextView creatTextView(String week) {
        TextView tvWeek = new TextView(this);
        tvWeek.setText(week);
        tvWeek.setTextSize(TypedValue.COMPLEX_UNIT_PX, getDimensionPixelSizeContent(R.dimen.text_typeface_size_four));
        tvWeek.setGravity(Gravity.CENTER);
        int width = DisplayUtils.getScreenWidth(this) / Const.SPAN_COUNT;
        LinearLayout.LayoutParams lpWeek = new LinearLayout.LayoutParams(width, width * 2/3);
        tvWeek.setLayoutParams(lpWeek);
        return tvWeek;
    }



}
