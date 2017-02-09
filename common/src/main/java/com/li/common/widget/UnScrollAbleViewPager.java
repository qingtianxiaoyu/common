package com.li.common.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Finance on 2015/12/16.
 * 禁止滑动的ViewPager
 */
public class UnScrollAbleViewPager extends ViewPager {
    private boolean mScrollable = false;

    public UnScrollAbleViewPager(Context context) {
        super(context);
    }

    public UnScrollAbleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !mScrollable || super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mScrollable && super.onInterceptTouchEvent(ev);

    }

    public boolean isScrollable() {
        return mScrollable;
    }

    public void setScrollable(boolean mScrollable) {
        this.mScrollable = mScrollable;
    }
}
