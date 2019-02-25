package com.ming.grabit.logreg.fragment;


import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.android.base.base.MvpFragment;
import com.android.base.mvp.view.IView;
import com.android.base.util.ToastUtil;
import com.ming.grabit.R;
import com.ming.grabit.callback.OnClickNextListener;
import com.ming.grabit.logreg.ForgetPwdActivity;
import com.ming.grabit.logreg.presenter.RegisterPresenter;
import com.taobao.uikit.feature.view.TTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 作者：yangqiyun
 * 时间：2017/7/20
 * 1120389276@qq.com
 * 描述：忘记密码第二步
 */
public class ForgetPwdSecondFragment extends MvpFragment<IView,RegisterPresenter> implements View.OnClickListener {


    @Bind(R.id.ttv_remind)
    TTextView ttvRemind;
    @Bind(R.id.et_sms_code)
    EditText etSmsCode;
    @Bind(R.id.tbtn_get_code_again)
    TTextView tbtnGetCodeAgain;
    @Bind(R.id.tbtn_next)
    TTextView tbtnNext;
    @Bind(R.id.ttv_s)
    TTextView ttvS;

    private CountDownTimer mCodeTimer;
    private static final int TYPE_SEND_SMS_CODE = 0x01;

    @Override
    public RegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }

    public ForgetPwdSecondFragment() {
        // Required empty public constructor
    }

    public static ForgetPwdSecondFragment newInstance() {
        ForgetPwdSecondFragment fragment = new ForgetPwdSecondFragment();
        return fragment;
    }

    private String getPhone() {
        try {
            return ((ForgetPwdActivity) getActivity()).getViewDelegate().entity.getPhone();
        } catch (Exception e) {
        }
        return "";
    }

    @Override
    public void onResume() {
        super.onResume();
        ttvRemind.setText(getString(R.string.text_sms_code_remind, getPhone()));
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_forget_pwd_second;
    }

    @Override
    public void initView() {
        etSmsCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tbtnNext.setEnabled(getTextLength(etSmsCode) == 6);
            }
        });
        getRegisterSms();
    }

    /**
     * 获取短信验证码
     */
    private void getRegisterSms(){
        Map<String,Object> params = new HashMap<>();
        params.put("phone",getPhone());
        setEnable(tbtnGetCodeAgain,false);
        getPresenter().sendRForgetPwdSms(params,TYPE_SEND_SMS_CODE);
        showLoadDialog();
    }

    @OnClick({R.id.tbtn_next, R.id.tbtn_get_code_again})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tbtn_next:
                //下一步
                checkParams();
                break;
            case R.id.tbtn_get_code_again:
                //重新获取验证码
                break;
        }
    }

    private void checkParams() {
        if (getTextLength(etSmsCode) != 6) {
            ToastUtil.shortShow(getString(R.string.text_phone_error_length));
            return;
        }
        if (mListener != null) {
            mListener.onClickNextByCode(tbtnNext, getText(etSmsCode));
        }
    }

    public OnClickNextListener mListener;

    public void setonClickNextListener(OnClickNextListener mListener) {
        this.mListener = mListener;
    }


    @Override
    public void onDestroy() {
        if (mCodeTimer != null) {
            mCodeTimer.cancel();
            mCodeTimer = null;
        }
        super.onDestroy();
    }

    private void star() {
        // 第一个参数是总时间， 第二个参数是间隔
        mCodeTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                setText(ttvS, millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                // 广播倒计时结束
                setText(ttvS, "");
                tbtnGetCodeAgain.setVisibility(View.VISIBLE);
                setEnable(tbtnGetCodeAgain, true);
            }
        };
        // 开始倒计时
        mCodeTimer.start();
    }

    @Override
    public void onFail(String code, int type) {

    }

    @Override
    public void onComplete(int type) {
        dismissLoadDialog();
    }

    @Override
    public void onSuccess(Object data, int type) {

        switch (type){
            case TYPE_SEND_SMS_CODE:
                //发送验证码
                star();
                break;
        }
    }
}
