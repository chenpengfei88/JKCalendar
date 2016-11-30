package com.fe.jkcalendar.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenpengfei on 2016/11/25.
 *  基类adapter
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private List<T> mDataList;
    protected Context mContext;
    protected OnItemClickListener mOnItemClickListener;

    public BaseAdapter(Context context, List<T> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getViewHolder();
    }

    protected abstract BaseViewHolder getViewHolder();

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindData(mDataList.get(position), position);
    }

    public T getItemData(int position) {
        return mDataList.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public void notifyItemsChanged(int... positons) {
        for(int i = 0; i < positons.length; i++) {
            notifyItemChanged(positons[i]);
        }
    }

    public void resetData(List<T> dataList) {
        if(mDataList == null) mDataList = new ArrayList<>();
        int size = mDataList.size();
        mDataList.clear();
        if(dataList != null)
        mDataList.addAll(dataList);
        notifyItemRangeChanged(0, dataList != null ? dataList.size() : size);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
