package com.android.spin.logreg;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

import com.android.base.base.BaseUIActivity;
import com.android.spin.R;
import com.android.spin.logreg.delegate.FacebookBindPhoneDelegate;
import com.android.spin.logreg.fragment.BaseRegisterFragment;
import com.android.spin.util.facebook.FacebookUser;

/**
 * 作者：yangqiyun
 * 时间：2017/10/11
 * 1120389276@qq.com
 * 描述：
 */

public class FacebookBindPhoneActivity extends BaseUIActivity<FacebookBindPhoneDelegate>
        implements BaseRegisterFragment.OnFragmentInteractionListener{

    public static void star(Context context, FacebookUser user){
        Intent intent = new Intent(context,FacebookBindPhoneActivity.class);
        intent.putExtra("user",user);
        context.startActivity(intent);
    }

    public FacebookUser getFacebookUser(){
        return (FacebookUser) getIntent().getSerializableExtra("user");
    }


    @Override
    public int getLayoutStyle() {
        return TITLEBARSTYLE_BELW;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initHeaderView() {
        setHeaderTitle(getString(R.string.text_create_account));
        getBaseHeaderTitleBarVie().setHeaderRightContent("1/4");
        setHeaderLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewDelegate != null){
                    viewDelegate.doBack();
                }
            }
        });
    }

    @Override
    protected Class<FacebookBindPhoneDelegate> getDelegateClass() {
        return FacebookBindPhoneDelegate.class;
    }

    @Override
    public void onFragmentInteraction() {
        getViewDelegate().doNext();
        hideSoftKeyboard(getRootView());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(viewDelegate != null){
                viewDelegate.doBack();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
