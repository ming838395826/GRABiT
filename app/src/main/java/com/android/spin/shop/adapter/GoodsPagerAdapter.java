package com.android.spin.shop.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.android.spin.shop.entity.ShopProductItemEntity;
import com.android.spin.shop.fragment.GoodsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：yangqiyun
 * 时间：2017/8/17
 * 1120389276@qq.com
 * 描述：
 */

public class GoodsPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragments;
    private List<ShopProductItemEntity> list;
    public GoodsPagerAdapter(FragmentManager fm, ArrayList<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    public void setmFragments(ArrayList<Fragment> mFragments){
        this.mFragments = mFragments;
    }

    public void setList(List<ShopProductItemEntity> list){
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        if(mFragments == null){
            return null;
        }

        if(mFragments.get(position) == null){
            return null;
        }
        if(mFragments.get(position) instanceof GoodsFragment){
            GoodsFragment goodsFragment = (GoodsFragment) mFragments.get(position);
//            goodsFragment.setData(list.get(position));
            return goodsFragment;
        }
        return mFragments == null?null:mFragments.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        GoodsFragment fragment = (GoodsFragment) super.instantiateItem(container, position);
        fragment.setData(list.get(position));
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragments == null?0:mFragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
