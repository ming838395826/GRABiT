package com.android.base.callback.impl;


import android.util.Log;

import com.android.base.module.http.callback.OnNetRequestListener;
import com.android.base.mvp.view.IView;

/**
 * Created by YANGQIYUN on 2016/5/4.
 */
public class OnNetRequestImplListener<T> implements OnNetRequestListener<T> {

    private final String TAG = OnNetRequestImplListener.class.getSimpleName();

    private IView mIView;
    private int type;
    public OnNetRequestImplListener(IView mIView, int type){
        this.mIView = mIView;
        this.type = type;
    }
    @Override
    public void onStart() {
        Log.d(TAG,"-----> onStart");
    }

    @Override
    public void onFinish() {
        Log.d(TAG,"-----> onFinish");
        mIView.onComplete(type);
    }

    @Override
    public void onSuccess(T data) {
        Log.d(TAG,"-----> onSuccess");
        mIView.onSuccess(data,type);
    }

    @Override
    public void onFailure(String t) {
        Log.d(TAG,"-----> onFailure  " + t);
        mIView.onFail(t,type);
    }
}
