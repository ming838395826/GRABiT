package com.android.spin.shop.fragment;


import android.app.Dialog;
import android.graphics.Color;
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
import com.android.base.util.ToastUtil;
import com.android.base.view.layout.PtrMaterialFrameLayout;
import com.android.base.view.listview.CommonListLoadMoreHandler;
import com.android.base.view.listview.CommonListViewWrapper;
import com.android.base.view.listview.CommonListViewWrapperConfig;
import com.android.base.view.listview.ListItemDecoration;
import com.android.spin.R;
import com.android.spin.card.entity.CardItemEntity;
import com.android.spin.common.util.Constant;
import com.android.spin.event.AddCardEvent;
import com.android.spin.event.UpdateCardEvent;
import com.android.spin.home.HomeActivity;
import com.android.spin.home.entity.ProUpdateEvent;
import com.android.spin.shop.adapter.GoodListAdapter;
import com.android.spin.shop.entity.ShopProductItemEntity;
import com.android.spin.shop.presenter.ShopPresenter;
import com.android.spin.util.DialogUtil;
import com.android.spin.util.ErrorToastUtli;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.taobao.uikit.feature.view.TRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.http.POST;

/**
 * 作者：yangqiyun
 * 时间：2017/9/6
 * 1120389276@qq.com
 * 描述：发现好物
 */
