package com.android.base.view.listview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.base.view.listview.adapter.BaseListAdapter;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * common列表包装类，列表基于RecyclerView，下拉刷新基于 ptr，封装一些列表的基础操作（如网络请求、数据异步更新、翻页、下拉刷新等）
 *
 * @author WilliamChik on 15/8/14.
 */
public class CommonListViewWrapper extends RecyclerView.OnScrollListener implements PtrHandler {

  public static final int LIST_REFRESH = 0;

  public static final int LIST_LOAD = 1;

  private static final String TAG = CommonListViewWrapper.class.getSimpleName();

  public static final String PAGE_PARAM = "page";

  private static final String LOG_INIT_CONFIG = "使用配置类初始化 CommonListViewWrapper";
  private static final String ERROR_INIT_CONFIG_WITH_NULL = "CommonListViewWrapper 配置类为空";
  private static final String WARNING_RE_INIT_CONFIG = "CommonListViewWrapper 已经配置过，不要重新配置";

  // 配置对象
  private CommonListViewWrapperConfig mconfig;
  // 是否下拉刷新操作
  private boolean mIsPtrRefresh = false;
  //列数 用于ListView九宫格模式下
  private int mCount = 1;
  private int mHeaderCount = 0;

  private int pageSize = 20;
  private int pages = 1;
  private int current = 1;

  private boolean isNoData = false;
  /**
   * 使用配置类初始化 CommonListViewWrapper，注意如果配置类已经设置过，则这个方法不会做任何事情
   *
   * @param config 配置类
   * @throws IllegalArgumentException 如果配置为空
   */
  public void init(CommonListViewWrapperConfig config) {
    if (config == null) {
      throw new IllegalArgumentException(ERROR_INIT_CONFIG_WITH_NULL);
    }
    if (mconfig == null) {
//      DebugLog.d(TAG, LOG_INIT_CONFIG);
      mconfig = config;
      initInner();
    } else {
//      DebugLog.w(TAG, WARNING_RE_INIT_CONFIG);
    }
  }

  public void setInProgress(){
    mconfig.mListView.setVisibility(View.GONE);
    if(mconfig.mEmptyView != null){
      mconfig.mEmptyView.setVisibility(View.VISIBLE);
      mconfig.mEmptyView.setInProgress();
    }
  }
  /**
   *
   * 无数据
   * */
  public void setInEmptyView(){
    mconfig.mListView.setVisibility(View.GONE);
    if(mconfig.mEmptyView != null){
      mconfig.mEmptyView.setVisibility(View.VISIBLE);
      mconfig.mEmptyView.setInEmptyView();
    }
  }

  public void setPageSize(int pageSize){
    this.pageSize = pageSize;
  }

  public void setPages(int pages){
    this.pages = pages;
  }

  /**
   *
   * 网路错误
   * */
  public void setInNetError(){
    mconfig.mListView.setVisibility(View.GONE);
    if(mconfig.mEmptyView != null){
      mconfig.mEmptyView.setVisibility(View.VISIBLE);
      mconfig.mEmptyView.setInNetError();
    }
  }

