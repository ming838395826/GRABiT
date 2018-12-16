package com.android.spin.mine.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.base.util.ToastUtil;
import com.android.spin.R;
import com.android.spin.event.UpdateUserInfoEvent;
import com.android.spin.mine.ChangePhoneActivity;
import com.android.spin.mine.presenter.MinePresenter;
import com.taobao.uikit.feature.view.TTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：yangqiyun
 * 时间：2017/7/20
 * 1120389276@qq.com
 * 描述：注册第二步
 */
public class ChangePhoneThirdFragment extends BaseChangePhoneFragment implements View.OnClickListener {

    @Bind(R.id.et_sms_code)
    EditText etSmsCode;
    @Bind(R.id.tbtn_get_code_again)
    TTextView tbtnGetCodeAgain;
    @Bind(R.id.tbtn_next)
    TTextView tbtnNext;

    private static final int TYPE_PHONE_STATUS_REQUSE_CODE = 0x00;
    private static final int TYPE_SEND_SMS_CODE = 0x01;
    @Bind(R.id.ttv_s)
    TTextView ttvS;

    private CountDownTimer mCodeTimer;

    public ChangePhoneThirdFragment() {
        // Required empty public constructor
    }

    @Override
    public MinePresenter initPresenter() {
        return new MinePresenter();
    }

    // TODO: Rename and change types and number of parameters
    public static ChangePhoneThirdFragment newInstance() {
        ChangePhoneThirdFragment fragment = new ChangePhoneThirdFragment();
        return fragment;
    }

    private String getPhone() {
        return ((ChangePhoneActivity) getActivity()).getViewDelegate().getQuestEntity().getPhone();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChangePhoneSms();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_change_phone_third;
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
    }

    @OnClick({R.id.tbtn_get_code_again, R.id.tbtn_next})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tbtn_next:
                //下一步
                checkParams();
                break;
            case R.id.tbtn_get_code_again:
                //重新获取验证码
                getChangePhoneSms();
                break;
        }
    }

    /**
     * 获取短信验证码
     */
    private void getChangePhoneSms() {
        Map<String, Object> params = new HashMap<>();
        setEnable(tbtnGetCodeAgain, false);
        params.put("phone", getPhone());
        getPresenter().sendChangeSms(params, TYPE_SEND_SMS_CODE);
        showLoadDialog();
    }

    private void doNext() {
        showLoadDialog();
        Map<String, Object> params = new HashMap<>();
        params.put("phone", getPhone());
        params.put("code", getText(etSmsCode));
        getPresenter().doChangePhone(params, getPhone(), TYPE_PHONE_STATUS_REQUSE_CODE);
    }

    /**
     * 下一步
     */
    private void checkParams() {
        if (getTextLength(etSmsCode) != 6) {
            ToastUtil.shortShow(getString(R.string.text_phone_error_length));
            return;
        }
        doNext();
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

        switch (type) {
            case TYPE_PHONE_STATUS_REQUSE_CODE:
                EventBus.getDefault().post(new UpdateUserInfoEvent());
                finishActivity();
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
