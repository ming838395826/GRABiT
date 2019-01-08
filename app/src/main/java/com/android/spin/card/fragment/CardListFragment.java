package com.android.spin.card.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.base.base.BaseFragment;
import com.android.base.base.MvpFragment;
import com.android.base.module.http.callback.ShowApiListResponse;
import com.android.base.mvp.view.IView;
import com.android.base.view.layout.PtrMaterialFrameLayout;
import com.android.base.view.listview.CommonListLoadMoreHandler;
import com.android.base.view.listview.CommonListViewWrapper;
import com.android.base.view.listview.CommonListViewWrapperConfig;
import com.android.base.view.listview.ListItemDecoration;
import com.android.spin.R;
import com.android.spin.card.adapter.CardListAdapter;
import com.android.spin.card.entity.CardItemEntity;
import com.android.spin.card.presenter.CardPresenter;
import com.android.spin.common.util.Constant;
import com.android.spin.draw.view.SwipeItemLayout;
import com.android.spin.event.AddCardEvent;
import com.android.spin.shop.HistoryFundDetailActivity;
import com.android.spin.shop.ShopFundDetailActivity;
import com.android.spin.shop.entity.ShopHistroyItemEntity;
import com.taobao.uikit.feature.view.TRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：yangqiyun
 * 时间：2017/9/5
 * 1120389276@qq.com
 * 描述：卡包列表
 */

