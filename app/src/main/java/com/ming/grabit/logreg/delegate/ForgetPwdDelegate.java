package com.ming.grabit.logreg.delegate;

import android.app.Dialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.base.base.delegate.AppDelegate;
import com.ming.grabit.R;
import com.ming.grabit.callback.OnClickNextListener;
import com.ming.grabit.logreg.LoginActivity;
import com.ming.grabit.logreg.entity.RegisterQuestEntity;
import com.ming.grabit.logreg.fragment.ForgetPwdFirstFragment;
import com.ming.grabit.logreg.fragment.ForgetPwdSecondFragment;
import com.ming.grabit.logreg.fragment.ForgetPwdThirdFragment;
import com.ming.grabit.util.DialogUtil;

/**
 * 作者：yangqiyun
 * 时间：2017/7/25
 * 1120389276@qq.com
 * 描述：忘记密码
 */

public class ForgetPwdDelegate extends AppDelegate implements
        OnClickNextListener {

    private ForgetPwdFirstFragment mForgetPwdFirstFragment;
    private ForgetPwdSecondFragment mForgetPwdSecondFragment;
    private ForgetPwdThirdFragment mForgetPwdThirdFragment;

    private int position = 0;

    public RegisterQuestEntity entity = new RegisterQuestEntity();

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    public void initWidget() {

        initStepFirst();
    }

    /**
     * 第一步
     */
    private void initStepFirst(){
        position = 0;
        if(mForgetPwdFirstFragment == null){
            mForgetPwdFirstFragment = ForgetPwdFirstFragment.newInstance();
            mForgetPwdFirstFragment.setonClickNextListener(this);
        }
        addFragment("1",R.id.layout_container,mForgetPwdFirstFragment);
    }

    /**
     * 第二步
     */
    private void initStepSecond(){
        if(mForgetPwdSecondFragment == null){
            mForgetPwdSecondFragment = ForgetPwdSecondFragment.newInstance();
            mForgetPwdSecondFragment.setonClickNextListener(this);
        }
        addFragment("2",R.id.layout_container,mForgetPwdSecondFragment);
        position = 1;
    }

    /**
     * 第三步
     */
    private void initStepThird(){
        if(mForgetPwdThirdFragment == null){
            mForgetPwdThirdFragment = ForgetPwdThirdFragment.newInstance();
            mForgetPwdThirdFragment.setonClickNextListener(this);
        }
        addFragment("3",R.id.layout_container,mForgetPwdThirdFragment);
        position = 2;
    }

    @Override
    public void onClickNext(View view, String phone) {
        //保存phone
        entity.setPhone(phone);
        hideFragment();
        initStepSecond();
    }

    @Override
    public void onClickNextByCode(View view, String code) {
        //保存验证码
        entity.setCode(code);
        hideFragment();
        initStepThird();
    }

    @Override
    public void onClickNextByPwd(View view, String pwd) {
        //保存密码
        //提示修改成功
        DialogUtil.getModifyPwdSuccessDialog(this.getActivity(), false, new DialogUtil.OnClickListener() {
            @Override
            public void onClick(Dialog dialog, View view, int position) {
                if(dialog != null){
                    dialog.dismiss();
                }
                LoginActivity.star(getActivity());
                finish();
            }
        }).show();
    }

    private void hideFragment(){
        FragmentManager manager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if(mForgetPwdFirstFragment != null && mForgetPwdFirstFragment.isAdded()){
            transaction.hide(mForgetPwdFirstFragment);
        }
        if(mForgetPwdSecondFragment != null && mForgetPwdSecondFragment.isAdded()){
            transaction.hide(mForgetPwdSecondFragment);
        }
        if(mForgetPwdThirdFragment != null && mForgetPwdThirdFragment.isAdded()){
            transaction.hide(mForgetPwdThirdFragment);
        }

        transaction.commitAllowingStateLoss();
    }

    /**
     * 上一步
     */
    public void doBack(){
        if(position == 0){
            finish();
        }else if(position == 1){
            initStepFirst();
        }else if(position == 2){
            initStepSecond();
        }
    }

}
