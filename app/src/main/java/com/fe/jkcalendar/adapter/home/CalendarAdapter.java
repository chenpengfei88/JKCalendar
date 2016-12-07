package com.fe.jkcalendar.adapter.home;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fe.jkcalendar.JKApplication;
import com.fe.jkcalendar.R;
import com.fe.jkcalendar.base.BaseAdapter;
import com.fe.jkcalendar.base.BaseViewHolder;
import com.fe.jkcalendar.utils.DisplayUtils;
import com.fe.jkcalendar.utils.StringUtils;
import com.fe.jkcalendar.vo.DateVO;
import com.fe.jkcalendar.vo.YMonthVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenpengfei on 2016/11/25.
 *  日历适配器
 */
public class CalendarAdapter extends BaseAdapter<DateVO> {

    private int mSelectPosition;

    private YMonthVO mCurrentYMonthVo;

    public CalendarAdapter(Context context, YMonthVO yMonthVO) {
        super(context, yMonthVO.getDateVOList());
        mCurrentYMonthVo = yMonthVO;
    }

    public void setDay(int day) {
        mCurrentYMonthVo.setDay(day);
    }

    @Override
    protected CalendarViewHolder getViewHolder() {
        return new CalendarViewHolder(View.inflate(mContext, R.layout.activity_gridview_item, null));
    }

    public int updateSelectPositionVO() {
        DateVO dateVO = getItemData(mSelectPosition);
        dateVO.setSelect(false);
        return mSelectPosition;
    }

    public boolean isSelected(int position) {
        return position == mSelectPosition;
    }


    public class CalendarViewHolder extends BaseViewHolder<DateVO> {

        TextView tvDate, tvLunarDate;
        LinearLayout llDate;
        RelativeLayout rlRoot;

        public CalendarViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvLunarDate = (TextView) itemView.findViewById(R.id.tv_lunar_date);
            llDate = (LinearLayout) itemView.findViewById(R.id.ll_date);
            rlRoot = (RelativeLayout) itemView.findViewById(R.id.rl_root);
            int itemWH = DisplayUtils.getScreenWidth(mContext) / 7;
            RelativeLayout.LayoutParams rootRlLp = new RelativeLayout.LayoutParams(itemWH, itemWH);
            rlRoot.setLayoutParams(rootRlLp);
        }

        @Override
        protected void onBindData(DateVO dateVo, final int position) {
            String hw = JKApplication.jkApplication.getInfo(dateVo.getCurrentDate());
            if(dateVo.getDate() == -1) {
                llDate.setVisibility(View.INVISIBLE);
            } else {
                llDate.setVisibility(View.VISIBLE);
                tvDate.setText(dateVo.getDate() + "");
                tvLunarDate.setText(StringUtils.isEmpty(hw) ? dateVo.getLunarDate() : hw);
            }
            //选中
            if(dateVo.isSelect()) {
                mSelectPosition = position;
                tvDate.setTextColor(Color.WHITE);
                tvLunarDate.setTextColor(Color.WHITE);
                rlRoot.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
            } else {
                tvDate.setTextColor(mContext.getResources().getColor(R.color.color_f333333));
                rlRoot.setBackgroundColor(Color.TRANSPARENT);
                //不是休假
                if(StringUtils.isEmpty(hw)) {
                    tvLunarDate.setTextColor(mContext.getResources().getColor(R.color.color_f999999));
                } else {
                    tvLunarDate.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                }
            }

            if(rlRoot != null && mOnItemClickListener != null) {
                rlRoot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(rlRoot, position);
                    }
                });
            }
        }
    }


}
