package com.android.base.base.delegate;

import android.support.annotation.NonNull;

import com.android.base.mvp.persenrter.MvpPresenter;
import com.android.base.mvp.view.IView;

import butterknife.ButterKnife;

/**
 * 作者：yangqiyun
 * 时间：2017/8/31
 * 1120389276@qq.com
 * 描述：
 */

public abstract class MvpDelegate<V extends IView, P extends MvpPresenter<V>>  extends AppDelegate implements IView{

    protected P mPresenter;

    /**
     * 创建presenter
     *
     * @return presenter
     */
    protected abstract
    @NonNull
    P createPresenter();

    protected P getPrensenter(){
        if(mPresenter == null){
            mPresenter = createPresenter();
            mPresenter.attachView((V) this);
        }
        return mPresenter;
    }

    @Override
    public void onDestroy() {

        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }
}
