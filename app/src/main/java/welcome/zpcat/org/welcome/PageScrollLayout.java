/*
 * Copyright (c) 2011-2015. ShenZhen iBOXPAY Information Technology Co.,Ltd.
 *
 * All right reserved.
 *
 * This software is the confidential and proprietary
 * information of iBoxPay Company of China.
 * ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only
 * in accordance with the terms of the contract agreement
 * you entered into with iBoxpay inc.
 */

package welcome.zpcat.org.welcome;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class PageScrollLayout extends ViewGroup {


    private static final int TOUCH_STATE_REST = 0;
    private static final int TOUCH_STATE_SCROLLING = 1;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mCurScreen;
    private int mDefaultScreen = 0;
    private int mTouchState = TOUCH_STATE_REST;
    private int mTouchSlop;
    private float mLastMotionX;
    private OnPagescrollLayoutListener mScrollerListener;
    private int mNumOfTabs = 0;
    private static final int mScreenNum = 2;

    public PageScrollLayout(Context context) {
        super(context);
    }

    public PageScrollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageScrollLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScroller = new Scroller(context);
        mCurScreen = mDefaultScreen;
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    }

    public void snapToDestination() {
        final int screenWidth = getWidth();
        final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
        snapToScreen(destScreen);
    }

    public void snapToScreen(int whichScreen) {

        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
        if (getScrollX() != (whichScreen * getWidth())) {
            final int delta = whichScreen * getWidth() - getScrollX();
            mScroller.startScroll(getScrollX(), 0, delta, 0, Math.abs(delta) * 2);
//            mCurScreen = whichScreen;
            invalidate();
        }
    }

    public void setToScreen(int whichScreen) {

        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
        mCurScreen = whichScreen;
        scrollTo(whichScreen * getWidth(), 0);

    }

    public int getCurScreen() {
        return mCurScreen;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childLeft = 0;
            final int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View childView = getChildAt(i);
                if (childView.getVisibility() != View.GONE) {
                    final int childWidth = childView.getMeasuredWidth();
                    childView.layout(childLeft, 0, childLeft + childWidth,
                            childView.getMeasuredHeight());
                    childLeft += childWidth;
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException("ScrollLayout only canmCurScreen run at EXACTLY mode!");
        }

        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException("ScrollLayout only can run at EXACTLY mode!");
        }

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
        scrollTo(mCurScreen * width, 0);
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();

        }
    }

    public void setScrollListener(OnPagescrollLayoutListener mScrollerListener) {
        this.mScrollerListener = mScrollerListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        final int action = event.getAction();
        final float x = event.getX();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                break;

            case MotionEvent.ACTION_MOVE:

                int deltaX = (int) (mLastMotionX - x);
                mLastMotionX = x;
                if (mNumOfTabs > 0) {
                    if (mCurScreen == 0) {//第一页的情况
                        if (deltaX > 0) {
                            snapToScreen(mCurScreen + 1);
                            mScrollerListener.scrollTo(mCurScreen + 1);
                            mCurScreen++;
                            mNumOfTabs -= 1;
                        }
                    } else if (mCurScreen < mScreenNum && mCurScreen > 0) { //第二页，末页之前一页之间的情况
                        if (deltaX < 0) {
                            snapToScreen(mCurScreen - 1);
                            mScrollerListener.scrollTo(mCurScreen - 1);
                            mNumOfTabs -= 1;
                            mCurScreen--;
                        } else if (deltaX > 0) {
                            snapToScreen(mCurScreen + 1);
                            mScrollerListener.scrollTo(mCurScreen + 1);
                            mNumOfTabs -= 1;
                            mCurScreen++;
                        }
                    } else if (mCurScreen == mScreenNum) { //最后一页的情况
                        if (deltaX < 0) {
                            snapToScreen(mCurScreen - 1);
                            mScrollerListener.scrollTo(mCurScreen - 1);
                            mCurScreen--;
                            mNumOfTabs -= 1;
                        } else if (deltaX > 0) {
                            mNumOfTabs -= 1;
                            mScrollerListener.scrollToNext();
                        }
                    } else {
                        if (deltaX > 0) {
                            snapToScreen(mCurScreen + 1);
                            mScrollerListener.scrollTo(mCurScreen + 1);
                            mCurScreen++;
                            mNumOfTabs -= 1;
                        } else if (deltaX < 0) {
                            snapToScreen(mCurScreen - 1);
                            mScrollerListener.scrollTo(mCurScreen - 1);
                            mCurScreen--;
                            mNumOfTabs -= 1;
                        } else {
                        }
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                mNumOfTabs = 0;
                mTouchState = TOUCH_STATE_REST;
                break;
            case MotionEvent.ACTION_CANCEL:
                mTouchState = TOUCH_STATE_REST;
                break;
        }

        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        final int action = ev.getAction();
        if ((action == MotionEvent.ACTION_MOVE) && (mTouchState != TOUCH_STATE_REST)) {
            return true;
        }
        final float x = ev.getX();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                final int xDiff = (int) Math.abs(mLastMotionX - x);
                if (xDiff > mTouchSlop) {
                    mTouchState = TOUCH_STATE_SCROLLING;

                }
                break;
            case MotionEvent.ACTION_DOWN:

                mNumOfTabs += 1;
                mLastMotionX = x;
                mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mTouchState = TOUCH_STATE_REST;
                break;
        }
        return mTouchState != TOUCH_STATE_REST;
    }

    public static interface OnPagescrollLayoutListener {

        void scrollToNext();

        void scrollTo(int whichScreen);
    }

}
