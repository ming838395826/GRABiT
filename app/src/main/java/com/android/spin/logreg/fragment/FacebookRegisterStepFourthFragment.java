package com.android.spin.logreg.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.android.base.mvp.view.IView;
import com.android.base.util.ToastUtil;
import com.android.spin.R;
import com.android.spin.base.SpinApplication;
import com.android.spin.common.util.Constant;
import com.android.spin.logreg.FacebookBindPhoneActivity;
import com.android.spin.logreg.RegisterActivity;
import com.android.spin.logreg.entity.RegisterQuestEntity;
import com.android.spin.logreg.presenter.RegisterPresenter;
import com.android.spin.mine.entity.UserEntity;
import com.taobao.uikit.feature.view.TTextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 作者：yangqiyun
 * 时间：2017/7/20
 * 1120389276@qq.com
 * 描述：注册第三步
 */
public class FacebookRegisterStepFourthFragment extends BaseRegisterFragment
        implements View.OnClickListener, IView {

    @Bind(R.id.et_sms_code)
    EditText etSmsCode;
    @Bind(R.id.tbtn_get_code_again)
    TTextView tbtnGetCodeAgain;
    @Bind(R.id.tbtn_next)
    TTextView tbtnNext;
    @Bind(R.id.ttv_remind)
    TTextView ttvRemind;

    private static final int TYPE_REGIST_REQUSE_CODE = 0x00;
    private static final int TYPE_SEND_SMS_CODE = 0x01;
    @Bind(R.id.ttv_s)
    TTextView ttvS;

    private CountDownTimer mCodeTimer;

    @Override
    public RegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }

    public FacebookRegisterStepFourthFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FacebookRegisterStepFourthFragment newInstance() {
        FacebookRegisterStepFourthFragment fragment = new FacebookRegisterStepFourthFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getRegisterSms();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_register_step_third;
    }

    private String getPhone() {
        return ((FacebookBindPhoneActivity) getActivity()).getViewDelegate().getQuestEntity().getPhone();
    }

    @Override
    public void initView() {

        ttvRemind.setText(String.format(getString(R.string.text_sms_code_remind), getPhone()));
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
    }

    @OnClick({R.id.tbtn_get_code_again, R.id.tbtn_next})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tbtn_get_code_again:
                //重新获取验证码
                getRegisterSms();

                break;
            case R.id.tbtn_next:
                //下一步
                checkParams();
                break;
        }
    }

    /**
     * 获取短信验证码
     */
    private void getRegisterSms(){
        Map<String,Object> params = new HashMap<>();
        params.put("phone",getPhone());
        setEnable(tbtnGetCodeAgain,false);
        getPresenter().sendRegisterSms(params,TYPE_SEND_SMS_CODE);
        showLoadDialog();
    }

    public void update(){
        getRegisterSms();
        ttvRemind.setText(String.format(getString(R.string.text_sms_code_remind), getPhone()));
    }

    /**
     * 注册
     */
    private void checkParams() {
        if (etSmsCode.getText().toString().trim().length() == 0 || etSmsCode.getText().toString().trim().length() > 20) {
            ToastUtil.shortShow(getString(R.string.text_sms_code_error_length));
            return;
        }
        UserEntity entity = ((FacebookBindPhoneActivity) getActivity()).getViewDelegate()
                .getQuestEntity();
        entity.setCode(etSmsCode.getText().toString().trim());

        Map<String, Object> params = new HashMap<>();
        params.put("password", entity.getPassword());
        params.put("phone", entity.getPhone());
        params.put("name", entity.getName().replace(" ",""));
        params.put("code", entity.getCode());
        params.put("fb_openid",entity.getFb_openid());
        params.put("avatar", entity.getAvatar() + "");

        showLoadDialog();
        getPresenter().doRegister(params, TYPE_REGIST_REQUSE_CODE);
    }

    @Override
    public void onFail(String code, int type) {
        if(Constant.WAIT_SEND_SMS_CODE.equals(code)){
            ToastUtil.shortShow("消息已发送，请耐心等待");
        }
        if(type == TYPE_SEND_SMS_CODE){
            setEnable(tbtnGetCodeAgain,true);
        }
    }

    @Override
    public void onComplete(int type) {
        dismissLoadDialog();
    }

    @Override
    public void onSuccess(Object data, int type) {
        switch (type) {
            case TYPE_REGIST_REQUSE_CODE:
                //注册成功
                setAlis();
                if (mListener != null) {
                    mListener.onFragmentInteraction();
                }
                break;
            case TYPE_SEND_SMS_CODE:
                //发送验证码
                star();
                break;
        }
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

    private void setAlis(){
        JPushInterface.setAliasAndTags(SpinApplication.getContext(),
                getPhone(),
                null,
                new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {

                    }
                });
    }

}
