package com.android.spin.logreg;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.base.base.BaseUIActivity;
import com.android.spin.R;
import com.android.spin.logreg.delegate.LoginDelegate;
/**
 * 作者：yangqiyun
 * 时间：2017/7/20
 * 1120389276@qq.com
 * 描述：登陸
 */
public class LoginActivity extends BaseUIActivity<LoginDelegate> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static void star(Context context){
        context.startActivity(new Intent(context,LoginActivity.class));
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
        setHeaderTitle(getString(R.string.text_login));
    }

    @Override
    protected Class<LoginDelegate> getDelegateClass() {
        return LoginDelegate.class;
    }
}
