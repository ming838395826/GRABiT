package com.ming.grabit.logreg.delegate;

import android.view.View;

import com.android.base.base.delegate.AppDelegate;
import com.ming.grabit.R;
import com.ming.grabit.logreg.FacebookBindPhoneActivity;
import com.ming.grabit.logreg.RegisterFacebookActivity;
import com.ming.grabit.util.facebook.FacebookUser;
import com.taobao.uikit.feature.view.TTextView;

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
