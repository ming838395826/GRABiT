package com.android.spin.shop.fragment;


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
import com.android.spin.R;
import com.android.spin.event.AddCardEvent;
import com.android.spin.event.UpdateCardEvent;
import com.android.spin.home.entity.ProUpdateEvent;
import com.android.spin.home.fragment.HomeCardFragment;
import com.android.spin.shop.adapter.GoodsPagerAdapter;
import com.android.spin.shop.entity.ShopProductItemEntity;
import com.android.spin.shop.presenter.ShopPresenter;
import com.android.spin.view.gallery.IndicatorContainer;
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
public class ShopFragment extends MvpFragment<IView, ShopPresenter> implements
        OnTabSelectListener, ViewPager.OnPageChangeListener {

    @Bind(R.id.vp_chind_content)
    ViewPager vpChindContent;
    @Bind(R.id.ctl_nav_good)
    CommonTabLayout ctlNavGood;
    @Bind(R.id.dc_indicator)
    IndicatorContainer dcIndicator;

    private GoodsPagerAdapter mGoodsPagerAdapter;

    private List<ShopProductItemEntity> items;

    private final static int TYPE_REQUEST_CURRENT = 0x01;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    public ShopPresenter initPresenter() {
        return new ShopPresenter();
    }

    public ShopFragment() {
        // Required empty public constructor
    }

    public ShopProductItemEntity getShopProductItemEntity() {
        return items.get(ctlNavGood.getCurrentTab());
    }

    public static ShopFragment newInstance(String status) {
        ShopFragment fragment = new ShopFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("status", "0");
        fragment.setArguments(mBundle);
        return fragment;
    }

    @Override
    public void onFail(String code, int type) {
        if(type == TYPE_REQUEST_CURRENT){
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
            case TYPE_REQUEST_CURRENT:
                //普通
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
        return R.layout.fragment_shop;
    }

    @Override
    public void initView() {

        getData();

//
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
        if(vpChindContent == null){
            return;
        }
        vpChindContent.removeAllViews();
        vpChindContent.removeAllViewsInLayout();
        for (int i = 0; i < list.size(); i++) {
            mTabEntities.add(new HomeCardFragment.TabEntity(null, 0, 0));
            mFragments.add(GoodsFragment.newInstance(list.get(i), 0,list.size()));
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
            ctlNavGood.setCurrentTab(vpChindContent.getCurrentItem());
        }


        ctlNavGood.setTabData(mTabEntities);
        int itemWidth = DensityUtil.getWidth() / list.size() - DensityUtil.dp2px(5);
        ctlNavGood.setIndicatorWidth(DensityUtil.px2dp(itemWidth));
        ctlNavGood.setDividerColor(Color.parseColor("#ffffff"));
        ctlNavGood.setDividerWidth(10);
        ctlNavGood.setDividerPadding(10);
        ctlNavGood.setTabPadding(10);
        if(mTabEntities != null && mTabEntities.size() > 0){
            dcIndicator.setNum(mTabEntities.size());
            int itemWidth1 = (DensityUtil.getWidth() - DensityUtil.dp2px(1) * (mTabEntities.size() - 1)) / mTabEntities.size();
            dcIndicator.setIndicatorWidth(itemWidth1);
        }
    }

    /**
     * 根据页面请求数据
     */
    private void getData() {
        showLoadView();
        getPresenter().getShopCurrent(null, TYPE_REQUEST_CURRENT);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ctlNavGood.setCurrentTab(position);
        dcIndicator.setSelected(position);
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
        hideEmptyLayout();
    }

    private void showLoadView(){
        showLoadingView();
        setVisibility(vpChindContent,View.GONE);
    }

    private void showNetErrorData() {
        showNetWorkError();
        setVisibility(vpChindContent,View.GONE);
    }

    private void showEmptyData() {
        showEmptyView(getString(R.string.text_fund_empty),R.mipmap.icon_empty);
        setVisibility(vpChindContent,View.GONE);
    }

    @Override
    public void onRefreshAgain() {
        super.onRefreshAgain();
        getData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeBean(UpdateCardEvent event) {
//        for(){
//
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdate(ProUpdateEvent event) {
        getData();
    }
}
