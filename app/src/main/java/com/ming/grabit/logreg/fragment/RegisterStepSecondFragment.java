package com.ming.grabit.logreg.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.android.base.util.RegExpValidatorUtils;
import com.android.base.util.ToastUtil;
import com.ming.grabit.R;
import com.ming.grabit.common.entity.GetPhoneResultEntity;
import com.ming.grabit.logreg.LoginActivity;
import com.ming.grabit.logreg.RegisterActivity;
import com.ming.grabit.logreg.presenter.RegisterPresenter;
import com.ming.grabit.util.DialogUtil;
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
public class RegisterStepSecondFragment extends BaseRegisterFragment implements View.OnClickListener{

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.tbtn_next)
    TTextView tbtnNext;

    private static final int TYPE_PHONE_STATUS_REQUSE_CODE = 0x00;

    public RegisterStepSecondFragment() {
        // Required empty public constructor
    }

    @Override
    public RegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }

    // TODO: Rename and change types and number of parameters
    public static RegisterStepSecondFragment newInstance() {
        RegisterStepSecondFragment fragment = new RegisterStepSecondFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_register_step_second;
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
                //检查是否已经注册
                doNext();
                break;
        }
    }
    private void doNext(){
        showLoadDialog();
        Map<String,Object> params = new HashMap<>();
        params.put("phone",etPhone.getText().toString().trim());
        getPresenter().getIsExistPhone(params,TYPE_PHONE_STATUS_REQUSE_CODE);
    }

    /**
     * 下一步
     */
    private void checkParams() {
        if (etPhone.getText().toString().trim().length() == 0 || etPhone.getText().toString().trim().length() > 20) {
            ToastUtil.shortShow(getString(R.string.text_phone_error_length));
            return;
        }
        if(!RegExpValidatorUtils.isTelephone(etPhone.getText().toString().trim())){
            ToastUtil.shortShow("电话号码格式不正确");
            return;
        }
    }


    /**
     * 提示手机已经被注册
     */
    private void showPhoneHasRegisted(){
        DialogUtil.getStandDialog(getActivity(), null, getString(R.string.dialog_phone_registed_hint)
                , getString(R.string.dialog_btn_cancel), getString(R.string.dialog_btn_login),
                true, new DialogUtil.OnClickListener() {
            @Override
            public void onClick(Dialog dialog, View view, int position) {
                switch (position){
                    case 1:
                        //去登录
                        LoginActivity.star(getActivity());
                        finishActivity();
                        break;
                }
            }
        }).show();
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
            case TYPE_PHONE_STATUS_REQUSE_CODE:
                //判断手机状态
                GetPhoneResultEntity status = (GetPhoneResultEntity) data;
                if(status.isIs_exist()){
                    showPhoneHasRegisted();
                    break;
                }
                ((RegisterActivity) getActivity()).getViewDelegate()
                        .getQuestEntity().setPhone(etPhone.getText().toString().trim());
                if (mListener != null) {
                    mListener.onFragmentInteraction();
                }
                break;
        }
    }
}
