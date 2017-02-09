package com.li.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.li.common.R;

/**
 * Created by liweifa on 2016/11/22.
 */

public class MyToolbar extends Toolbar {
    String mCenterTitle;
    TextView mCenterTitleTextView;
    float mCenterTitleSize;
    int mCenterTitleColor;

    public MyToolbar(Context context) {
        this(context, null);
    }


    public MyToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.toolbarStyle);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyToolbar, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.MyToolbar_center_title) {
                mCenterTitle = typedArray.getString(attr);


            } else if (attr == R.styleable.MyToolbar_center_title_size) {
                mCenterTitleSize = typedArray.getDimension(attr, 40);

            } else if (attr == R.styleable.MyToolbar_center_title_color) {
                mCenterTitleColor = typedArray.getColor(attr, Color.parseColor("#ffffff"));

            }
        }
        if (!TextUtils.isEmpty(mCenterTitle)) {
            addCenterTitle(mCenterTitle);
        }

    }

    private void addCenterTitle(String centerTitle) {
        mCenterTitleTextView = new TextView(this.getContext());
        mCenterTitleTextView.setLayoutParams(getDefaultLayoutParams());
        mCenterTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
        mCenterTitleTextView.setLines(1);
        mCenterTitleTextView.setMaxEms(6);
        mCenterTitleTextView.setGravity(Gravity.CENTER);
        mCenterTitleTextView.setTextColor(Color.WHITE);
        if (mCenterTitleSize != -1) {
            mCenterTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCenterTitleSize);
        }
        mCenterTitleTextView.setText(centerTitle);
        addView(mCenterTitleTextView);

    }

    public void setCenterTitle(CharSequence sequence) {
        if (sequence == null) {
            return;
        } else {
            mCenterTitle = sequence.toString();
            if (mCenterTitleTextView == null) {
                addCenterTitle(mCenterTitle);
            } else {
                mCenterTitleTextView.setText(sequence);
            }

        }
    }

    private ViewGroup.LayoutParams getDefaultLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        return layoutParams;
    }

    public void initActionBar(boolean homeAsUpEnable) {
        if (getContext() instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
            appCompatActivity.setSupportActionBar(this);
            appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnable);

        }

    }

}