public class CardListFragment extends MvpFragment<IView, CardPresenter> implements
        CommonListViewWrapper.OnPtrHandlerRefreshListener, CommonListViewWrapper.OnPtrHandlerLoadListener {

    private static final String TYPE_PARAMS = "status";
    @Bind(R.id.tr_card_list)
    TRecyclerView trCardList;
    @Bind(R.id.ptr_material_style_ptr_frame)
    PtrMaterialFrameLayout ptrMaterialStylePtrFrame;

    private CommonListViewWrapper mListWrapper;

    private static final int TYPE_REQUEST_LIST = 0x00;
    private static final int DELETE_COUPONS = 0x01;
    private static final int SET_COUPONS_USER = 0x02;

    private int page = 1;
    private final int perPage = 20;

    //0:未使用；1：也使用 2：已过期

    @Override
    public CardPresenter initPresenter() {
        return new CardPresenter();
    }

    public CardListFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    public static CardListFragment newInstance(String status) {
        CardListFragment fragment = new CardListFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(TYPE_PARAMS, status);
        fragment.setArguments(mBundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_card_list;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        initListView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCardChange(AddCardEvent event) {
        if (event.isAdd()) {
            onRefreshBegin();
        }
    }

    /**
     * reclerview
     */
    private void initListView() {

        // 兼容 ViewPager，ptr 和 ViewPager 的滑动冲突
        ptrMaterialStylePtrFrame.disableWhenHorizontalMove(true);
        View headerView = new View(getActivity());
        trCardList.addHeaderView(headerView);

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        trCardList.addItemDecoration(new ListItemDecoration(
                getActivity(), LinearLayout.VERTICAL, getResources().getDrawable(R.drawable.list_divider_h10_tran)));
        trCardList.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));

        final CardListAdapter mListAdapter = new CardListAdapter(getActivity());
        mListAdapter.setOnViewClickListener(new CardListAdapter.OnViewClickListener() {
            @Override
            public void setUserdCoupons(int position) {
                showLoadDialog();
                getPresenter().setUserCoupons(mListAdapter.getItem(position).getId(),DELETE_COUPONS,position);
            }

            @Override
            public void setDeleteCoupons(int position) {
                showLoadDialog();
                getPresenter().deleteUserCoupons(mListAdapter.getItem(position).getId(),SET_COUPONS_USER,position);
            }
        });
        mListAdapter.setStatus(getArguments().getString(TYPE_PARAMS));

        mListWrapper = new CommonListViewWrapper();
        mListWrapper.setOnPtrHandlerLoadListener(this);
        mListWrapper.setOnPtrHandlerRefreshListener(this);
        mListWrapper.addHeaderCount();
        mListWrapper.setPageSize(perPage);
        CommonListLoadMoreHandler loadMoreHandler = new CommonListLoadMoreHandler();
        CommonListViewWrapperConfig config =
                new CommonListViewWrapperConfig.Builder().setListAdapter(mListAdapter).setListView(trCardList)
                        .setLayoutManager(layoutManager).setLoadMoreHandler(loadMoreHandler)
                        .setPtrFrameLayout(ptrMaterialStylePtrFrame).build();
        mListWrapper.init(config);
        if("0".equals(getArguments().getString(TYPE_PARAMS))){
            trCardList.setOnItemClickListener(new TRecyclerView.OnItemClickListener() {
                @Override
                public void onItemClick(TRecyclerView parent, View view, int position, long id) {
//                    Intent intent = new Intent(getActivity(),ShopFundDetailActivity.class);
//                    intent.putExtra("id",((CardItemEntity) mListWrapper.getAdapter().getDataList().get(position)).getItem().getId());
//                    startActivity(intent);
//                    getActivity().overridePendingTransition(R.anim.slide_in_from_bottom,R.anim.slide_out_to_bottom);

                    ARouter.getInstance().build("/app/ShopFundDetail").withTransition(R.anim.slide_in_from_bottom,R.anim.slide_out_to_bottom).
                            withSerializable("id",((CardItemEntity) mListWrapper.getAdapter().getDataList().get(position)).getItem().getId()).navigation();
//                    ShopFundDetailActivity.star(getActivity(), ((CardItemEntity) mListWrapper.getAdapter().getDataList().get(position)).getItem().getId());
                }
            });
        }

//        //显示刷新图标
//        ptrMaterialStylePtrFrame.setStartRefresh(DensityUtil.dp2px(80));
        onRefreshBegin();

        showLoadingView();
    }

    @Override
    public void onRefreshBegin() {
        setCommonListViewWrapperRefresh(mListWrapper,true);
        page = 1;
        Map<String, Object> params = new HashMap<>();
        params.put("status", getArguments().getString(TYPE_PARAMS));
        params.put(Constant.KEY_PAGE, page);
        params.put(Constant.KEY_PER_PAGE, perPage);
        getPresenter().getUserCoupons(params, TYPE_REQUEST_LIST);
    }

    @Override
    public void onRefreshError() {
        page = 1;
    }

    @Override
    public void onLoadBegin() {
        page ++;
        Map<String, Object> params = new HashMap<>();
        params.put("status", getArguments().getString(TYPE_PARAMS));
        params.put(Constant.KEY_PAGE, page);
        params.put(Constant.KEY_PER_PAGE, perPage);
        getPresenter().getUserCoupons(params, TYPE_REQUEST_LIST);
    }

    @Override
    public void onLoadError() {
        page --;
    }

    @Override
    public void onFail(String code, int type) {
        showNetWorkError();
    }

    @Override
    public void onComplete(int type) {
        if(mListWrapper != null){
            ptrMaterialStylePtrFrame.refreshComplete();
        }
    }

    @Override
    public void onSuccess(Object data, int type) {
        switch (type) {
            case TYPE_REQUEST_LIST:
                ShowApiListResponse<CardItemEntity> base = (ShowApiListResponse<CardItemEntity>) data;
                List<CardItemEntity> cards = base.getData();
                if (cards == null || cards.size() == 0 && page == 1) {
                    showEmptyData();
                } else {
                    hideEmptyLayoutData();
                }
                if(mOnGetResultListener != null){
                    mOnGetResultListener.onGetResult(getArguments().getString(TYPE_PARAMS),Integer.valueOf(base.getTotal()));
                }
                if(mListWrapper != null){
                    ptrMaterialStylePtrFrame.refreshComplete();
                    mListWrapper.updateListData(cards);
                }
                break;
        }
    }

    private void hideEmptyLayoutData(){
        setVisibility(ptrMaterialStylePtrFrame,View.VISIBLE);
        hideEmptyLayout();
    }

    private void showEmptyData() {
        showEmptyView(getString(R.string.text_card_empty), R.mipmap.icon_no_card);
        setVisibility(ptrMaterialStylePtrFrame,View.GONE);
    }

    private OnGetResultListener mOnGetResultListener;
    public interface OnGetResultListener{
        void onGetResult(String status,int total);
    }

    public void setOnGetResultListener(OnGetResultListener mOnGetResultListener){
        this.mOnGetResultListener = mOnGetResultListener;
    }
}