public class ShopListNewFragment extends MvpFragment<IView, ShopPresenter> implements
        CommonListViewWrapper.OnPtrHandlerRefreshListener, CommonListViewWrapper.OnPtrHandlerLoadListener{

    private final static int TYPE_REQUEST_CURRENT = 0x01;//获取列表
    private final static int TYPE_POST_USER_COUPONS = 0x02;//领取卡片
    private final static int TYPE_GET_GOODS_DETAIL = 0x04;//获取详情
    private final static int TYPE_CHECK_RECEIVED = 0x05;//获取详情
    @Bind(R.id.tr_shop_list)
    TRecyclerView mTrShopList;

    @Bind(R.id.ptr_material_style_ptr_frame)
    PtrMaterialFrameLayout mPtrMaterialStylePtrFrame;
    private GoodListAdapter mGoodListAdapter;
    private CommonListViewWrapper mListWrapper;

    private int page = 1;
    private final int perPage = 20;
    private boolean isRefresh = false;
    private int recevierPosition=-1;

    private List<ShopProductItemEntity> items;

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

    private int getType() {
        return getArguments().getInt("type");
    }

    @Override
    public void onFail(String code, int type) {
        switch (type){
            case TYPE_REQUEST_CURRENT:
                showNetErrorData();
                break;
            case TYPE_POST_USER_COUPONS:
                if (Constant.FAIL_GET_AGAIN_CODE.equals(code)) {
                    if (getType() == 0) {
                        ToastUtil.shortShow(getString(R.string.text_getter_receied));
                    } else {
                        ToastUtil.shortShow(getString(R.string.text_set_receied));
                    }
                }else {
                    ErrorToastUtli.showErrorToast(code);
                    //刷新商品
                    if(recevierPosition>0){
                        ShopProductItemEntity entity= (ShopProductItemEntity) mListWrapper.getAdapter().getItem(recevierPosition);
                        Map<String,Object> params = new HashMap<>();
                        params.put("id",entity.getId());
                        getPresenter().getShopItemDetailNew(params,TYPE_GET_GOODS_DETAIL);
                        isRefresh = true;
                    }

                }

                if("1006".equals(code)){
                    //库存不足
                    DialogUtil.getNoGoodSDialog(getActivity(), true, null).show();
                }
                break;
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
            case TYPE_POST_USER_COUPONS:
                //获取优惠券成功
                try {
                    isRefresh = false;
                    ShopProductItemEntity item = (ShopProductItemEntity) mListWrapper.getAdapter().getItem(recevierPosition);

//                    mShopProductItemEntity.setCurrent_stock(mShopProductItemEntity.getCurrent_stock() + 1);
//                    String count = mShopProductItemEntity.getStock() - mShopProductItemEntity.getCurrent_stock() + "";
//                    if (getType() == 1) {
//                        ttvProductTagLeft.setText(getResources().getString(R.string.text_unit_comming_left));
//                        ttvProductTag.setText(getResources().getString(R.string.text_unit_comming_right));
//                    } else {
//                        ttvProductTagLeft.setText(getResources().getString(R.string.text_unit_left));
//                        ttvProductTag.setText(getResources().getString(R.string.text_unit_right));
//                    }
//                    ttvProductCount.setText(count);
//
//                    EventBus.getDefault().post(new UpdateCardEvent(mShopProductItemEntity.getId()));
//                    mShopProductItemEntity.setUser_coupon(new ShopProductItemEntity.UserCouponBean());
//                    ttvBtnRight.setText(getString(R.string.text_home_got_it));
//                    ttvBtnRight.setBackgroundColor(Color.parseColor("#66191917"));
//                    ttvBtnRight.setEnabled(false);
                } catch (Exception e) {
                }

                DialogUtil.getGetterDialog(getActivity(), true, new DialogUtil.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view, int position) {
                        ((HomeActivity) getActivity()).getViewDelegate().showCardfragment();
                        EventBus.getDefault().post(new AddCardEvent(0));
                    }
                }).show();
                break;
            case TYPE_GET_GOODS_DETAIL:
                //刷新商品
                try{
                    isRefresh = false;
                    if(mPtrMaterialStylePtrFrame != null){
                        mPtrMaterialStylePtrFrame.refreshComplete();
                        ShopProductItemEntity item = (ShopProductItemEntity) mListWrapper.getAdapter().getItem(recevierPosition);
                        updateEntity(item,(ShopProductItemEntity) data);
                    }
                }catch (Exception e){}
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
        Map<String, Object> params1 = new HashMap<>();
        params1.put("id", 1);
        getPresenter().getShopItemReceived(params1,200);
    }

    /**
     * 数据更新
     * @param entity
     */
    private void updateEntity(ShopProductItemEntity mShopProductItemEntity,ShopProductItemEntity entity){

        if(entity == null){
            return;
        }
        mShopProductItemEntity.setUser_item_notice(entity.getUser_item_notice());
        mShopProductItemEntity.setCurrent_stock(entity.getCurrent_stock());
        mShopProductItemEntity.setUser_coupon(entity.getUser_coupon());
        mShopProductItemEntity.setBusiness(entity.getBusiness());
        mShopProductItemEntity.setBusiness_id(entity.getBusiness_id());
        mShopProductItemEntity.setContact_phone(entity.getContact_phone());
        mShopProductItemEntity.setDescription(entity.getDescription());
        mShopProductItemEntity.setEnd_time(entity.getEnd_time());
        mShopProductItemEntity.setExchange_deadline(entity.getExchange_deadline());
        mShopProductItemEntity.setFront_cover(entity.getFront_cover());
        mShopProductItemEntity.setImages(entity.getImages());
        mShopProductItemEntity.setLocation(entity.getLocation());
        mShopProductItemEntity.setName(entity.getName());
        mShopProductItemEntity.setStart_time(entity.getStart_time());
        mShopProductItemEntity.setStatus(entity.getStatus());
        mShopProductItemEntity.setStock(entity.getStock());

//        Bundle mBundle = getArguments();
//        if (mBundle != null) {
//            mBundle.putSerializable(PAGE_STATUS, mShopProductItemEntity);
//        }
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
        mListAdapter.setOnViewClickListener(new GoodListAdapter.OnViewClickListener() {
            @Override
            public void recevier(int position) {

                Map<String, Object> params = new HashMap<>();
                ShopProductItemEntity entity= (ShopProductItemEntity) mListWrapper.getAdapter().getItem(position);
                recevierPosition=position;
                params.put("card_id", entity.getId());
                getPresenter().postUserCoupons(params, TYPE_POST_USER_COUPONS);

            }

            @Override
            public void showPerson(int position) {
                ShopProductItemEntity entity= (ShopProductItemEntity) mListWrapper.getAdapter().getItem(position);
                CardUserDialogFragment cardUserDialogFragment = CardUserDialogFragment.newInstance(entity.getId()+"");
                cardUserDialogFragment.setTargetFragment(ShopListNewFragment.this, 200);
                cardUserDialogFragment.show(getActivity().getSupportFragmentManager(), "USER");
            }

            @Override
            public void checkRecevoerStatus(int position) {
                ShopProductItemEntity entity= (ShopProductItemEntity) mListWrapper.getAdapter().getItem(position);
                Map<String, Object> params = new HashMap<>();
                params.put("id", entity.getId());
                getPresenter().getShopItemReceived(params,TYPE_CHECK_RECEIVED, position);
            }
        });
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

//                    ARouter.getInstance().build("/app/ShopFundDetail").withTransition(R.anim.slide_in_from_bottom,R.anim.slide_out_to_bottom).
//                            withSerializable("id",((CardItemEntity) mListWrapper.getAdapter().getDataList().get(position)).getItem().getId()).navigation();
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
        page = 1;
        Map<String, Object> params = new HashMap<>();
        params.put(Constant.KEY_PAGE, page);
        params.put(Constant.KEY_PER_PAGE, perPage);
        getData();
    }

    @Override
    public void onRefreshError() {
        page = 1;
    }

    @Override
    public void onLoadBegin() {
        page ++;
        Map<String, Object> params = new HashMap<>();
        params.put(Constant.KEY_PAGE, page);
        params.put(Constant.KEY_PER_PAGE, perPage);
        getData();
    }

    @Override
    public void onLoadError() {
        page --;
    }
}
