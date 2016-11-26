package com.fe.jkcalendar.adapter.home;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fe.jkcalendar.R;
import com.fe.jkcalendar.adapter.BaseAdapter;
import com.fe.jkcalendar.adapter.BaseViewHolder;
import com.fe.jkcalendar.utils.DisplayUtils;
import com.fe.jkcalendar.vo.DateVO;
import java.util.List;

/**
 * Created by chenpengfei on 2016/11/25.
 *  日历适配器
 */
public class CalendarAdapter extends BaseAdapter<DateVO> {

    private int mSelectPosition;

    public CalendarAdapter(Context context, List<DateVO> dataList) {
        super(context, dataList);
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
            if(dateVo.getDate() == -1) {
                llDate.setVisibility(View.INVISIBLE);
            } else {
                llDate.setVisibility(View.VISIBLE);
                tvDate.setText(dateVo.getDate() + "");
                tvLunarDate.setText(dateVo.getLunarDate());
            }
            if(dateVo.isSelect()) mSelectPosition = position;
            //颜色的改变
            tvDate.setTextColor(dateVo.isSelect() ? Color.WHITE : mContext.getResources().getColor(R.color.color_f333333));
            tvLunarDate.setTextColor(dateVo.isSelect() ? Color.WHITE : mContext.getResources().getColor(R.color.color_f999999));
            rlRoot.setBackgroundColor(!dateVo.isSelect() ? Color.TRANSPARENT : mContext.getResources().getColor(R.color.colorPrimary));

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
