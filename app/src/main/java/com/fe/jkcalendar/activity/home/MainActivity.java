package com.fe.jkcalendar.activity.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fe.jkcalendar.R;
import com.fe.jkcalendar.activity.BaseActivity;
import com.fe.jkcalendar.adapter.BaseAdapter;
import com.fe.jkcalendar.adapter.home.CalendarAdapter;
import com.fe.jkcalendar.utils.Const;
import com.fe.jkcalendar.utils.DateUtils;
import com.fe.jkcalendar.utils.DisplayUtils;
import com.fe.jkcalendar.vo.DateVO;
import com.fe.jkcalendar.widget.GridDividerItemDecoration;
import java.util.List;

/**
 * Created by chenpengfei 2016/11/25.
 * 首页
 */
public class MainActivity extends BaseActivity {

    /**
     * 日历
     */
    private RecyclerView mRvCalendar;

    /**
     *  周
     */
    private LinearLayout mLlWeek;

    /**
     *  日历适配器
     */
    private CalendarAdapter mAdapterCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mLlWeek = (LinearLayout) findViewById(R.id.ll_week);
        addWeekViews();

        mRvCalendar = (RecyclerView) findViewById(R.id.rv_calendar);
        mRvCalendar.setLayoutManager(new GridLayoutManager(this, Const.SPAN_COUNT));
        mRvCalendar.addItemDecoration(new GridDividerItemDecoration());
        List<DateVO> dateVOList = DateUtils.getCurrentDateDayList();
        mAdapterCalendar = new CalendarAdapter(this, dateVOList);
        mRvCalendar.setAdapter(mAdapterCalendar);
        mAdapterCalendar.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int selectPosition = mAdapterCalendar.updateSelectPositionVO();
                DateVO dateVO = mAdapterCalendar.getItemData(position);
                dateVO.setSelect(true);
                mAdapterCalendar.notifyItemsChanged(selectPosition, position);
            }
        });

        DateVO currentDateVo = DateUtils.getCurrentDateVo(dateVOList);
        mBarHelper.setTitle(currentDateVo.toString());
    }

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
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }
}
