package com.android.spin.mine.delegate;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.android.base.base.delegate.MvpDelegate;
import com.android.base.mvp.view.IView;
import com.android.spin.R;
import com.android.spin.mine.entity.ContactUsEntity;
import com.android.spin.mine.presenter.MinePresenter;

import butterknife.Bind;

/**
 * 作者：yangqiyun
 * 时间：2017/10/1
 * 1120389276@qq.com
 * 描述：
 */

public class ContactUsDelegate extends MvpDelegate<IView, MinePresenter> {

    private static final int GET_CONTACT = 0x01;
    @Bind(R.id.tv_phone)
    TextView mTvPhone;
    @Bind(R.id.tv_email)
    TextView mTvEmail;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_contact_us;
    }

    @Override
    public void initWidget() {
        getPrensenter().getContactUsInfo(GET_CONTACT);
    }

    @Override
    public void onFail(String code, int type) {

    }

    @Override
    public void onComplete(int type) {

    }

    @Override
    public void onSuccess(Object data, int type) {
        switch (type) {
            case GET_CONTACT:
                if(data instanceof ContactUsEntity){
                    mTvPhone.setText(((ContactUsEntity) data).getValue().getPhone());
                    mTvEmail.setText(((ContactUsEntity) data).getValue().getEmail());
                }

                break;
        }
    }

    @NonNull
    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter();
    }
}
