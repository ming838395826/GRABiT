package com.android.spin;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.base.base.delegate.MvpDelegate;
import com.android.base.mvp.view.IView;
import com.android.base.util.AppLanguageManager;
import com.android.spin.base.SpinApplication;
import com.android.spin.db.UserManager;
import com.android.spin.home.HomeActivity;
import com.android.spin.logreg.LoginActivity;
import com.android.spin.logreg.RegisterActivity;
import com.android.spin.logreg.RegisterFacebookActivity;
import com.android.spin.logreg.presenter.RegisterPresenter;
import com.android.spin.util.facebook.FaceBookLogin;
import com.android.spin.util.facebook.FacebookUser;
import com.taobao.uikit.feature.view.TImageView;
import com.taobao.uikit.feature.view.TTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 作者：yangqiyun
 * 时间：2017/7/20
 * 1120389276@qq.com
 * 描述：主页
 */

public class MainDelegate extends MvpDelegate<IView, RegisterPresenter> implements View.OnClickListener {

    @Bind(R.id.tv_sample_login)
    TTextView tvSampleLogin;
    @Bind(R.id.tv_face_book_login)
    TTextView tvFaceBookLogin;
    @Bind(R.id.tv_create_account)
    TTextView tvCreateAccount;
    @Bind(R.id.rl_container)
    RelativeLayout rlContainer;

    FaceBookLogin faceBookLogin;
    @Bind(R.id.timg_back)
    TImageView mTimgBack;
    @Bind(R.id.tv_title)
    TTextView mTvTitle;

    private FacebookUser mFacebookUser;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(UserManager.getInstance().getToken()) && !SpinApplication.isFirstLoad) {
            showLoginView();
        } else {
            hideLoginView();
            if ("EN".equalsIgnoreCase(AppLanguageManager.getLanuage())) {
                mTvTitle.setText("Free Discounts at Your Finger Tips");
            }else {
                mTvTitle.setText("唾手可得的優惠");
            }
        }
    }

    @Override
    public void initWidget() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!SpinApplication.isFirstLoad || false && TextUtils.isEmpty(UserManager.getInstance().getToken())) {//无论怎么样都进主界面
                    return;
                }
                HomeActivity.star(getActivity());
                SpinApplication.isFirstLoad = false;

            }
        }, 1000);
    }

    /**
     * 显示登陆布局
     */
    private void showLoginView() {
        setVisibility(tvSampleLogin, View.VISIBLE);
        setVisibility(tvFaceBookLogin, View.VISIBLE);
        setVisibility(tvCreateAccount, View.VISIBLE);
        setVisibility(mTimgBack, View.VISIBLE);
    }

    /**
     * 显示登陆布局
     */
    private void hideLoginView() {
        setVisibility(tvSampleLogin, View.INVISIBLE);
        setVisibility(tvFaceBookLogin, View.INVISIBLE);
        setVisibility(tvCreateAccount, View.INVISIBLE);
        setVisibility(mTimgBack, View.INVISIBLE);
    }

    @OnClick({R.id.tv_sample_login, R.id.tv_face_book_login, R.id.tv_create_account, R.id.timg_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sample_login:
                //普通登陆
                LoginActivity.star(getActivity());
//                AppLanguageManager.setLanguageHk();
//                getActivity().recreate();
                break;
            case R.id.tv_face_book_login:
                //普通登陆
                initFaceBook();
//                AppLanguageManager.setLanguageHk();
//                getActivity().recreate();
                break;
            case R.id.tv_create_account:
                //普通登陆
                RegisterActivity.star(this.getActivity());
//                AppLanguageManager.setLanguageHk();
//                getActivity().recreate();
                break;
            case R.id.timg_back:
                //返回
                finish();
                break;
        }
    }

    private void initFaceBook() {

        //初始化Facebook登录服务
        faceBookLogin = new FaceBookLogin(getActivity());
        faceBookLogin.setFacebookListener(new FaceBookLogin.FacebookListener() {
            @Override
            public void facebookLoginSuccess(FacebookUser user) {
                mFacebookUser = user;
                Map<String, Object> params = new HashMap<>();
                params.put("access_token", user.getToken());
                getPrensenter().doFbLogin(params, 0);

                showLoadDialog();
            }

            @Override
            public void facebookLoginFail() {

            }
        });

        if (faceBookLogin != null) {
            faceBookLogin.login();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (faceBookLogin == null) {
            return;
        }
        //facebook回调
        faceBookLogin.getCallbackManager().onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onFail(String code, int type) {
        if ("1009".equals(code)) {
            //没注册，需要绑定
            RegisterFacebookActivity.star(getActivity(), mFacebookUser);
        }
    }

    @Override
    public void onComplete(int type) {
        dismissLoadDialog();
    }

    @Override
    public void onSuccess(Object data, int type) {
        switch (type) {
            case 0:
                HomeActivity.star(this.getActivity());
                break;
        }
    }

    @NonNull
    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }
}