  /**
   * UI 元素初始化，注意这里不能有非UI（即数据）相关的初始化操作，因为UI初始化总是在网络请求之后进行
   */
  private void initInner() {
    mconfig.mListView.setLayoutManager(mconfig.mLayoutManager);
    mconfig.mListView.setAdapter(mconfig.mListAdapter);
    mconfig.mListView.setHasFixedSize(true);
    mconfig.mListView.setOnScrollListener(this);
    // 如果启动了下拉刷新，让 PtrFrameLayout 设置 PtrHandler 用于监听下拉事件
    if (mconfig.mPtrFrameLayout != null) {
      mconfig.mPtrFrameLayout.setPtrHandler(this);
    }
    // 如果启动了加载更多，则设置相应的回调
    if (mconfig.mLoadMoreHandler != null) {
      mconfig.mLoadMoreHandler.setCallBack(new CommonListLoadMoreHandler.LoadMoreCallBack() {
        @Override
        public void onClickLoadMore() {
          int lastVisibleItemPosition = mconfig.mLayoutManager.findLastCompletelyVisibleItemPositions(null)[0];
          int totalItemCount = mconfig.mLayoutManager.getItemCount();
          if (lastVisibleItemPosition * mCount > totalItemCount - 1 && mconfig.mLoadMoreHandler
                  .canLoadMore() && !mconfig.mLoadMoreHandler.getLoadStatu()) {
            mconfig.mLoadMoreHandler.onLoadStart();
            onPtrHandlerLoadListener.onLoadBegin();
          }
        }
      });
    }
  }
  //设置列表列数
  public void setColumm(int columm){
    this.mCount = columm;
  }
  //设置列表列数
  public void addHeaderCount(){
    this.mHeaderCount ++;
  }
  //设置列表列数
  public void removeHeaderCount(){
    if(mHeaderCount <= 0){
      return;
    }
    this.mHeaderCount --;
  }

  public int getmCount(){
    return mconfig.mListAdapter.getDataList().size();
  }

  public BaseListAdapter getAdapter(){
    return mconfig.mListAdapter;
  }
  /**
   * 添加单个
   * */
  public void addItem(Object obj){
    mconfig.mListAdapter.getDataList().add(obj);
    mconfig.mListAdapter.notifyDataSetChanged();
  }

  /**
   * 网络请求后 或 直接用获取到的数据 更新列表数据到当前数据集的尾部
   *
   * @param data 列表数据
   */
  public void updateListData(final List data) {
    // 下拉刷新时，需要先把数据清空
    if (mIsPtrRefresh) {
      isNoData = false;
      mconfig.mListAdapter.clearDataList();
      mconfig.mListAdapter.notifyDataSetChanged();
      mIsPtrRefresh = false;
      if (mconfig.mLoadMoreHandler != null) {
        mconfig.mLoadMoreHandler.showFooter();
      }
    }
    if (data == null || data.size() < pageSize) {
      isNoData = true;
      if (mconfig.mLoadMoreHandler != null) {
        mconfig.mLoadMoreHandler.setEmpty();
//        return;
      }
    }
    if (data != null && data.size() > 0) {
      // 添加【加载更多】的脚部视图的操作必须放在 notifyItemRangeInserted()之前，否则 notifyItemRangeInserted() 的渐现动画会失效
      if (mconfig.mLoadMoreHandler != null) {
        mconfig.mLoadMoreHandler.onWaitToLoadMore(mconfig.mListView);
      }

      int headerCount = mconfig.mListView.getHeaderViewsCount();
      int footerCount = mconfig.mListView.getFooterViewsCount();
      int originalItemCount = mconfig.mListAdapter.getItemCount();

      mconfig.mListAdapter.addDataList(data);
      // 这里不要采用notifyDataSetChanged()，因为该方法会导致数据数组全部更新，从而导致列表的全局刷新；而使用notifyItemRangeInserted()只会让列表局部刷新。
      // 这里采用notifyItemRangeInserted() | notifyItemRangeChanged() 的另外一个好处就是，RecyclerView局部刷新会支持动画效果，用户体验较佳。
      mconfig.mListAdapter.notifyItemRangeInserted(originalItemCount + headerCount + footerCount, data.size());
      mconfig.mListView.scheduleLayoutAnimation();
    } else {
      // 返回的列表数据为空，请求参数重置为上一次请求成功时的状态
//      mBizParams.putAll(mFormerBizParams);
    }

    if (mconfig.mLoadMoreHandler != null) {
      mconfig.mLoadMoreHandler.onLoadFinish(mconfig.mListView);
    }
  }

  /**
   * 如果启动了下拉刷新，则在数据加载完成时通知 PtrFrameLayout 完成刷新操作
   */
  private void refreshComplete() {
    if (mconfig.mPtrFrameLayout != null && mconfig.mPtrFrameLayout.isRefreshing()) {
      mconfig.mPtrFrameLayout.refreshComplete();
    }
  }

