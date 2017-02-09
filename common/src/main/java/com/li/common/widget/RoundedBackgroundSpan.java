package com.li.common.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

/**
 * Created by liweifa on 2015/12/15.
 * 圆角背景样式
 */

public class RoundedBackgroundSpan extends ReplacementSpan {
    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return Math.round(MeasureText(paint, text, start, end));
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        RectF rect = new RectF(x, top, x + MeasureText(paint, text, start, end), bottom);
        paint.setColor(Color.parseColor("#9764d6"));// = Application.Context.Resources.GetColor(Resource.Color.nextTimeBackgroundColor);
        canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2 + 3, paint);
        paint.setColor(Color.WHITE);// = Application.Context.Resources.GetColor(Resource.Color.nextTimeTextColor);
        canvas.drawText(text, start, end, x, y, paint);
    }

    private float MeasureText(Paint paint, CharSequence text, int start, int end) {
        return paint.measureText(text, start, end);
    }
}
