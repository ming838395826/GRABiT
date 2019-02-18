package com.android.spin.logreg.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.base.base.BaseFragment;
import com.android.base.base.MvpFragment;
import com.android.base.mvp.view.IView;
import com.android.base.util.ToastUtil;
import com.android.spin.R;
import com.android.spin.callback.OnClickNextListener;
import com.android.spin.logreg.ForgetPwdActivity;
import com.android.spin.logreg.entity.RegisterQuestEntity;
import com.android.spin.logreg.presenter.RegisterPresenter;
import com.taobao.uikit.feature.view.TTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：yangqiyun
 * 时间：2017/7/20
 * 1120389276@qq.com
 * 描述：忘记密码第一步
 */
public class ForgetPwdThirdFragment extends MvpFragment<IView,RegisterPresenter> implements View.OnClickListener{

    @Bind(R.id.tet_pwd)
    EditText tetPwd;
    @Bind(R.id.tbtn_next)
    TTextView tbtnNext;

    @Override
    public RegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }

    public ForgetPwdThirdFragment() {
        // Required empty public constructor
    }

    public static ForgetPwdThirdFragment newInstance() {
        ForgetPwdThirdFragment fragment = new ForgetPwdThirdFragment();
        return fragment;
    }

    private RegisterQuestEntity getRegisterQuestEntity(){
        return ((ForgetPwdActivity) getActivity()).getViewDelegate().entity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_forget_pwd_third;
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
                tbtnNext.setEnabled(getTextLength(tetPwd) >=6 && getTextLength(tetPwd) <= 24 );
            }
        });
    }

    private void checkParams() {
        if(getTextLength(tetPwd) < 7){
            ToastUtil.shortShow(getString(R.string.text_pwd_error_length));
            return;
        }
        RegisterQuestEntity entity = getRegisterQuestEntity();
        if(entity == null){
            return;
        }
        Map<String,Object> params = new HashMap<>();
        params.put("phone",entity.getPhone());
        params.put("code",entity.getCode());
        params.put("password",getText(tetPwd));
        getPresenter().doResetPwd(params,0);
        showLoadDialog();
    }

    @OnClick(R.id.tbtn_next)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tbtn_next:
                //提交修改
                checkParams();
                break;
        }
    }

    public OnClickNextListener mListener;

    public void setonClickNextListener(OnClickNextListener mListener) {
        this.mListener = mListener;
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
            case 0:
                if(mListener == null){
                    return;
                }
                mListener.onClickNextByPwd(tbtnNext,getText(tetPwd));
                break;
        }
    }
}
