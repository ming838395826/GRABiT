package com.android.spin.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.android.base.base.BaseUIActivity;
import com.android.spin.R;
import com.android.spin.logreg.fragment.BaseRegisterFragment;
import com.android.spin.mine.delegate.ChangePhoneDelegate;
import com.android.spin.mine.delegate.ChangePwdDelegate;
import com.android.spin.mine.fragment.BaseChangePhoneFragment;

public class ChangePhoneActivity extends BaseUIActivity<ChangePhoneDelegate> implements BaseChangePhoneFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void star(Context context,String phone){

        Intent intent = new Intent(context,ChangePhoneActivity.class);
        intent.putExtra("phone",phone);
        context.startActivity(intent);
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
        setHeaderTitleAndColor(getString(R.string.text_change_pwd_title),getResources().getColor(R.color.app_font_color_main_title));
        setHeaderTitleViewBackgroundColor(R.color.color_white);
        setHeaderTitleLeftDrawable(R.mipmap.icon_gray_back);
    }

    @Override
    protected Class<ChangePhoneDelegate> getDelegateClass() {
        return ChangePhoneDelegate.class;
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
