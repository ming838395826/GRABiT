package com.android.spin.card.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 作者：yangqiyun
 * 时间：2017/8/17
 * 1120389276@qq.com
 * 描述：
 */

public class CardListPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragments;
    private String[] mTitles;
    public CardListPagerAdapter(FragmentManager fm, ArrayList<Fragment> mFragments, String[] mTitles) {
        super(fm);
        this.mFragments = mFragments;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments == null?null:mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null?0:mFragments.size();
    }
}
