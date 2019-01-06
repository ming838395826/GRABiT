package com.android.spin.mine.delegate;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.android.base.base.delegate.AppDelegate;
import com.android.base.base.delegate.MvpDelegate;
import com.android.base.mvp.view.IView;
import com.android.base.util.ToastUtil;
import com.android.spin.R;
import com.android.spin.event.UpdateUserInfoEvent;
import com.android.spin.mine.presenter.MinePresenter;
import com.taobao.uikit.feature.view.TTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 作者：yangqiyun
 * 时间：2017/10/1
 * 1120389276@qq.com
 * 描述：
 */

public class FeedBackDelegate extends MvpDelegate<IView, MinePresenter> implements TextWatcher {
    private static final int feedBack = 0x01;

    @Bind(R.id.et_idea)
    EditText mEtIdea;
    @Bind(R.id.et_email)
    EditText mEtEmail;
    @Bind(R.id.tv_submit)
    TTextView mTvSubmit;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void initWidget() {
        mEtIdea.addTextChangedListener(this);
        mEtEmail.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mTvSubmit.setEnabled(getTextLength(mEtIdea) > 1 && getTextLength(mEtEmail) > 5);
    }

    @OnClick(R.id.tv_submit)
    public void submit() {
        showLoadDialog();
        Map<String, Object> params = new HashMap<>();
        params.put("content", mEtIdea.getText().toString());
        params.put("email", mEtEmail.getText().toString());
        getPrensenter().feedback(params,feedBack);
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
            case feedBack:
                ToastUtil.shortShow(getString(R.string.text_feed_back_success));
                finish();
                break;
        }
    }

    @NonNull
    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter();
    }
}
