package com.android.spin.logreg.fragment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.base.base.BaseFragment;
import com.android.base.util.ToastUtil;
import com.android.spin.R;
import com.android.spin.callback.OnClickNextListener;
import com.taobao.uikit.feature.view.TTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：yangqiyun
 * 时间：2017/7/20
 * 1120389276@qq.com
 * 描述：忘记密码第一步
 */
public class ForgetPwdFirstFragment extends BaseFragment implements View.OnClickListener {


    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.tbtn_next)
    TTextView tbtnNext;
    @Bind(R.id.ttv_sms_hint)
    TTextView ttvSmsHint;


    public ForgetPwdFirstFragment() {
        // Required empty public constructor
    }

    public static ForgetPwdFirstFragment newInstance() {
        ForgetPwdFirstFragment fragment = new ForgetPwdFirstFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_forget_pwd_first;
    }

    @Override
    public void initView() {
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tbtnNext.setEnabled(getTextLength(etPhone) > 7);
            }
        });
    }

    @OnClick({R.id.tbtn_next})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tbtn_next:
                //下一步
                checkParams();
                break;
        }
    }

    /**
     * 检查参数及提示
     */
    private void checkParams() {
        if (getTextLength(etPhone) > 7) {
            if (mListener != null) {
                mListener.onClickNext(tbtnNext, getText(etPhone));
            }
        } else {
            ToastUtil.shortShow(getString(R.string.text_phone_error_length));
        }
    }

    public OnClickNextListener mListener;

    public void setonClickNextListener(OnClickNextListener mListener) {
        this.mListener = mListener;
    }

}
