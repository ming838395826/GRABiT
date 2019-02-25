package com.ming.grabit.mine.delegate;

import android.view.View;

import com.android.base.base.delegate.AppDelegate;
import com.ming.grabit.R;
import com.ming.grabit.db.UserManager;
import com.ming.grabit.mine.ChangePhoneActivity;
import com.ming.grabit.util.string.FormatStringUtil;
import com.taobao.uikit.feature.view.TTextView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 作者：yangqiyun
 * 时间：2017/10/1
 * 1120389276@qq.com
 * 描述：
 */

public class PhoneDelegate extends AppDelegate implements View.OnClickListener{

    @Bind(R.id.ttv_phone)
    TTextView ttvPhone;
    @Bind(R.id.ttv_phone_change)
    TTextView ttvPhoneChange;

    private String getPhone(){
        return getString(R.string.text_mine_mobile) + "    " + FormatStringUtil.getPhone(UserManager.getInstance().getUser().getPhone());
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_phone;
    }

    @Override
    public void initWidget() {

        ttvPhone.setText(getPhone());

    }
    @OnClick(R.id.ttv_phone_change)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ttv_phone_change:
                ChangePhoneActivity.star(this.getActivity(),UserManager.getInstance().getUser().getPhone());
                finish();
                break;
        }
    }
}
