package com.android.spin.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.android.base.base.BaseUIActivity;
import com.android.spin.R;
import com.android.spin.logreg.fragment.BaseRegisterFragment;
import com.android.spin.mine.delegate.ChangePwdDelegate;

public class ChangePwdActivity extends BaseUIActivity<ChangePwdDelegate>
        implements BaseRegisterFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setHeaderTitle(getString(R.string.text_change_pwd_title));

    }

    @Override
    protected Class<ChangePwdDelegate> getDelegateClass() {
        return ChangePwdDelegate.class;
    }

    @Override
    public void onFragmentInteraction() {
//        getViewDelegate().doNext();
        hideSoftKeyboard(getRootView());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(viewDelegate != null){
//                viewDelegate.doBack();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
