package com.li.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by liweifa on 2016/12/1.
 * 没有任何触摸事件的RecycleView
 */

public class NotTouchSlideRecycleView extends RecyclerView {
    public NotTouchSlideRecycleView(Context context) {
        super(context);
    }

    public NotTouchSlideRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NotTouchSlideRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }
}
