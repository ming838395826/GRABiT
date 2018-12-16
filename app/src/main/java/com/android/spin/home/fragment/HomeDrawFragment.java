package com.android.spin.home.fragment;

import android.os.Bundle;

import com.android.base.base.BaseFragment;
import com.android.spin.R;
/**
 * 作者：yangqiyun
 * 时间：2017/7/21
 * 1120389276@qq.com
 * 描述：抽奖
 */
public class HomeDrawFragment extends BaseFragment {

    public HomeDrawFragment() {
        // Required empty public constructor
    }

    public static HomeDrawFragment newInstance() {
        HomeDrawFragment fragment = new HomeDrawFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_draw;
    }

    @Override
    public void initView() {

    }
}
