package com.android.spin.mine.delegate;

import android.support.annotation.NonNull;
import android.view.View;

import com.android.base.base.delegate.AppDelegate;
import com.android.base.base.delegate.MvpDelegate;
import com.android.base.mvp.view.IView;
import com.android.spin.R;
import com.android.spin.db.UserManager;
import com.android.spin.mine.ChangePhoneActivity;
import com.android.spin.mine.presenter.MinePresenter;
import com.android.spin.util.string.FormatStringUtil;
import com.taobao.uikit.feature.view.TTextView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 作者：yangqiyun
 * 时间：2017/10/1
 * 1120389276@qq.com
 * 描述：
 */

public class ContactUsDelegate extends MvpDelegate<IView, MinePresenter> {

    private static final int GET_CONTACT = 0x01;

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

    }

    @NonNull
    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter();
    }
}
