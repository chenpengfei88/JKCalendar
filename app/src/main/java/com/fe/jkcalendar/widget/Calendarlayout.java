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
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import com.fe.jkcalendar.R;
import com.fe.jkcalendar.adapter.home.CalendarAdapter;
import com.fe.jkcalendar.utils.Const;

/**
 * Created by chenpengfei on 2016/11/27.
 *  日历布局
 */
public class CalendarLayout extends FrameLayout {


    //是否初始化
    private boolean isInit = true;
    private float mInitY;
    private float mProgress;
    private float mOutRotation, mInRotation, mRotation = 90;
    private float mOffsetNum = 700;
    private Camera mCamera = new Camera();
    private Matrix mMatrix = new Matrix();

    // 检测到手机的最小滑动值
    private int mTouchSlop;
    //数据是否已经加载
    private boolean isLoad;

    private boolean isUp;

    //底部index
    private int mBottonIndex = 1;

    private OnRotationListener mOnRotationListener;


    public CalendarLayout(Context context) {
        super(context);
    }

    public CalendarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
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
                if(!isUp)
                   drawNextScreen(i, mBottonIndex == 0 ? 1 : 0, canvas);
                else
                    drawUpScreen(i, mBottonIndex == 0 ? 1 : 0, canvas);
            }
        }
    }

    private void drawNextScreen(int index, int judge, Canvas canvas) {
        View view = getChildAt(index);
        int height = view.getHeight();
        int width = view.getWidth();
        canvas.save();
        mCamera.save();
        mCamera.rotateX(index == judge ? mOutRotation : mInRotation);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();

        mMatrix.preTranslate(- width / 2, index == judge ? -height : 0);
        mMatrix.postTranslate(width / 2, index == judge ? height : 0);
        mMatrix.postTranslate(0,  index == judge ? -(int) (height * mProgress) : height * (1 - mProgress));
        canvas.concat(mMatrix);
        drawChild(canvas, view, getDrawingTime());
        canvas.restore();
    }

    private void drawUpScreen(int index, int judge, Canvas canvas) {
        View view = getChildAt(index);
        int height = view.getHeight();
        int width = view.getWidth();
        canvas.save();
        mCamera.save();
        mCamera.rotateX(index == judge ? -mOutRotation : -mInRotation);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();

        mMatrix.preTranslate(-width / 2, index ==judge? 0 : -height);
        mMatrix.postTranslate(width / 2, index == judge ? 0 : height);
        mMatrix.postTranslate(0,  index == judge ? (int) (height * mProgress) : - height * (1 - mProgress));
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
                //向下
                if(moveY < mInitY) {
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
                    mProgress = (mInitY - moveY) / mOffsetNum;
                    if(mProgress < 0) mProgress = 0;
                    if(mProgress > 1) mProgress = 1;
                    mOutRotation = (int) (mRotation * mProgress);
                    mInRotation = -(mRotation - mOutRotation);
                    invalidate();

                    isUp = false;
                } else if(moveY > mInitY){
                    isUp = true;

                    if(!isLoad) {
                        if(mBottonIndex == 1) {
                            mBottonIndex = 0;
                        } else {
                            mBottonIndex = 1;
                        }
                        CardView cardView = (CardView) getChildAt(mBottonIndex);
                        RecyclerView recyclerView = (RecyclerView) cardView.getChildAt(0);
                        mOnRotationListener.onRotationStart(true, recyclerView);
                        isLoad = true;
                    }

                    mProgress = (moveY - mInitY) / mOffsetNum;
                    if(mProgress < 0) mProgress = 0;
                    if(mProgress > 1) mProgress = 1;
                    mOutRotation = (int) (mRotation * mProgress);
                    mInRotation = -(mRotation - mOutRotation);
                    invalidate();
                    isInit = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                float offsetRotation = mRotation - mOutRotation;
                if(offsetRotation <= 0) {
                    isLoad = false;
                    mOnRotationListener.onRotationEnd(isUp);
                    clearHideViewData();
                } else {
                    rotationAnimation(offsetRotation, mOutRotation);
                }
                break;
        }
        return true;
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
                mOnRotationListener.onRotationEnd(isUp);
                isLoad = false;
                clearHideViewData();
            }
        });
    }

    private void clearHideViewData() {
        CardView cardView = (CardView) getChildAt(mBottonIndex == 0 ? 1 : 0);
        RecyclerView recyclerView = (RecyclerView) cardView.getChildAt(0);
        CalendarAdapter calendarAdapter = (CalendarAdapter) recyclerView.getAdapter();
        calendarAdapter.resetData(null);
    }

    public void setOnRotationListener(OnRotationListener onRotationListener) {
        this.mOnRotationListener = onRotationListener;
    }

    public interface OnRotationListener {
        void onRotationEnd(boolean up);
        void onRotationStart(boolean up, RecyclerView recyclerView);
    }
}
