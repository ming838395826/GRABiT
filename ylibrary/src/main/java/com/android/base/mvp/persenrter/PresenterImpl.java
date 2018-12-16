package com.android.base.mvp.persenrter;

import com.android.base.mvp.view.IView;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Created by YANGQIYUN on 2017/4/6.
 */

public class PresenterImpl<V extends IView> implements IPresenter {
    //
    protected IView mIView;
    //请求参数
    protected Map<String, Object> params;

    private WeakReference<V> viewRef;

    /**
     * 获取view接口
     *
     * @return view接口
     */
    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    /**
     * 初始化
     *
     * @param view view接口
     */
    public void attachView(V view) {
        viewRef = new WeakReference<V>(view);
        onViewAttached();
    }

    /**
     * presenter与view绑定回调方法，应该在这里进行一些初始化操作
     */
    protected void onViewAttached() {

    }

    /**
     * presenter与view绑定回调方法，应该在这里进行一些清理操作
     */
    protected void onViewDetached() {

    }

    public Map<String, Object> pull(Map<String, String> p){
        synchronized(PresenterImpl.class){
            if(params == null && p != null){
                params.putAll(p);
            }
        }
        return params;
    }

    public Map<String, Object> cleanParam(){
        synchronized(PresenterImpl.class) {
            if (params != null) {
                params.clear();
            }
        }
        return params;
    }

    public IView getmIView() {
        return mIView;
    }

    public void setmIView(IView mIView) {
        this.mIView = mIView;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
