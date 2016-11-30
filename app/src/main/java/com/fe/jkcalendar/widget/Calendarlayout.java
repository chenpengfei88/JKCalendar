package com.fe.jkcalendar.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.fe.jkcalendar.R;
import com.fe.jkcalendar.adapter.home.CalendarAdapter;
import com.fe.jkcalendar.utils.Const;
import com.fe.jkcalendar.vo.DateVO;

import java.util.List;

/**
 * Created by chenpengfei on 2016/11/27.
 *  日历布局
 */
public class CalendarLayout extends FrameLayout {


    //是否初始化
    private boolean isInit = true;
    //数据是否已经加载
    private boolean isLoad;
    //是否是滑动上个月数据
    private boolean isUpMonth;

    private float mInitY;
    //滑动进度
    private float mProgress;
    //滑出化劲的角度
    private float mOutRotation, mInRotation, mRotation = 90;
    //滑动的距离
    private float mOffsetNum = 700;
    //底部index
    public int mBottonIndex = 1;

    //向上滑动
    private static final int SLIDE_UP = 1;
    //向下滑动
    private static final int SLIDE_DOWN = 2;

    private int mCurrentSlide;
    //是否在执行动画
    private boolean isAnimation;

    private Camera mCamera = new Camera();
    private Matrix mMatrix = new Matrix();
    private OnRotationListener mOnRotationListener;


    public CalendarLayout(Context context) {
        super(context);
    }

    public CalendarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CalendarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CalendarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        if(isInit) {
            super.dispatchDraw(canvas);
        } else {
            for(int i = 0 ; i < getChildCount(); i++) {
                drawScreen(i, mBottonIndex == 0 ? 1 : 0, canvas);
            }
        }
    }

    private void drawScreen(int index, int judge, Canvas canvas) {
        View view = getChildAt(index);
        int height = view.getHeight();
        int width = view.getWidth();
        canvas.save();
        mCamera.save();
        mCamera.rotateX(index == judge ? isUpMonth ? -mOutRotation : mOutRotation : isUpMonth ? -mInRotation : mInRotation);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();

        mMatrix.preTranslate(- width / 2, index == judge ? isUpMonth ? 0 : -height : isUpMonth ? -height : 0);
        mMatrix.postTranslate(width / 2, index == judge ? isUpMonth ? 0 : height : isUpMonth ? height : 0);
        float moveOffsetOne = (height * mProgress);
        float moveOffsetTwo = height * (1 - mProgress);
        mMatrix.postTranslate(0,  index == judge ? isUpMonth ? moveOffsetOne : -moveOffsetOne : isUpMonth ? -moveOffsetTwo : moveOffsetTwo);
        canvas.concat(mMatrix);
        drawChild(canvas, view, getDrawingTime());
        canvas.restore();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInitY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();
                if(moveY == mInitY || isAnimation) break;
                //向上滑动
                if(moveY < mInitY || mCurrentSlide == SLIDE_UP) {
                    if(mCurrentSlide != SLIDE_DOWN) {
                        slide(moveY, true, SLIDE_UP, false);
                    }
                } else if(moveY > mInitY || mCurrentSlide == SLIDE_DOWN) {
                    if(mCurrentSlide != SLIDE_UP) {
                        slide(moveY, false, SLIDE_DOWN, true);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mCurrentSlide = 0;
                float offsetRotation = mRotation - mOutRotation;
                if(offsetRotation <= 0) {
                    isLoad = false;
                    mOnRotationListener.onRotationEnd(isUpMonth);
                    resetViewData(mBottonIndex == 0 ? 1 : 0, null);
                } else {
                    isAnimation = true;
                    rotationAnimation(offsetRotation, mOutRotation);
                }
                break;
        }
        return true;
    }

    //滑动
    private void slide(float moveY, boolean slideUp, int slide, boolean upMonth) {
        isUpMonth = upMonth;
        mCurrentSlide = slide;
        if(!isLoad) {
            if(mBottonIndex == 1) {
                mBottonIndex = 0;
            } else {
                mBottonIndex = 1;
            }
            CardView cardView = (CardView) getChildAt(mBottonIndex);
            RecyclerView recyclerView = (RecyclerView) cardView.getChildAt(0);
            mOnRotationListener.onRotationStart(false, recyclerView);
            isLoad = true;
        }
        isInit = false;
        mProgress = (slideUp ? mInitY - moveY : moveY - mInitY) / mOffsetNum;
        if(mProgress < 0) mProgress = 0;
        if(mProgress > 1) mProgress = 1;
        mOutRotation = (int) (mRotation * mProgress);
        mInRotation = -(mRotation - mOutRotation);
        invalidate();
    }

    public void addRvView(Context context, View view, CalendarAdapter calendarAdapter) {
        addView(view);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_caldenar);
        recyclerView.setLayoutManager(new GridLayoutManager(context, Const.SPAN_COUNT));
        recyclerView.addItemDecoration(new GridDividerItemDecoration());
        recyclerView.setAdapter(calendarAdapter);
    }

    private void rotationAnimation(final float rotation, final float outRotation) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration((long) (rotation * 8));
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                mOutRotation = outRotation + rotation * progress;
                mInRotation = - (mRotation - mOutRotation);
                mProgress = mOutRotation / mRotation;
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mOnRotationListener.onRotationEnd(isUpMonth);
                isLoad = false;
                isAnimation = false;
                resetViewData(mBottonIndex == 0 ? 1 : 0, null);
            }
        });
    }

    public void resetViewData(int index, List<DateVO> dateVOList) {
        CardView cardView = (CardView) getChildAt(index);
        RecyclerView recyclerView = (RecyclerView) cardView.getChildAt(0);
        CalendarAdapter calendarAdapter = (CalendarAdapter) recyclerView.getAdapter();
        calendarAdapter.resetData(dateVOList);
    }

    public void setOnRotationListener(OnRotationListener onRotationListener) {
        this.mOnRotationListener = onRotationListener;
    }

    public interface OnRotationListener {
        void onRotationEnd(boolean up);
        void onRotationStart(boolean up, RecyclerView recyclerView);
    }
}
