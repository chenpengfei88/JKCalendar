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
import com.fe.jkcalendar.utils.CalendarUtils;
import com.fe.jkcalendar.utils.Const;
import com.fe.jkcalendar.utils.DateUtils;
import com.fe.jkcalendar.utils.DisplayUtils;
import com.fe.jkcalendar.utils.StringUtils;
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

        initMonthData(null, 0);
        mLayoutCalendar.addRvView(this, View.inflate(this, R.layout.activity_gridview, null) , new CalendarAdapter(this, new YMonthVO()));
        mLayoutCalendar.addRvView(this, View.inflate(this, R.layout.activity_gridview, null), new CalendarAdapter(this, mYMonth));

        mLayoutCalendar.setOnRotationListener(new CalendarLayout.OnRotationListener() {
            @Override
            public void onRotationEnd(boolean up) {
                mYMonth.setYearMonth(!up ? mNextYMonth.getYearMonth() : mUpYMonth.getYearMonth());
                mYMonth.setDateVOList(!up ? mNextYMonth.getDateVOList() : mUpYMonth.getDateVOList());
                mYMonth.setDay(!up ? mNextYMonth.getDay() : mUpYMonth.getDay());
                setCurrentYMonth();
                getUpNextMonthDayList();
            }

            @Override
            public void onRotationStart(boolean up, RecyclerView recyclerView) {
                CalendarAdapter cAdapter = (CalendarAdapter) recyclerView.getAdapter();
                cAdapter.resetData(up ? mUpYMonth.getDateVOList() : mNextYMonth.getDateVOList());
            }
        });

        mLayoutCalendar.setOnSelectedDateListener(new CalendarLayout.OnSelectedDateListener() {
            @Override
            public void onSelectedDate(int date) {
                mYMonth.setDay(date);
                isDay();
            }
        });
    }

    private void setCurrentYMonth() {
        //当前日期
        mBarHelper.setTitle(mYMonth.toString());
        isDay();
    }

    private void isDay() {
        if(!DateUtils.yearMonthIsCurrentMonth(mYMonth.getDate())) {
            mBarHelper.setRightTitle(getStringContent(R.string.text_today));
        } else {
            mBarHelper.setRightTitle("");
        }
    }

    private void initMonthData(String yearMonth, int day) {
        //当前月份
        mYMonth = StringUtils.isEmpty(yearMonth) ? DateUtils.getCurrentDateDayList() : DateUtils.getYearMonthDayList(yearMonth, day, true);
        getUpNextMonthDayList();
        setCurrentYMonth();
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

    @Override
    protected void onRightOneClick() {
        initMonthData(null, 0);
        mLayoutCalendar.resetViewData(mYMonth.getDateVOList());
    }

    @Override
    protected void onRightTwoClick() {
        CalendarUtils.showCalendar(this, mYMonth.getYear(), mYMonth.getMonth(), mYMonth.getDay(), new CalendarUtils.OnSelectDateListener() {
            @Override
            public void onSelectDate(int year, int month, int day, String yearMonth) {
                initMonthData(yearMonth, day);
                mLayoutCalendar.resetViewData(mYMonth.getDateVOList());
            }
        });
    }
}
