package com.android.spin.logreg.delegate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.base.base.delegate.MvpDelegate;
import com.android.base.mvp.view.IView;
import com.android.base.util.ToastUtil;
import com.android.spin.R;
import com.android.spin.base.SpinApplication;
import com.android.spin.home.HomeActivity;
import com.android.spin.logreg.ForgetPwdActivity;
import com.android.spin.logreg.presenter.RegisterPresenter;
import com.android.spin.util.ErrorToastUtli;
import com.orhanobut.logger.Logger;
import com.taobao.uikit.feature.view.TTextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 作者：yangqiyun
 * 时间：2017/7/21
 * 1120389276@qq.com
 * 描述：登陆
 */

public class LoginDelegate extends MvpDelegate<IView, RegisterPresenter> implements View.OnClickListener ,TextWatcher{

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.tbtn_forget_pwd)
    TTextView tbtnForgetPwd;
    @Bind(R.id.tbtn_login)
    TTextView tbtnLogin;

    private static final int TYPE_LOGIN_REQUEST_CODE = 0x00;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initWidget() {

        etPhone.addTextChangedListener(this);
        etPwd.addTextChangedListener(this);
    }

    @OnClick({R.id.tbtn_login, R.id.tbtn_forget_pwd})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tbtn_forget_pwd:
                //忘记密码
                ForgetPwdActivity.star(this.getActivity());
                break;
            case R.id.tbtn_login:
                //登陆
                doLogin();
                break;
        }
    }

    @NonNull
    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    private boolean chackParams() {

        if (getTextLength(etPhone) < 1) {
            ToastUtil.shortShow(getString(R.string.text_phone_error_length));
            return false;
        }
        if(getTextLength(etPwd) < 6 || getTextLength(etPwd) > 24){
            ToastUtil.shortShow(getString(R.string.text_pwd_error_length));
            return false;
        }
        return true;
    }

    /**
     * 登陆
     */
    private void doLogin() {

        if (!chackParams()) {
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("phone", getText(etPhone));
        params.put("password", getText(etPwd));
        showLoadDialog();
        getPrensenter().doLogin(params, TYPE_LOGIN_REQUEST_CODE);
    }

    @Override
    public void onFail(String code, int type) {
        ErrorToastUtli.showErrorToast(code);
    }

    @Override
    public void onComplete(int type) {
        dismissLoadDialog();
    }

    @Override
    public void onSuccess(Object data, int type) {
        switch (type) {
            case TYPE_LOGIN_REQUEST_CODE:
                //登陆成功
                setEnable(tbtnLogin,false);
                HomeActivity.star(this.getActivity());
                setAlis();
                finish();
                break;
        }
    }

    private void setAlis(){
        JPushInterface.setAliasAndTags(SpinApplication.getContext(),
                getText(etPhone),
                null,
                new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                        Logger.e(i + " " + s);
                    }
                });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        tbtnLogin.setEnabled(getTextLength(etPhone) > 7 && getTextLength(etPwd) > 5 && getTextLength(etPwd) < 21);
    }
}
