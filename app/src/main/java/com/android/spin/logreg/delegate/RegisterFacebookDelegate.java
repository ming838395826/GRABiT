package com.android.spin.logreg.delegate;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.android.base.base.delegate.AppDelegate;
import com.android.spin.R;
import com.android.spin.logreg.FacebookBindPhoneActivity;
import com.android.spin.logreg.RegisterFacebookActivity;
import com.android.spin.util.facebook.FaceBookLogin;
import com.android.spin.util.facebook.FacebookUser;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.taobao.uikit.feature.view.TTextView;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 作者：yangqiyun
 * 时间：2017/10/11
 * 1120389276@qq.com
 * 描述：
 */

public class RegisterFacebookDelegate extends AppDelegate implements View.OnClickListener {

    @Bind(R.id.ttv_mine_name)
    TTextView ttvMineName;
    @Bind(R.id.tbtn_bind_phone)
    TTextView tbtnBindPhone;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_register_facebook;
    }

    private FacebookUser getFacebookUser() {
        return ((RegisterFacebookActivity) getActivity()).getFacebookUser();
    }

    @Override
    public void initWidget() {

        ttvMineName.setText(getFacebookUser().getName());
    }

    @OnClick(R.id.tbtn_bind_phone)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tbtn_bind_phone:
                //绑定手机号码
                FacebookBindPhoneActivity.star(this.getActivity(),getFacebookUser());
                break;
        }
    }
}
