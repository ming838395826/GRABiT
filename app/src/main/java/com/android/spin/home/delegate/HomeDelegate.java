package com.android.spin.home.delegate;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.base.base.delegate.AppDelegate;
import com.android.spin.R;
import com.android.spin.db.UserManager;
import com.android.spin.home.entity.TabEntity;
import com.android.spin.logreg.LoginActivity;
import com.android.spin.logreg.RegisterActivity;
import com.android.spin.util.DialogUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 作者：yangqiyun
 * 时间：2017/7/20
 * 1120389276@qq.com
 * 描述：
 */

public class HomeDelegate extends AppDelegate {
    @Bind(R.id.ctl_nav)
    CommonTabLayout ctlNav;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initWidget() {

        setFragmentVisible(R.id.fg_home_found);

        initTab();

    }

    /**
     * 跳转到卡包
     */
    public void showCardfragment(){
        if(ctlNav != null){
            ctlNav.setCurrentTab(1);
            setFragmentVisible(R.id.fg_home_card);
        }
    }

    /**
     * 显示对应的fragment
     *
     * @param selectedFragmentId
     */
    private void setFragmentVisible(int selectedFragmentId) {
        FragmentTransaction fragmentTransaction = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.app_slide_left_in,R.anim.app_slide_right_out);
        int[] ids = {R.id.fg_home_found, R.id.fg_home_card, R.id.fg_home_mine};
        for (int id : ids) {
            Fragment fragment = ((AppCompatActivity) getActivity()).getSupportFragmentManager().findFragmentById(id);
            if (id == selectedFragmentId) {
                fragmentTransaction.show(fragment);
            } else {
                fragmentTransaction.hide(fragment);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
        if(R.id.fg_home_card==selectedFragmentId&&getActivity()!=null){
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


    private int[] mIconUnselectIds = {R.mipmap.grabita, R.mipmap.cardb, R.mipmap.personalb};
    private int[] mIconSelectIds = {R.mipmap.grabitb, R.mipmap.carda, R.mipmap.personala};

    /**
     * 初始化切换
     */
    private void initTab() {
        for (int i = 0; i < mIconUnselectIds.length; i++) {
            mTabEntities.add(new TabEntity(null, mIconSelectIds[i], mIconUnselectIds[i]));
        }

        ctlNav.setTabData(mTabEntities);
        ctlNav.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if(position == 0){
                    setFragmentVisible(R.id.fg_home_found);
                }else if(position == 1){
                    setFragmentVisible(R.id.fg_home_card);
                }else {
                    setFragmentVisible(R.id.fg_home_mine);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

}
