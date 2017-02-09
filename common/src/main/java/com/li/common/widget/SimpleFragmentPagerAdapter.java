package com.li.common.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by liweifa on 2016/12/1.
 */

public class SimpleFragmentPagerAdapter extends FragmentStatePagerAdapter {
    Fragment[] mFragments;

    public void setmFragments(Fragment[] mFragments) {
        this.mFragments = mFragments;
    }

    public SimpleFragmentPagerAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }
}
