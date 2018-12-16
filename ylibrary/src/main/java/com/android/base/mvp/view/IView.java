package com.android.base.mvp.view;

/**
 * Created by Administrator on 2016/7/23.
 */
public interface IView<T> {

    /**
     * 失败
     * */
    void onFail(String code, int type);
    /**
     * 完成
     * */
    void onComplete(int type);

    /**
     * 成功
     * */
    void onSuccess(T data, int type);
}
