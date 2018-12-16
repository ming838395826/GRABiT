package com.android.spin.logreg;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.android.base.base.BaseUIActivity;
import com.android.spin.R;
import com.android.spin.logreg.delegate.ForgetPwdDelegate;
import com.android.spin.logreg.entity.RegisterQuestEntity;

public class ForgetPwdActivity extends BaseUIActivity<ForgetPwdDelegate> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void star(Context mContext){
        mContext.startActivity(new Intent(mContext,ForgetPwdActivity.class));
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
        setHeaderTitle(getString(R.string.title_forget_pwd));
    }

    @Override
    protected Class<ForgetPwdDelegate> getDelegateClass() {
        return ForgetPwdDelegate.class;
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
