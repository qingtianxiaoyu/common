package com.li.common.widget;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by liweifa on 2016/11/22.
 */

public class ToastUtil {
    private static ToastUtil mToastUtil;
    private Toast mToast;

    private ToastUtil() {
    }

    public static ToastUtil instance() {
        if (mToastUtil == null) {
            synchronized (ToastUtil.class) {
                mToastUtil = new ToastUtil();
            }
        }
        return mToastUtil;
    }

    public boolean isShown() {
        return mToast != null && mToast.getView().isShown();
    }

    /**
     * Make a standard toast that just contains a text view.
     *
     * @param context  The context to use.  Usually your {@link android.app.Application}
     *                 or {@link android.app.Activity} object.
     * @param message  The text to show.  Can be formatted text.
     * @param duration How long to display the message.  Either {@see #Toast.LENGTH_SHORT} or
     *                 {@see #Toast.LENGTH_LONG}
     */
    public void showToast(Context context, CharSequence message, int duration) {


        if (mToast != null && mToast.getView().isShown()) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(context, message, duration);

        mToast.setText(message);
        mToast.show();
    }

    public void showToast(Context context, CharSequence message) {
        showToast(context, message, Toast.LENGTH_SHORT);
    }


}
