package com.fe.jkcalendar.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fe.jkcalendar.utils.Const;

/**
 * Created by chenpengfei on 2016/11/25.
 *  Grid分割线
 */
public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    private int mDividerWH;
    private int mDividerColor;

    public GridDividerItemDecoration() {
        this(1, Color.parseColor("#dddddd"));
    }

    public GridDividerItemDecoration(int dividerWH, int dividerColor) {
        mDividerWH = dividerWH;
        mDividerColor = dividerColor;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mDividerColor);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if((i + 1) % Const.SPAN_COUNT == 0 && i != 0) {
                final int right = child.getRight();
                final int top = child.getBottom();
                final int bottom = top + mDividerWH;
                c.drawRect(0, top, right, bottom, mPaint);
            }
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int rows = parent.getChildCount() / Const.SPAN_COUNT - 1;
        final int count = rows * Const.SPAN_COUNT;
        for (int i = 0; i < Const.SPAN_COUNT; i++) {
            final View child = parent.getChildAt(count + i);
            final int left = child.getRight();
            final int right = left + mDividerWH;
            final int bottom = child.getBottom();
            c.drawRect(left, 0, right, bottom, mPaint);
        }
    }
}
