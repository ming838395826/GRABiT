package com.android.spin.shop.fragment;


import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.base.base.MvpFragment;
import com.android.base.module.http.callback.ShowApiListResponse;
import com.android.base.mvp.view.IView;
import com.android.base.view.layout.PtrMaterialFrameLayout;
import com.android.base.view.listview.CommonListLoadMoreHandler;
import com.android.base.view.listview.CommonListViewWrapper;
import com.android.base.view.listview.CommonListViewWrapperConfig;
import com.android.base.view.listview.ListItemDecoration;
import com.android.spin.R;
import com.android.spin.card.entity.CardItemEntity;
import com.android.spin.event.UpdateCardEvent;
import com.android.spin.home.entity.ProUpdateEvent;
import com.android.spin.shop.adapter.GoodListAdapter;
import com.android.spin.shop.entity.ShopProductItemEntity;
import com.android.spin.shop.presenter.ShopPresenter;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.taobao.uikit.feature.view.TRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：yangqiyun
 * 时间：2017/9/6
 * 1120389276@qq.com
 * 描述：发现好物
 */
public class ShopListNewFragment extends MvpFragment<IView, ShopPresenter> implements
        CommonListViewWrapper.OnPtrHandlerRefreshListener, CommonListViewWrapper.OnPtrHandlerLoadListener{


    @Bind(R.id.tr_shop_list)
    TRecyclerView mTrShopList;
    @Bind(R.id.ptr_material_style_ptr_frame)
    PtrMaterialFrameLayout mPtrMaterialStylePtrFrame;

    private GoodListAdapter mGoodListAdapter;
    private CommonListViewWrapper mListWrapper;
    private int page = 1;
    private final int perPage = 20;

    private List<ShopProductItemEntity> items;

    private final static int TYPE_REQUEST_CURRENT = 0x01;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    public ShopPresenter initPresenter() {
        return new ShopPresenter();
    }

    public ShopListNewFragment() {
        // Required empty public constructor
    }


    public static ShopListNewFragment newInstance(String status) {
        ShopListNewFragment fragment = new ShopListNewFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("status", "0");
        fragment.setArguments(mBundle);
        return fragment;
    }

    @Override
    public void onFail(String code, int type) {
        if (type == TYPE_REQUEST_CURRENT) {
            showNetErrorData();
        }
    }

    @Override
    public void onComplete(int type) {
        dismissLoadDialog();
    }

    @Override
    public void onSuccess(Object data, int type) {
        switch (type) {
            case TYPE_REQUEST_CURRENT:
                //普通
                ShowApiListResponse<ShopProductItemEntity> mShopProducts = (ShowApiListResponse<ShopProductItemEntity>) data;
                items = mShopProducts.getData();
                if (items == null || items.size() == 0) {
                    showEmptyData();
                } else {
                    hideEmptyLayoutData();
                }
//                if(mOnGetResultListener != null){
//                    mOnGetResultListener.onGetResult(getArguments().getString(TYPE_PARAMS),Integer.valueOf(base.getTotal()));
//                }
                if(mListWrapper != null){
                    mPtrMaterialStylePtrFrame.refreshComplete();
                    mListWrapper.updateListData(items);
                }
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shop_list;
    }

    @Override
    public void initView() {
        initListView();
        getData();
    }

    /**
     * reclerview
     */
    private void initListView() {

        // 兼容 ViewPager，ptr 和 ViewPager 的滑动冲突
        mPtrMaterialStylePtrFrame.disableWhenHorizontalMove(true);
        View headerView = new View(getActivity());
        mTrShopList.addHeaderView(headerView);

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mTrShopList.addItemDecoration(new ListItemDecoration(
                getActivity(), LinearLayout.VERTICAL, getResources().getDrawable(R.drawable.list_divider_h10_tran)));

        GoodListAdapter mListAdapter = new GoodListAdapter(getActivity());
        mListAdapter.setStatus("0");

        mListWrapper = new CommonListViewWrapper();
        mListWrapper.setOnPtrHandlerLoadListener(this);
        mListWrapper.setOnPtrHandlerRefreshListener(this);
        mListWrapper.addHeaderCount();
        mListWrapper.setPageSize(perPage);
        CommonListLoadMoreHandler loadMoreHandler = new CommonListLoadMoreHandler();
        CommonListViewWrapperConfig config =
                new CommonListViewWrapperConfig.Builder().setListAdapter(mListAdapter).setListView(mTrShopList)
                        .setLayoutManager(layoutManager).setLoadMoreHandler(loadMoreHandler)
                        .setPtrFrameLayout(mPtrMaterialStylePtrFrame).build();
        mListWrapper.init(config);
        if("0".equals("0")){
            mTrShopList.setOnItemClickListener(new TRecyclerView.OnItemClickListener() {
                @Override
                public void onItemClick(TRecyclerView parent, View view, int position, long id) {

                    ARouter.getInstance().build("/app/ShopFundDetail").withTransition(R.anim.slide_in_from_bottom,R.anim.slide_out_to_bottom).
                            withSerializable("id",((CardItemEntity) mListWrapper.getAdapter().getDataList().get(position)).getItem().getId()).navigation();
                }
            });
        }

//        //显示刷新图标
//        ptrMaterialStylePtrFrame.setStartRefresh(DensityUtil.dp2px(80));
        onRefreshBegin();

        showLoadingView();
    }

    /**
     * 初始化数据
     */
    private void initPages(List<ShopProductItemEntity> list) {

    }

    /**
     * 根据页面请求数据
     */
    private void getData() {
        showLoadView();
        getPresenter().getShopCurrent(null, TYPE_REQUEST_CURRENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    private void hideEmptyLayoutData() {
        setVisibility(mTrShopList, View.VISIBLE);
        hideEmptyLayout();
    }

    private void showLoadView() {
        showLoadingView();
        setVisibility(mTrShopList, View.GONE);
    }

    private void showNetErrorData() {
        showNetWorkError();
        setVisibility(mTrShopList, View.GONE);
    }

    private void showEmptyData() {
        showEmptyView(getString(R.string.text_fund_empty), R.mipmap.icon_empty);
        setVisibility(mTrShopList, View.GONE);
    }

    @Override
    public void onRefreshAgain() {
        super.onRefreshAgain();
        getData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeBean(UpdateCardEvent event) {
//        for(){
//
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdate(ProUpdateEvent event) {
        getData();
    }

    @Override
    public void onRefreshBegin() {
        setCommonListViewWrapperRefresh(mListWrapper,true);
//        page = 1;
//        Map<String, Object> params = new HashMap<>();
//        params.put("status", getArguments().getString(TYPE_PARAMS));
//        params.put(Constant.KEY_PAGE, page);
//        params.put(Constant.KEY_PER_PAGE, perPage);
//        getPresenter().getUserCoupons(params, TYPE_REQUEST_LIST);
        getData();
    }

    @Override
    public void onRefreshError() {

    }

    @Override
    public void onLoadBegin() {

    }

    @Override
    public void onLoadError() {

    }
}
