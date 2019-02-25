package com.ming.grabit.logreg.delegate;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.android.base.base.delegate.AppDelegate;
import com.ming.grabit.R;
import com.ming.grabit.db.UserManager;
import com.ming.grabit.home.HomeActivity;
import com.ming.grabit.logreg.entity.RegisterQuestEntity;
import com.ming.grabit.logreg.fragment.RegisterStepFifthFragment;
import com.ming.grabit.logreg.fragment.RegisterStepFirstFragment;
import com.ming.grabit.logreg.fragment.RegisterStepFourthFragment;
import com.ming.grabit.logreg.fragment.RegisterStepSecondFragment;
import com.ming.grabit.logreg.fragment.RegisterStepSixthFragment;
import com.ming.grabit.logreg.fragment.RegisterStepThirdFragment;

/**
 * 作者：yangqiyun
 * 时间：2017/7/21
 * 1120389276@qq.com
 * 描述：
 */

public class RegisterDelegate extends AppDelegate{

    private RegisterStepFirstFragment mFirstFragment;
    private RegisterStepSecondFragment mSecondFragment;
    private RegisterStepThirdFragment mThirdFragment;
    private RegisterStepFourthFragment mFourthFragment;
    private RegisterStepFifthFragment mFifthFragment;
    private RegisterStepSixthFragment mSixthFragment;

    private RegisterQuestEntity mQuestEntity = new RegisterQuestEntity();

    private int position = 1;

    public RegisterQuestEntity getQuestEntity(){
        return mQuestEntity;
    }
    @Override
    public int getRootLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initWidget() {

        initStepFirst();
    }

    /**
     * 第一步
     */
    private void initStepFirst(){
        if(mFirstFragment == null){
            mFirstFragment = RegisterStepFirstFragment.newInstance();
        }
        addFragment("1",R.id.layout_container,mFirstFragment);
    }

    /**
     * 第二步
     */
    private void initStepSecond(){
        if(mSecondFragment == null){
            mSecondFragment = RegisterStepSecondFragment.newInstance();
        }
        addFragment("2",R.id.layout_container,mSecondFragment);
    }

    /**
     * 第三步
     */
    private void initStepFourth(){
        if(mThirdFragment == null){
            mThirdFragment = RegisterStepThirdFragment.newInstance();
        }
        addFragment("4",R.id.layout_container,mThirdFragment);
    }

    /**
     * 第四步
     */
    private void initStepThird(){
        if(mFourthFragment == null){
            mFourthFragment = RegisterStepFourthFragment.newInstance();
        }
        addFragment("3",R.id.layout_container,mFourthFragment);
    }

    /**
     * 第五步
     */
    private void initStepFifth(){
        if(mFifthFragment == null){
            mFifthFragment = RegisterStepFifthFragment.newInstance();
        }
        addFragment("5",R.id.layout_container,mFifthFragment);
    }

    /**
     * 第六步
     */
    private void initStepSixth(){
        if(mSixthFragment == null){
            mSixthFragment = RegisterStepSixthFragment.newInstance();
        }
        addFragment("6",R.id.layout_container,mSixthFragment);
    }

    private void hideFragment(){
        FragmentManager manager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if(mSixthFragment != null && mSixthFragment.isAdded()){
            transaction.hide(mSixthFragment);
        }
        if(mFifthFragment != null && mFifthFragment.isAdded()){
            transaction.hide(mFifthFragment);
        }
        if(mFourthFragment != null && mFourthFragment.isAdded()){
            transaction.hide(mFourthFragment);
        }
        if(mThirdFragment != null && mThirdFragment.isAdded()){
            transaction.hide(mThirdFragment);
        }
        if(mSecondFragment != null && mSecondFragment.isAdded()){
            transaction.hide(mSecondFragment);
        }
        if(mFirstFragment != null && mFirstFragment.isAdded()){
            transaction.hide(mFirstFragment);
        }

        transaction.commitAllowingStateLoss();
    }

    /**
     * 下一步
     */
    public void doNext(){
        if(position != 6){
            hideFragment();
        }
        switch (position){
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
                initStepFifth();
                position = 5;
                break;
            case 5:
                initStepSixth();
                position = 6;
                break;
            case 6:
                Intent intent = new Intent(this.getActivity(), HomeActivity.class);
                getActivity().startActivity(intent);
                position = 1;
                break;
        }
        getBaseHeaderTitleBarView().setHeaderRightContent(position +"/6");
    }

    /**
     * 上一步
     */
    public void doBack(){
        if(position == 1){
            getActivity().finish();
        }else{
            position--;
            hideFragment();
            switch (position){
                case 1:
                    initStepFirst();
                    break;
                case 2:
                    initStepSecond();
                    break;
                case 3:
                    initStepThird();
                    break;
                case 4:
                    if(!TextUtils.isEmpty(UserManager.getInstance().getToken())){
                        HomeActivity.star(this.getActivity());
                    }
                    finish();
//                    initStepFourth();
                    break;
                case 5:
                    initStepFifth();
                    break;
            }
            getBaseHeaderTitleBarView().setHeaderRightContent(position +"/6");
        }
    }
}
