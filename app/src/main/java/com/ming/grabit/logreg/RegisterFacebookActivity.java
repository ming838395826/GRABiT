package com.ming.grabit.logreg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.base.base.BaseUIActivity;
import com.ming.grabit.R;
import com.ming.grabit.logreg.delegate.RegisterFacebookDelegate;
import com.ming.grabit.util.facebook.FacebookUser;

public class RegisterFacebookActivity extends BaseUIActivity<RegisterFacebookDelegate> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void star(Context mContext,FacebookUser user){
        Intent intent = new Intent(mContext,RegisterFacebookActivity.class);
        intent.putExtra("user",user);
        mContext.startActivity(intent);
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
        setHeaderTitle("GRABiT");
    }

    @Override
    protected Class<RegisterFacebookDelegate> getDelegateClass() {
        return RegisterFacebookDelegate.class;
    }
}
