package com.ming.grabit.mine.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.android.base.util.ToastUtil;
import com.ming.grabit.R;
import com.ming.grabit.common.entity.GetPhoneResultEntity;
import com.ming.grabit.logreg.LoginActivity;
import com.ming.grabit.mine.ChangePhoneActivity;
import com.ming.grabit.mine.presenter.MinePresenter;
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
public class ChangePhoneSecondFragment extends BaseChangePhoneFragment implements View.OnClickListener{

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.tbtn_next)
    TTextView tbtnNext;

    private static final int TYPE_PHONE_STATUS_REQUSE_CODE = 0x00;

    public ChangePhoneSecondFragment() {
        // Required empty public constructor
    }

    @Override
    public MinePresenter initPresenter() {
        return new MinePresenter();
    }

    // TODO: Rename and change types and number of parameters
    public static ChangePhoneSecondFragment newInstance() {
        ChangePhoneSecondFragment fragment = new ChangePhoneSecondFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_change_phone_second;
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
                doNext();
                break;
        }
    }

    private void doNext(){

        if (etPhone.getText().toString().trim().length() < 7 || etPhone.getText().toString().trim().length() > 11) {
            ToastUtil.shortShow(getString(R.string.text_phone_error_length));
            return;
        }

        showLoadDialog();
        Map<String,Object> params = new HashMap<>();
        params.put("phone",etPhone.getText().toString().trim());
        getPresenter().getIsExistPhone(params,TYPE_PHONE_STATUS_REQUSE_CODE);
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
                ((ChangePhoneActivity) getActivity()).getViewDelegate()
                        .getQuestEntity().setPhone(etPhone.getText().toString().trim());
                if (mListener != null) {
                    mListener.onFragmentInteraction();
                }
                break;
        }
    }
}
