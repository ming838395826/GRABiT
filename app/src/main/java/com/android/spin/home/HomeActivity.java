package com.android.spin.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.base.base.BaseUIActivity;
import com.android.base.util.ToastUtil;
import com.android.spin.home.delegate.HomeDelegate;
import com.android.spin.home.holder.HomeBackHolder;
import com.android.spin.util.facebook.FaceBookLogin;
import com.android.spin.util.facebook.FacebookUser;

import java.util.HashMap;
import java.util.Map;

@Route(path = "/app/HomeActivity")
public class HomeActivity extends BaseUIActivity<HomeDelegate> {


    private FaceBookLogin mFaceBookLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFaceBookLogin = new FaceBookLogin(HomeActivity.this);
    }

    public FaceBookLogin getFaceBookLogin(){
        return mFaceBookLogin;
    }

    public static void star(Context context){
        context.startActivity(new Intent(context,HomeActivity.class));
    }

    @Override
    public int getLayoutStyle() {
        return TITLEBARSTYLE_NOT;
    }

    @Override
    public void initView() {

        setSwipeBackEnable(false);

    }

    @Override
    public void initHeaderView() {

    }

    @Override
    protected Class<HomeDelegate> getDelegateClass() {
        return HomeDelegate.class;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            HomeBackHolder.exitBy2Click(this);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mFaceBookLogin == null){
            return;
        }
        //facebook回调
        mFaceBookLogin.getCallbackManager().onActivityResult(requestCode, resultCode, data);

    }
}
