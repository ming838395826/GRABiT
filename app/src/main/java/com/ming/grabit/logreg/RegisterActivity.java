package com.ming.grabit.logreg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.android.base.base.BaseUIActivity;
import com.ming.grabit.R;
import com.ming.grabit.logreg.delegate.RegisterDelegate;
import com.ming.grabit.logreg.fragment.BaseRegisterFragment;

/**
 * 作者：yangqiyun
 * 时间：2017/7/20
 * 1120389276@qq.com
 * 描述：註冊
 */
public class RegisterActivity extends BaseUIActivity<RegisterDelegate>
        implements BaseRegisterFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void star(Context context){
        context.startActivity(new Intent(context,RegisterActivity.class));
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
        getBaseHeaderTitleBarVie().setHeaderRightContent("1/6");
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
    protected Class<RegisterDelegate> getDelegateClass() {
        return RegisterDelegate.class;
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
