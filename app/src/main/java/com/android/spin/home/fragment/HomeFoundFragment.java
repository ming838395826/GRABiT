package com.android.spin.home.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.base.base.BaseFragment;
import com.android.base.base.BaseHeaderTitleBarView;
import com.android.base.base.MvpFragment;
import com.android.base.mvp.view.IView;
import com.android.spin.R;
import com.android.spin.base.SpinApplication;
import com.android.spin.card.adapter.CardListPagerAdapter;
import com.android.spin.common.CommonShareActivity;
import com.android.spin.common.CommonWebActivity;
import com.android.spin.home.entity.NoticeResult;
import com.android.spin.home.entity.ProUpdateEvent;
import com.android.spin.shop.HistoryFundActivity;
import com.android.spin.shop.HistoryFundNewActivity;
import com.android.spin.shop.adapter.GoodsPagerAdapter;
import com.android.spin.shop.entity.ShopProductItemEntity;
import com.android.spin.shop.fragment.GoodsFragment;
import com.android.spin.shop.fragment.ShopCommingFragment;
import com.android.spin.shop.fragment.ShopFragment;
import com.android.spin.shop.fragment.ShopListNewFragment;
import com.android.spin.shop.presenter.ShopPresenter;
import com.android.spin.util.DialogUtil;
import com.android.spin.util.image.BlurBuilder;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 作者：yangqiyun
 * 时间：2017/7/21
 * 1120389276@qq.com
 * 描述：发现好物
 */
public class HomeFoundFragment extends MvpFragment<IView,ShopPresenter> implements
        View.OnClickListener,OnTabSelectListener{

    @Bind(R.id.btb_top_bar)
    BaseHeaderTitleBarView btbTopBar;
    @Bind(R.id.ctl_nav_fund)
    CommonTabLayout ctlNav;

    private boolean isloading = false;
//    @Bind(R.id.vp_content)
//    ViewPager vpContent;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    public HomeFoundFragment() {
        // Required empty public constructor
    }

    public static HomeFoundFragment newInstance() {
        HomeFoundFragment fragment = new HomeFoundFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(SpinApplication.isNeedLoad){
            getPresenter().getConfigsNotice(null,0);
//            showLoadDialog();
        }
    }

    @Override
    public ShopPresenter initPresenter() {
        return new ShopPresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_found;
    }

    private void initHeaderView(){

        btbTopBar.setHeaderTitleImage(R.mipmap.icon_grabit_title);

//        btbTopBar.setHeaderTitle("GRABiT");
//        btbTopBar.setTitleColor(getResources().getColor(R.color.app_font_color_main_title));
        btbTopBar.setHeaderLeftImage(R.mipmap.record);
        btbTopBar.setHeaderRightImage(R.mipmap.share);
        btbTopBar.setOnClickListener(this,R.id.tv_top_bar_left,R.id.tv_top_bar_right);
    }

    @Override
    public void initView() {

        initHeaderView();

        initPages();

//        tlMineCard.setViewPager(vpMineCard,mTitles);
        ctlNav.setCurrentTab(0);
        setFragmentVisible(R.id.fg_goos_current);

    }

    /**
     * 初始化数据
     */
    private void initPages() {

//        mFragments.add(ShopFragment.newInstance("0"));
//        mFragments.add(ShopFragment.newInstance("1"));
//
//        vpContent.setOffscreenPageLimit(2);
//        vpContent.setAdapter(new GoodsPagerAdapter(getChildFragmentManager(), mFragments));
        ctlNav.setOnTabSelectListener(this);

//        vpContent.addOnPageChangeListener(this);

        String[] mTitles = {getString(R.string.text_home_grabit),getString(R.string.text_home_upcoming)};
        for(int i = 0; i < mTitles.length; i++){
            mTabEntities.add(new HomeCardFragment.TabEntity(mTitles[i], 0, 0));
        }
        ctlNav.setTabData(mTabEntities);
//        for (String status : mStatus) {
//            mFragments.add(CardListFragment.newInstance(status));
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_top_bar_left:
                //历史
                startActivity(new Intent(getActivity(), HistoryFundNewActivity.class));
                break;
            case R.id.tv_top_bar_right:
                //分享
                if(isloading){
                    return;
                }
                isloading = true;
                showLoadDialog();
                try {
                    ShopProductItemEntity entity;
                    int position = ctlNav.getCurrentTab();
                    if(position == 0){
                        ShopListNewFragment fragment = (ShopListNewFragment) getChildFragmentManager().findFragmentById(R.id.fg_goos_current);

                        entity = fragment.getShareItem();
                    }else{
                        ShopListNewFragment fragment = (ShopListNewFragment) getChildFragmentManager().findFragmentById(R.id.fg_goos_comming);
                        entity = fragment.getShareItem();
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isloading = false;
                        }
                    },2000);
                    BlurBuilder.snapShotWithoutStatusBar(getActivity());
                    CommonShareActivity.star(getActivity(),entity);
                    dismissLoadDialog();
                }catch (Exception e){
                    Log.e("分享失败",Log.getStackTraceString(e));
                    dismissLoadDialog();
                }

                break;
        }
    }

    /**
     * 显示对应的fragment
     *
     * @param selectedFragmentId
     */
    private void setFragmentVisible(int selectedFragmentId) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.app_slide_left_in,R.anim.app_slide_right_out);
        int[] ids = {R.id.fg_goos_current, R.id.fg_goos_comming};
        for (int id : ids) {
            Fragment fragment = getChildFragmentManager().findFragmentById(id);
            if (id == selectedFragmentId) {
                fragmentTransaction.show(fragment);
            } else {
                fragmentTransaction.hide(fragment);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onTabSelect(int position) {
//        vpContent.setCurrentItem(position);
        if(position == 0){
            setFragmentVisible(R.id.fg_goos_current);
        }else{
            setFragmentVisible(R.id.fg_goos_comming);
        }
    }

    @Override
    public void onTabReselect(int position) {

    }

    @Override
    public void onFail(String code, int type) {
        if(type == 0 && "".equals(code)){
            SpinApplication.isNeedLoad = false;
        }
    }

    @Override
    public void onComplete(int type) {
        dismissLoadDialog();
    }

    @Override
    public void onSuccess(Object data, int type) {
        switch (type){
            case 0:
                SpinApplication.isNeedLoad = false;
                final NoticeResult result = (NoticeResult) data;
                if(result == null || result.getStatus() == 1){
                    return;
                }
                if(getActivity() == null){
                    return;
                }
                DialogUtil.getHomeNotice(getActivity(), result, new DialogUtil.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view, int position) {
                        switch (position){
                            case 0:
                                if(dialog != null && dialog.isShowing()){
                                    dialog.dismiss();
                                }
                                break;
                            case 1:
                                if(result.getValue() != null && !TextUtils.isEmpty(result.getValue().getUrl())){
                                    CommonWebActivity.star(getContext(),result.getValue().getUrl());
                                }else{
                                    if(dialog != null && dialog.isShowing()){
                                        dialog.dismiss();
                                    }
                                }
                                break;
                        }
                    }
                }).show();
                break;
        }
    }

}
