package com.android.spin.home.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.base.base.BaseFragment;
import com.android.base.base.BaseHeaderTitleBarView;
import com.android.base.event.TokenFailEvent;
import com.android.spin.MainActivity;
import com.android.spin.R;
import com.android.spin.card.adapter.CardListPagerAdapter;
import com.android.spin.card.fragment.CardListFragment;
import com.android.spin.db.UserManager;
import com.android.spin.draw.view.NoScrollViewPager;
import com.android.spin.event.AddCardEvent;
import com.android.spin.logreg.LoginActivity;
import com.android.spin.logreg.RegisterActivity;
import com.android.spin.util.DialogUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：yangqiyun
 * 时间：2017/7/21
 * 1120389276@qq.com
 * 描述： 卡包
 */
public class HomeCardFragment extends BaseFragment implements OnTabSelectListener, ViewPager.OnPageChangeListener {

    @Bind(R.id.tl_mine_card)
    CommonTabLayout tlMineCard;
    @Bind(R.id.vp_mine_card)
    NoScrollViewPager vpMineCard;
    @Bind(R.id.btb_top_bar)
    BaseHeaderTitleBarView btbTopBar;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = new String[3];

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private String[] mStatus = {"0", "1", "2"};

    public HomeCardFragment() {
        // Required empty public constructor
    }

    public static HomeCardFragment newInstance() {
        HomeCardFragment fragment = new HomeCardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_card;
    }

    private void initHeaderView(){
        btbTopBar.setHeaderTitle(getString(R.string.text_card_title));
        btbTopBar.setTitleColor(getResources().getColor(R.color.app_color_191917));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser&&getActivity()!=null){
            if(!UserManager.getInstance().isLogin()){
                DialogUtil.getLoginDialog(getActivity(), false, new DialogUtil.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view, int position) {
                        switch (position){
                            case 0:

                                break;
                            case 1://登陆
                                LoginActivity.star(getActivity());
                                break;
                            case 2://注册
                                RegisterActivity.star(getActivity());
                                break;
                        }
                    }
                }).show();
            }
        }
    }

    @Override
    public void initView() {
        vpMineCard.setScroll(false);

        initHeaderView();

        initPages();

        mTitles[0] = getString(R.string.text_card_tab_new);
        mTitles[1] = getString(R.string.text_card_tab_used);
        mTitles[2] = getString(R.string.text_card_tab_expired);

        for(int i = 0; i < mTitles.length; i++){
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }

        for (String status : mStatus) {
            CardListFragment mCardListFragment = CardListFragment.newInstance(status);
            mCardListFragment.setOnGetResultListener(new CardListFragment.OnGetResultListener() {
                @Override
                public void onGetResult(String status, int total) {
                    if(mStatus[0].equals(status)){
                        if(total == 0){
                            ((TabEntity)mTabEntities.get(0)).setTitle(getString(R.string.text_card_tab_new));
                        }else{
                            ((TabEntity)mTabEntities.get(0)).setTitle(getString(R.string.text_card_tab_new) + " (" + total + ")");
//                            mTitles[0] = getString(R.string.text_card_tab_new);
                        }
                    }else if(mStatus[1].equals(status)){
                        if(total == 0){
                            ((TabEntity)mTabEntities.get(1)).setTitle(getString(R.string.text_card_tab_used));
//                            mTitles[1] = getString(R.string.);
                        }else{
                            ((TabEntity)mTabEntities.get(1)).setTitle(getString(R.string.text_card_tab_used) + " (" + total + ")");
//                            mTitles[1] = getString(R.string.text_card_tab_used) + "(" + total + ")";
                        }
                    }else if(mStatus[2].equals(status)){
                        if(total == 0){
                            ((TabEntity)mTabEntities.get(2)).setTitle(getString(R.string.text_card_tab_expired));
//                            mTabEntities.get(2).setTitle(getString(R.string.text_card_tab_new));
//                            mTitles[2] = getString(R.string.text_card_tab_expired);
                        }else{
                            ((TabEntity)mTabEntities.get(2)).setTitle(getString(R.string.text_card_tab_expired) + " (" + total + ")");
//                            mTitles[2] = getString(R.string.text_card_tab_expired) + "(" + total + ")";
                        }
                    }
                    tlMineCard.notifyDataSetChanged();
                }
            });
            mFragments.add(mCardListFragment);
        }

        vpMineCard.setOffscreenPageLimit(3);
        vpMineCard.setAdapter(new CardListPagerAdapter(getChildFragmentManager(), mFragments, mTitles));
        tlMineCard.setOnTabSelectListener(this);

        vpMineCard.addOnPageChangeListener(this);

        tlMineCard.setTabData(mTabEntities);
//        tlMineCard.setViewPager(vpMineCard,mTitles);
        tlMineCard.setCurrentTab(0);

    }

    /**
     * 初始化数据
     */
    private void initPages() {


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tlMineCard.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelect(int position) {
        vpMineCard.setCurrentItem(position);
    }

    @Override
    public void onTabReselect(int position) {

    }

    public static class TabEntity implements CustomTabEntity{

        public String title;
        public int selectedIcon;
        public int unSelectedIcon;

        public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
            this.title = title;
            this.selectedIcon = selectedIcon;
            this.unSelectedIcon = unSelectedIcon;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return selectedIcon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getSelectedIcon() {
            return selectedIcon;
        }

        public void setSelectedIcon(int selectedIcon) {
            this.selectedIcon = selectedIcon;
        }

        public int getUnSelectedIcon() {
            return unSelectedIcon;
        }

        public void setUnSelectedIcon(int unSelectedIcon) {
            this.unSelectedIcon = unSelectedIcon;
        }

        @Override
        public int getTabUnselectedIcon() {
            return unSelectedIcon;
        }


    }
}
