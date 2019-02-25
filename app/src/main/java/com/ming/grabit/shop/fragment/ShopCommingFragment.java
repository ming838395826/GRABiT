package com.ming.grabit.shop.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.base.base.MvpFragment;
import com.android.base.module.http.callback.ShowApiListResponse;
import com.android.base.mvp.view.IView;
import com.android.base.util.DensityUtil;
import com.ming.grabit.R;
import com.ming.grabit.home.entity.ProUpdateEvent;
import com.ming.grabit.home.fragment.HomeCardFragment;
import com.ming.grabit.shop.adapter.GoodsPagerAdapter;
import com.ming.grabit.shop.entity.ShopProductItemEntity;
import com.ming.grabit.shop.presenter.ShopPresenter;
import com.ming.grabit.view.gallery.IndicatorContainer;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：yangqiyun
 * 时间：2017/9/6
 * 1120389276@qq.com
 * 描述：发现好物
 */
public class ShopCommingFragment extends MvpFragment<IView, ShopPresenter> implements
        OnTabSelectListener,ViewPager.OnPageChangeListener{

    private List<ShopProductItemEntity> items;

    @Bind(R.id.vp_comming_chind_content)
    ViewPager vpChindContent;
    @Bind(R.id.ctl_nav_good_comming)
    CommonTabLayout ctlNavGood;
    @Bind(R.id.dc_comming_indicator)
    IndicatorContainer dcCommingIndicator;

    private final static int TYPE_REQUEST_COMMING = 0x00;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private GoodsPagerAdapter mGoodsPagerAdapter;

    @Override
    public ShopPresenter initPresenter() {
        return new ShopPresenter();
    }

    public ShopCommingFragment() {
        // Required empty public constructor
    }

    public static ShopCommingFragment newInstance(String status) {
        ShopCommingFragment fragment = new ShopCommingFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("status", "1");
        fragment.setArguments(mBundle);
        return fragment;
    }

    public ShopProductItemEntity getShopProductItemEntity(){
        return items.get(ctlNavGood.getCurrentTab());
    }

    @Override
    public void onFail(String code, int type) {
        if(type == TYPE_REQUEST_COMMING){
            showNetErrorData();
        }
    }

    @Override
    public void onComplete(int type) {
        dismissLoadDialog();
    }

    @Override
    public void onSuccess(Object data, int type) {
        switch (type) {
            case TYPE_REQUEST_COMMING:
                //即将开抢
                ShowApiListResponse<ShopProductItemEntity> mShopProducts = (ShowApiListResponse<ShopProductItemEntity>) data;
                items = mShopProducts.getData();
                if(items == null || items.size() == 0){
                    showEmptyData();
                }else{
                    hideEmptyLayoutData();
                }
                initPages(items);
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_comming_shop;
    }

    @Override
    public void initView() {
        getData();

//
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    /**
     * 初始化数据
     */
    private void initPages(List<ShopProductItemEntity> list) {
        if (list == null) {
            return;
        }
        mFragments.clear();
        mTabEntities.clear();

        vpChindContent.removeAllViews();
        vpChindContent.removeAllViewsInLayout();
        for (int i = 0; i < list.size(); i++) {
            mTabEntities.add(new HomeCardFragment.TabEntity(null, 0, 0));
            mFragments.add(GoodsFragment.newInstance(list.get(i), 1,list.size()));
        }
//        vpChindContent.setOffscreenPageLimit(6);
        if(mGoodsPagerAdapter == null){
            mGoodsPagerAdapter = new GoodsPagerAdapter(getFragmentManager(), mFragments);
            mGoodsPagerAdapter.setList(list);
            vpChindContent.setAdapter(mGoodsPagerAdapter);
            ctlNavGood.setOnTabSelectListener(this);
            vpChindContent.addOnPageChangeListener(this);
        }else{
            mGoodsPagerAdapter.setList(list);
            mGoodsPagerAdapter.notifyDataSetChanged();
        }

        vpChindContent.addOnPageChangeListener(this);
        ctlNavGood.setTabData(mTabEntities);
        int itemWidth = DensityUtil.getWidth() / list.size() - DensityUtil.dp2px(5);
        ctlNavGood.setIndicatorWidth(DensityUtil.px2dp(itemWidth));
        ctlNavGood.setDividerColor(Color.parseColor("#ffffff"));
        ctlNavGood.setDividerWidth(10);
        ctlNavGood.setDividerPadding(10);
        ctlNavGood.setTabPadding(10);
        if(mTabEntities != null && mTabEntities.size() > 0){
            dcCommingIndicator.setNum(mTabEntities.size());
            int itemWidth1 = (DensityUtil.getWidth() - DensityUtil.dp2px(1) * (mTabEntities.size() - 1)) / mTabEntities.size();
            dcCommingIndicator.setIndicatorWidth(itemWidth1);
        }
    }

    /**
     * 根据页面请求数据
     */
    private void getData() {
        showLoadView();
        getPresenter().getShopComing(null,TYPE_REQUEST_COMMING);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ctlNavGood.setCurrentTab(position);
        dcCommingIndicator.setSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelect(int position) {
        vpChindContent.setCurrentItem(position);
    }

    @Override
    public void onTabReselect(int position) {

    }

    private void hideEmptyLayoutData(){
        setVisibility(vpChindContent,View.VISIBLE);
        setVisibility(ctlNavGood,View.VISIBLE);
        setVisibility(dcCommingIndicator,View.VISIBLE);
        hideEmptyLayout();
    }

    private void showLoadView(){
        showLoadingView();
        setVisibility(vpChindContent,View.GONE);
        setVisibility(ctlNavGood,View.GONE);
        setVisibility(dcCommingIndicator,View.GONE);
    }

    private void showNetErrorData() {
        showNetWorkError();
        setVisibility(vpChindContent,View.GONE);
        setVisibility(ctlNavGood,View.GONE);
        setVisibility(dcCommingIndicator,View.GONE);
    }

    private void showEmptyData() {
        showEmptyView(getString(R.string.text_fund_empty),R.mipmap.icon_empty);
        setVisibility(vpChindContent,View.GONE);
        setVisibility(ctlNavGood,View.GONE);
        setVisibility(dcCommingIndicator,View.GONE);
    }

    @Override
    public void onRefreshAgain() {
        super.onRefreshAgain();
        getData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateComming(ProUpdateEvent event) {
        getData();
    }
}
