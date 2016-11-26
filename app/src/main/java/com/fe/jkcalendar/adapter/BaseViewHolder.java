package com.fe.jkcalendar.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by chenpengfei on 2016/11/25.
 * 基类ViewHolder
 */
public abstract class BaseViewHolder<E> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
        initView(itemView);

    }

    protected abstract void initView(View itemView);

    protected abstract void onBindData(E data, int position);
}
