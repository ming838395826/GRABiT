package com.android.base.mvp.persenrter;

import android.support.annotation.IntDef;

import com.android.base.mvp.view.IView;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @description: Mvp框架Presenter基类
 * @creator: YANGQIYUN
 * @date: 2017/7/24 下午1:53
 */
public class MvpPresenter<V extends IView> {
    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private WeakReference<V> viewRef;

    // 分页数据
    protected static final int REFRESH = 0x01;      // 刷新数据
    protected static final int LOAD_MORE = 0x02;    // 加载更多数据
    protected int pageNo = 0;    // 当前页面
    protected int pageSize = 10; // 每页数量
    protected int totalNo = 0;   // 总数量

    @IntDef({REFRESH, LOAD_MORE})
    protected @interface LoadType {
    }

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
     * 销毁
     */
    public void detachView() {
        // 清理RxJava观察
        compositeDisposable.clear();
        compositeDisposable.dispose();

        onViewDetached();


        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    /**
     * 判断是否还有下一页数据
     *
     * @return 是否还有下一页
     */
    protected boolean hasMore() {
        return totalNo >= pageNo * pageSize;
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
}
