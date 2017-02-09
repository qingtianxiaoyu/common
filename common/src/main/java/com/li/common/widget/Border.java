package com.li.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;


public class Border extends View {

    public Border(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public Border(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Border(Context context) {
        this(context, null);
    }

    /**
     * Draw nothing.
     *
     * @param canvas an unused parameter.
     */
    @Override
    public void draw(Canvas canvas) {
        drawBackground(canvas);

    }

    private void drawBackground(Canvas canvas) {
        getBackground().setBounds(0, 0, getWidth(), getHeight());
        getBackground().draw(canvas);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                getDefaultSize2(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize2(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    /**
     * Compare to: {@link View#getDefaultSize(int, int)}
     * If mode is AT_MOST, return the child size instead of the parent size
     * (unless it is too big).
     */
    private static int getDefaultSize2(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(size, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }
}