  /**
   * 删除脚部
   */
  public void removeFooter() {
    if (mconfig.mLoadMoreHandler != null) {
      mconfig.mLoadMoreHandler.removeFooter(mconfig.mListView);
    }
  }

  /**
   * 重置 page 参数值，令 page = 1
   * 列表重用时会用到
   */
  public void resetPageParam() {
//    mBizParams.put(PAGE_PARAM, "1");
  }

//  /**
//   * “page” 参数加 1
//   *
//   * @return "-1" if error occurred
//   */
//  private String pagePlusOne() {
//    if (mBizParams == null) {
//      return "-1";
//    }
//
//    String page = mBizParams.get(PAGE_PARAM).toString();
//    try {
//      page = String.valueOf(Integer.valueOf(page) + 1);
//    } catch (Exception e) {
//      e.printStackTrace();
//      page = "-1";
//    }
//    return page;
//  }

  @Override
  public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
    if (mconfig.mOnScrollListener != null) {
      mconfig.mOnScrollListener.onScrollStateChanged(recyclerView, newState);
    }
    // 加载更多功能开启，则在列表滚动到底部时加入加载更多的逻辑
    if (mconfig.mLoadMoreHandler != null && !isNoData) {
      if (newState == RecyclerView.SCROLL_STATE_IDLE) {
        // 列表已停止滑动
        int lastVisibleItemPosition = mconfig.mLayoutManager.findLastCompletelyVisibleItemPositions(null)[0];
        int totalItemCount = mconfig.mLayoutManager.getItemCount();
        if (lastVisibleItemPosition * mCount > totalItemCount - 1 - mHeaderCount && mconfig.mLoadMoreHandler
                .canLoadMore() && !mconfig.mLoadMoreHandler.getLoadStatu()) {
          mconfig.mLoadMoreHandler.onLoadStart();
          onPtrHandlerLoadListener.onLoadBegin();
        }
      }
    }
  }

  @Override
  public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    if (mconfig.mOnScrollListener != null) {
      mconfig.mOnScrollListener.onScrolled(recyclerView, dx, dy);
    }
  }

  @Override
  public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
    // 允许下拉刷新的时机
    return PtrDefaultHandler.checkContentCanBePulledDown(frame, mconfig.mListView, header);
  }

  @Override
  public void onRefreshBegin(PtrFrameLayout frame) {
    mIsPtrRefresh = true;
    if(onPtrHandlerRefreshListener != null){
      onPtrHandlerRefreshListener.onRefreshBegin();
    }
  }
  /**
   * 手动设置刷新状态
   * */
  public void setIsRefresh(boolean mIsPtrRefresh){
    this.mIsPtrRefresh = mIsPtrRefresh;
  }

  /**
   * 滚动加载更多模块的回调
   */
  public interface LoadMoreCallBack {

    /**
     * 点击加载更多视图时的回调
     */
    void onClickLoadMore();

  }
  /**
   * 由于 CommonListWrapper 内部实现了 OnScrollListener，这里再做多一个接口暴露给外层调用，
   * 接口参考 {@link RecyclerView.OnScrollListener}
   */
  public abstract static class OnScrollListener {

    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    }

  }
  /**
   *刷新及加载接口
   */
  private OnPtrHandlerRefreshListener onPtrHandlerRefreshListener;
  public interface OnPtrHandlerRefreshListener{
    void onRefreshBegin();
    void onRefreshError();
  }
  public void setOnPtrHandlerRefreshListener(OnPtrHandlerRefreshListener onPtrHandlerRefreshListener){
    this.onPtrHandlerRefreshListener = onPtrHandlerRefreshListener;
  }
  private OnPtrHandlerLoadListener onPtrHandlerLoadListener;

  public interface OnPtrHandlerLoadListener{
    void onLoadBegin();
    void onLoadError();
  }
  public void setOnPtrHandlerLoadListener(OnPtrHandlerLoadListener onPtrHandlerLoadListener){
    this.onPtrHandlerLoadListener = onPtrHandlerLoadListener;
  }
}
