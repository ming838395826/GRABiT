package com.ming.grabit.logreg.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.android.base.util.ToastUtil;
import com.ming.grabit.R;
import com.ming.grabit.logreg.RegisterActivity;
import com.taobao.uikit.feature.view.TTextView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 作者：yangqiyun
 * 时间：2017/7/20
 * 1120389276@qq.com
 * 描述：注册第二步
 */
public class RegisterStepFourthFragment extends BaseRegisterFragment implements View.OnClickListener {

    @Bind(R.id.tet_pwd)
    EditText tetPwd;
    @Bind(R.id.tbtn_next)
    TTextView tbtnNext;

    public RegisterStepFourthFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RegisterStepFourthFragment newInstance() {
        RegisterStepFourthFragment fragment = new RegisterStepFourthFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_register_step_fourth;
    }

    @Override
    public void initView() {

        tetPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tbtnNext.setEnabled(getTextLength(tetPwd) >= 6 && getTextLength(tetPwd) <=24 );
            }
        });

    }
    @OnClick({R.id.tbtn_next})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tbtn_next:
                //下一步
                checkParams();
                break;
        }
    }

    /**
     *
     */
    private void checkParams() {
        if (tetPwd.getText().toString().trim().length() < 6 || tetPwd.getText().toString().trim().length() > 24) {
            ToastUtil.shortShow(getString(R.string.text_pwd_error_length));
            return;
        }
        ((RegisterActivity) getActivity()).getViewDelegate()
                .getQuestEntity().setPassword(tetPwd.getText().toString().trim());
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onFail(String code, int type) {

    }

    @Override
    public void onComplete(int type) {

    }

    @Override
    public void onSuccess(Object data, int type) {

    }
}
