package com.ming.grabit.mine.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.android.base.util.ToastUtil;
import com.ming.grabit.R;
import com.ming.grabit.common.util.Constant;
import com.ming.grabit.db.UserManager;
import com.ming.grabit.mine.presenter.MinePresenter;
import com.taobao.uikit.feature.view.TTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 作者：yangqiyun
 * 时间：2017/7/20
 * 1120389276@qq.com
 * 描述：注册第二步
 */
public class ChangePhoneFirstFragment extends BaseChangePhoneFragment implements View.OnClickListener {

    @Bind(R.id.ttv_s)
    TTextView ttvS;
    @Bind(R.id.et_sms_code)
    EditText etSmsCode;
    @Bind(R.id.tbtn_get_code_again)
    TTextView tbtnGetCodeAgain;
    @Bind(R.id.tbtn_next)
    TTextView tbtnNext;

    private CountDownTimer mCodeTimer;

    private static final int TYPE_REGIST_REQUSE_CODE = 0x00;
    private static final int TYPE_SEND_SMS_CODE = 0x01;

    @Override
    public MinePresenter initPresenter() {
        return new MinePresenter();
    }

    public ChangePhoneFirstFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ChangePhoneFirstFragment newInstance() {
        ChangePhoneFirstFragment fragment = new ChangePhoneFirstFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChangePhoneSms();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_change_phone_first;
    }

    private String getPhone() {
        return UserManager.getInstance().getUser().getPhone();
    }

    @Override
    public void initView() {

        etSmsCode.setHint(String.format(getString(R.string.text_sms_code_remind), getPhone()));

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
                getChangePhoneSms();

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
    private void getChangePhoneSms() {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", UserManager.getInstance().getUser().getPhone());
        setEnable(tbtnGetCodeAgain, false);
        getPresenter().sendChangeSms(params, TYPE_SEND_SMS_CODE);
        showLoadDialog();
    }

    /**
     * 检查验证码
     */
    private void checkParams() {
        if (etSmsCode.getText().toString().trim().length() == 0 || etSmsCode.getText().toString().trim().length() > 20) {
            ToastUtil.shortShow(getString(R.string.text_sms_code_error_length));
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("code", etSmsCode.getText().toString().trim());

        showLoadDialog();
        getPresenter().doVerifyPhone(params, TYPE_REGIST_REQUSE_CODE);
    }

    @Override
    public void onFail(String code, int type) {
        if (Constant.WAIT_SEND_SMS_CODE.equals(code)) {
            ToastUtil.shortShow("消息已发送，请耐心等待");
        }
        if (type == TYPE_SEND_SMS_CODE) {
            setEnable(tbtnGetCodeAgain, true);
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
                //验证通过
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

}
