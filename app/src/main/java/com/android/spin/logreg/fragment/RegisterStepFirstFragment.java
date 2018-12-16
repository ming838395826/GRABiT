package com.android.spin.logreg.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.base.mvp.view.IView;
import com.android.base.util.AppLanguageManager;
import com.android.base.util.DensityUtil;
import com.android.base.util.ToastUtil;
import com.android.spin.R;
import com.android.spin.common.AgreementActivity;
import com.android.spin.common.util.Constant;
import com.android.spin.logreg.RegisterActivity;
import com.android.spin.logreg.presenter.RegisterPresenter;
import com.taobao.uikit.feature.view.TTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：yangqiyun
 * 时间：2017/7/21
 * 1120389276@qq.com
 * 描述：注册第一步
 */

public class RegisterStepFirstFragment extends BaseRegisterFragment implements View.OnClickListener {

    @Bind(R.id.tet_name)
    EditText ttvName;
    @Bind(R.id.tbtn_next)
    TTextView tbtnNext;
    @Bind(R.id.ttv_service)
    TTextView ttvService;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_register_step_first;
    }

    @Override
    public void initView() {
        ttvName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tbtnNext.setEnabled(getTextLength(ttvName) > 0 && getTextLength(ttvName) < 21 );
            }
        });
    }

    // TODO: Rename and change types and number of parameters
    public static RegisterStepFirstFragment newInstance() {
        RegisterStepFirstFragment fragment = new RegisterStepFirstFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tbtn_next,R.id.ttv_service})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tbtn_next:
                //下一步
                checkParams();
                break;
            case R.id.ttv_service:
                //查看服務條款
                if (AppLanguageManager.isLanguageEN()) {
                    AgreementActivity.star(getActivity(), Constant.URL_SERVICE_AGREEMENT_EN);
                } else {
                    AgreementActivity.star(getActivity(), Constant.URL_SERVICE_AGREEMENT_CN);
                }

                break;
        }
    }

    /**
     *
     */
    private void checkParams() {
        if (ttvName.getText().toString().trim().length() == 0 || ttvName.getText().toString().trim().length() > 20) {
            ToastUtil.shortShow(getString(R.string.text_name_error_length));
            return;
        }
        ((RegisterActivity) getActivity()).getViewDelegate()
                .getQuestEntity().setName(ttvName.getText().toString().trim());
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    private void show2Service() {
        Dialog bottomDialog = new Dialog(getActivity(), R.style.BottomDialog);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_content_service, null);
        bottomDialog.setContentView(contentView);
        bottomDialog.setCanceledOnTouchOutside(true);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = DensityUtil.getWidth();
        params.height = DensityUtil.getHeight() * 2/3;
        contentView.setLayoutParams(params);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
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
