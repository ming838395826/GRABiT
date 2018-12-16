package com.android.spin.logreg;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.base.base.BaseUIActivity;
import com.android.spin.R;
import com.android.spin.logreg.delegate.RegisterFacebookDelegate;
import com.android.spin.util.facebook.FacebookUser;

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
