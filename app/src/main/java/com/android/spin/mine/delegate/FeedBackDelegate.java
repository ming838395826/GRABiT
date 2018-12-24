package com.android.spin.mine.delegate;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.android.base.base.delegate.AppDelegate;
import com.android.spin.R;
import com.taobao.uikit.feature.view.TTextView;

import butterknife.Bind;

/**
 * 作者：yangqiyun
 * 时间：2017/10/1
 * 1120389276@qq.com
 * 描述：
 */

public class FeedBackDelegate extends AppDelegate implements TextWatcher {


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
        mTvSubmit.setEnabled(getTextLength(mEtIdea) > 1 && getTextLength(mEtEmail) > 5 );
    }
}
