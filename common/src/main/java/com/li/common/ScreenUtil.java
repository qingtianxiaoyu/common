package com.li.common;

import android.content.Context;

/**
 * Created by liweifa on 2016/11/25.
 */

public class ScreenUtil {


    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     *
     * @param context 使用ApplicationContext
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp==dip
     *
     * @param context 使用ApplicationContext
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
