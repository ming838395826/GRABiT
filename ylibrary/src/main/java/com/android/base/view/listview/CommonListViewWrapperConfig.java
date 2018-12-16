package com.android.base.view.listview;

import android.support.v7.widget.StaggeredGridLayoutManager;

import com.android.base.view.layout.EmptyView;
import com.android.base.view.listview.adapter.BaseListAdapter;
import com.taobao.uikit.feature.view.TRecyclerView;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author YANGQIYUN on 15/12/29.
 */
public final class CommonListViewWrapperConfig<T> {

    // 列表
    final TRecyclerView mListView;

    // 列表对应数据适配器
    final BaseListAdapter<T> mListAdapter;

    // 列表对应的布局管理器
    final StaggeredGridLayoutManager mLayoutManager;

    // 滚动翻页提示工具
    final CommonListLoadMoreHandler mLoadMoreHandler;

    // 下拉刷新组件
    final PtrFrameLayout mPtrFrameLayout;

    // 暴露给外层使用的滚动监听器
    final CommonListViewWrapper.OnScrollListener mOnScrollListener;

    // 异常视图
    final EmptyView mEmptyView;

    private CommonListViewWrapperConfig(final Builder builder) {
        this.mListView = builder.mListView;
        this.mListAdapter = builder.mListAdapter;
        this.mLayoutManager = builder.mLayoutManager;
        this.mLoadMoreHandler = builder.mLoadMoreHandler;
        this.mPtrFrameLayout = builder.mPtrFrameLayout;
        this.mOnScrollListener = builder.mOnScrollListener;
        this.mEmptyView = builder.mEmptyView;
    }

    /**
     * 配置类的 Builder
     */
    public static class Builder<T> {

        // 列表
        private TRecyclerView mListView;

        // 列表对应数据适配器
        private BaseListAdapter<T> mListAdapter;

        // 列表对应的布局管理器
        private StaggeredGridLayoutManager mLayoutManager;

        //     滚动翻页提示工具
        private CommonListLoadMoreHandler mLoadMoreHandler;

        // 下拉刷新组件
        private PtrFrameLayout mPtrFrameLayout;

        private EmptyView mEmptyView;

        // 暴露给外层使用的滚动监听器
        private CommonListViewWrapper.OnScrollListener mOnScrollListener;

        public Builder setEmptyView(EmptyView emptyView) {
            mEmptyView = emptyView;
            return this;
        }

        public Builder setListView(TRecyclerView listView) {
            mListView = listView;
            return this;
        }

        public Builder setLayoutManager(StaggeredGridLayoutManager layoutManager) {
            mLayoutManager = layoutManager;
            return this;
        }

        public Builder setListAdapter(BaseListAdapter adapter) {
            mListAdapter = adapter;
            return this;
        }

        public Builder setLoadMoreHandler(CommonListLoadMoreHandler loadMoreHandler) {
            mLoadMoreHandler = loadMoreHandler;
            return this;
        }

        public Builder setPtrFrameLayout(PtrFrameLayout ptrFrameLayout) {
            mPtrFrameLayout = ptrFrameLayout;
            return this;
        }


        /**
         * 建立配置对象
         */
        public CommonListViewWrapperConfig build() {
            CommonListViewWrapperConfig config = new CommonListViewWrapperConfig(this);
            return config;
        }
    }

}
