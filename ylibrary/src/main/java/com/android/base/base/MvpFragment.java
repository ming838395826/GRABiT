package com.android.base.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.base.mvp.persenrter.MvpPresenter;
import com.android.base.mvp.view.IView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 作者：yangqiyun
 * 时间：2017/6/11
 * 1120389276@qq.com
 * 描述：与baseactivity同属性
 */

public abstract class MvpFragment<V extends IView,T extends MvpPresenter<V>> extends BaseFragment implements IView{

    protected T presenter;

    public T getPresenter() {
        if(presenter == null){
            presenter = initPresenter();
            presenter.attachView((V)this);
        }
        return presenter;
    }

    public T initPresenter(){
        return null;
    }

    @Override
    public void onDestroy() {
        if(presenter != null){
            presenter.detachView();
            presenter = null;
        }
        super.onDestroy();
    }
}
