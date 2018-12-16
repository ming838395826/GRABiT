package com.android.spin.logreg.delegate;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.android.base.base.delegate.AppDelegate;
import com.android.spin.R;
import com.android.spin.db.UserManager;
import com.android.spin.home.HomeActivity;
import com.android.spin.logreg.FacebookBindPhoneActivity;
import com.android.spin.logreg.entity.RegisterQuestEntity;
import com.android.spin.logreg.fragment.FacebookRegisterStepFifthFragment;
import com.android.spin.logreg.fragment.FacebookRegisterStepFourthFragment;
import com.android.spin.logreg.fragment.FacebookRegisterStepSecondFragment;
import com.android.spin.logreg.fragment.FacebookRegisterStepThirdFragment;
import com.android.spin.mine.entity.UserEntity;
import com.android.spin.util.facebook.FacebookUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 作者：yangqiyun
 * 时间：2017/10/11
 * 1120389276@qq.com
 * 描述：
 */

public class FacebookBindPhoneDelegate extends AppDelegate {

    private FacebookRegisterStepSecondFragment mFacebookRegisterStepSecondFragment;
    private FacebookRegisterStepThirdFragment mFacebookRegisterStepThirdFragment;
    private FacebookRegisterStepFourthFragment mFacebookRegisterStepFourthFragment;
    private FacebookRegisterStepFifthFragment mFacebookRegisterStepFifthFragment;

    private UserEntity mQuestEntity = new UserEntity();

    private int position = 1;

    public UserEntity getQuestEntity() {
        return mQuestEntity;
    }

    public FacebookUser getFacebookUser(){
        return ((FacebookBindPhoneActivity)getActivity()).getFacebookUser();
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initWidget() {

        initStepFirst();

        initRegisterQuestEntity();
    }

    private void initRegisterQuestEntity(){
        FacebookUser user = getFacebookUser();
        if(user == null){
            return;
        }
        mQuestEntity.setName(user.getName());
        mQuestEntity.setAvatar(user.getAvatar());
        mQuestEntity.setGender(user.isMale()?1:2);
        mQuestEntity.setBirthday(user.getBirthday());
        mQuestEntity.setFb_openid(user.getId());
    }

    /**
     * 第一步
     */
    private void initStepFirst() {
        if (mFacebookRegisterStepSecondFragment == null) {
            mFacebookRegisterStepSecondFragment = FacebookRegisterStepSecondFragment.newInstance();
        }
        addFragment("1", R.id.layout_container, mFacebookRegisterStepSecondFragment);
    }

    /**
     * 第二步
     */
    private void initStepSecond() {
        if (mFacebookRegisterStepThirdFragment == null) {
            mFacebookRegisterStepThirdFragment = FacebookRegisterStepThirdFragment.newInstance();
        }
        addFragment("2", R.id.layout_container, mFacebookRegisterStepThirdFragment);
    }

    /**
     * 第三步
     */
    private void initStepThird() {
        if (mFacebookRegisterStepFourthFragment == null) {
            mFacebookRegisterStepFourthFragment = FacebookRegisterStepFourthFragment.newInstance();
        }
        addFragment("4", R.id.layout_container, mFacebookRegisterStepFourthFragment);
    }

    /**
     * 第四步
     */
    private void initStepFourth() {
        if (mFacebookRegisterStepFifthFragment == null) {
            mFacebookRegisterStepFifthFragment = FacebookRegisterStepFifthFragment.newInstance();
        }
        addFragment("3", R.id.layout_container, mFacebookRegisterStepFifthFragment);

    }

    private void hideFragment() {
        FragmentManager manager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (mFacebookRegisterStepSecondFragment != null && mFacebookRegisterStepSecondFragment.isAdded()) {
            transaction.hide(mFacebookRegisterStepSecondFragment);
        }
        if (mFacebookRegisterStepThirdFragment != null && mFacebookRegisterStepThirdFragment.isAdded()) {
            transaction.hide(mFacebookRegisterStepThirdFragment);
        }
        if (mFacebookRegisterStepFourthFragment != null && mFacebookRegisterStepFourthFragment.isAdded()) {
            transaction.hide(mFacebookRegisterStepFourthFragment);
        }
        if (mFacebookRegisterStepFifthFragment != null && mFacebookRegisterStepFifthFragment.isAdded()) {
            transaction.hide(mFacebookRegisterStepFifthFragment);
        }

        transaction.commitAllowingStateLoss();
    }

    /**
     * 下一步
     */
    public void doNext() {
        if (position != 4) {
            hideFragment();
        }
        switch (position) {
            case 1:
                initStepSecond();
                position = 2;
                break;
            case 2:
                initStepThird();
                position = 3;
                break;
            case 3:
                initStepFourth();
                position = 4;
                break;
            case 4:
                Intent intent = new Intent(this.getActivity(), HomeActivity.class);
                getActivity().startActivity(intent);
                position = 1;
                break;
        }
        getBaseHeaderTitleBarView().setHeaderRightContent(position + "/4");
    }

    /**
     * 上一步
     */
    public void doBack() {
        if (position == 1) {
            getActivity().finish();
        } else {
            position--;
            hideFragment();
            switch (position) {
                case 1:
                    initStepFirst();
                    mFacebookRegisterStepThirdFragment = null;
                    break;
                case 2:
                    initStepSecond();
                    mFacebookRegisterStepFourthFragment = null;
                    break;
                case 3:
                    if (!TextUtils.isEmpty(UserManager.getInstance().getToken())) {
                        HomeActivity.star(this.getActivity());
                    }
                    finish();
                    break;
            }
            getBaseHeaderTitleBarView().setHeaderRightContent(position + "/4");
        }
    }
}
