package com.android.spin.shop.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.base.base.MvpFragment;
import com.android.base.event.TokenFailEvent;
import com.android.base.module.http.callback.ChildSubscriber;
import com.android.base.module.http.callback.ShowApiListResponse;
import com.android.base.module.http.callback.ShowApiResponse;
import com.android.base.mvp.view.IView;
import com.android.base.util.SignHelper;
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
import com.android.spin.home.entity.NoticeResult;
import com.android.spin.home.entity.ProUpdateEvent;
import com.android.spin.shop.adapter.GoodListAdapter;
import com.android.spin.shop.entity.CardUserEntity;
import com.android.spin.shop.entity.RecevierResultEntity;
import com.android.spin.shop.entity.ShopItemEntity;
import com.android.spin.shop.entity.ShopItemReceivedEntity;
import com.android.spin.shop.entity.ShopProductItemEntity;
import com.android.spin.shop.presenter.ShopPresenter;
import com.android.spin.shop.service.ShopRetrofitService;
import com.android.spin.util.DialogUtil;
import com.android.spin.util.ErrorToastUtli;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.rajesh.zlbum.ui.AlbumActivity;
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
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.POST;

/**
 * 作者：yangqiyun
 * 时间：2017/9/6
 * 1120389276@qq.com
 * 描述：发现好物
 */
public class ShopListNewFragment extends MvpFragment<IView, ShopPresenter> implements
        CommonListViewWrapper.OnPtrHandlerRefreshListener, CommonListViewWrapper.OnPtrHandlerLoadListener {

    private final static int TYPE_REQUEST_CURRENT = 0x01;//获取列表
    private final static int TYPE_POST_USER_COUPONS = 0x02;//领取卡片
    private final static int TYPE_POST_SET_TIP = 0x03;//预约提醒
    private final static int TYPE_GET_GOODS_DETAIL = 0x04;//获取详情
    private final static int TYPE_CHECK_RECEIVED = 0x05;//检查状态
    @Bind(R.id.tr_shop_list)
    TRecyclerView mTrShopList;

    @Bind(R.id.ptr_material_style_ptr_frame)
    PtrMaterialFrameLayout mPtrMaterialStylePtrFrame;
    private GoodListAdapter mGoodListAdapter;
    private CommonListViewWrapper mListWrapper;

    private int page = 1;
    private final int perPage = 20;
    private boolean isRefresh = false;
    private int recevierPosition = -1;

    private List<ShopProductItemEntity> items;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private int type=0;

    public void setType(int mType) {
        this.type = mType;
    }

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
        return  type;
//        return getArguments().getInt("type");
    }

    @Override
    public void onFail(String code, int type) {
        switch (type) {
            case TYPE_REQUEST_CURRENT:
                showNetErrorData();
                break;
            case TYPE_POST_USER_COUPONS:
                if (Constant.FAIL_GET_AGAIN_CODE.equals(code)) {
                    if (getType() == 0) {//
                        ToastUtil.shortShow(getString(R.string.text_getter_receied));
                    } else {
                        ToastUtil.shortShow(getString(R.string.text_set_receied));
                    }
                } else {
                    ErrorToastUtli.showErrorToast(code);
                    //刷新商品
                    if (recevierPosition >= 0) {
                        ShopProductItemEntity entity = (ShopProductItemEntity) mListWrapper.getAdapter().getItem(recevierPosition);
                        Map<String, Object> params = new HashMap<>();
                        params.put("id", entity.getId());
                        getPresenter().getShopItemDetailNew(params, TYPE_GET_GOODS_DETAIL);
                        isRefresh = true;
                    }

                }

                if ("1006".equals(code)) {
                    //库存不足
                    DialogUtil.getNoGoodSDialog(getActivity(), true, null).show();
                }
                break;
            case TYPE_CHECK_RECEIVED:
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
                if (mListWrapper != null) {
                    mPtrMaterialStylePtrFrame.refreshComplete();
                    mListWrapper.updateListData(items);
                }

                break;
            case TYPE_POST_USER_COUPONS:
                //获取优惠券成功
                try {
                    isRefresh = false;
                    if (recevierPosition >=0) {
                        RecevierResultEntity result= (RecevierResultEntity) data;
                        ShopProductItemEntity item = (ShopProductItemEntity) mListWrapper.getAdapter().getItem(recevierPosition);
                        item.setIsRecerve(1);
                        if(result!=null){
                            List<ShopItemEntity> items = item.getItems();
                            for (int i=0;i<items.size();i++){
                                if(result.getItem_id().equalsIgnoreCase(items.get(i).getId())){
                                    items.get(i).setCurrent_stock(items.get(i).getCurrent_stock()+1);
                                    break;
                                }
                            }
                        }
                        mListWrapper.getAdapter().notifyItemChanged(recevierPosition + 1);
//                        ShopProductItemEntity entity = (ShopProductItemEntity) mListWrapper.getAdapter().getItem(recevierPosition);
//                        Map<String, Object> params = new HashMap<>();
//                        params.put("id", entity.getId());
//                        getPresenter().getShopItemDetailNew(params, TYPE_GET_GOODS_DETAIL);
//                        isRefresh = true;
                        DialogUtil.getGetterDialog(getActivity(), true, item.getBusiness().getName(), item.getBusiness().getAvatar(),new DialogUtil.OnClickListener() {
                            @Override
                            public void onClick(Dialog dialog, View view, int position) {
                                ((HomeActivity) getActivity()).getViewDelegate().showCardfragment();
                                EventBus.getDefault().post(new AddCardEvent(0));
                            }
                        }).show();
                    }
                } catch (Exception e) {
                }

                break;
            case TYPE_POST_SET_TIP:
                //设置提醒成功
                if (recevierPosition >=0) {
                    final ShopProductItemEntity itemsuccessful = (ShopProductItemEntity) mListWrapper.getAdapter().getItem(recevierPosition);
                    itemsuccessful.setUser_item_notice(new ShopProductItemEntity.userItemNoticeBean());
                    mListWrapper.getAdapter().notifyItemChanged(recevierPosition + 1);
                    DialogUtil.getRemindSetSuccessDialog(getActivity(), true, new DialogUtil.OnClickListener() {
                        @Override
                        public void onClick(Dialog dialog, View view, int position) {
                            EventBus.getDefault().post(new UpdateCardEvent(itemsuccessful.getId()));
                        }
                    }).show();
                }
                try {
//                    mShopProductItemEntity.setUser_item_notice(new ShopProductItemEntity.userItemNoticeBean());
//                    ttvBtnRight.setText(getString(R.string.text_home_grabit_reminder_set));
//                    ttvBtnRight.setBackgroundColor(Color.parseColor("#66191917"));
//                    ttvBtnRight.setEnabled(false);
                } catch (Exception e) {
                }

                break;
            case TYPE_GET_GOODS_DETAIL:
                //刷新商品
                try {
                    isRefresh = false;
                    if (mPtrMaterialStylePtrFrame != null) {
                        mPtrMaterialStylePtrFrame.refreshComplete();
                        ShopProductItemEntity item = (ShopProductItemEntity) mListWrapper.getAdapter().getItem(recevierPosition);
                        updateEntity(item, (ShopProductItemEntity) data);
                        mListWrapper.getAdapter().notifyItemChanged(recevierPosition);
                        recevierPosition=-1;
                    }
                } catch (Exception e) {
                }
                break;
            case TYPE_CHECK_RECEIVED:
                NoticeResult result = (NoticeResult) data;
                ShopProductItemEntity item = (ShopProductItemEntity) mListWrapper.getAdapter().getItem(result.getPosition());
                item.setIsRecerve(1);
                mListWrapper.getAdapter().notifyItemChanged(result.getPosition());
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
        getPresenter().getShopItemReceived(params1, 200);
    }

    /**
     * 数据更新
     *
     * @param entity
     */
    private void updateEntity(ShopProductItemEntity mShopProductItemEntity, ShopProductItemEntity entity) {

        if (entity == null) {
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
        mShopProductItemEntity.setItems(entity.getItems());
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
                ShopProductItemEntity entity = (ShopProductItemEntity) mListWrapper.getAdapter().getItem(position);
                if(getType()==0){
                    Map<String, Object> params = new HashMap<>();
                    recevierPosition = position;
                    params.put("card_id", entity.getId());
                    showLoadDialog();
                    getPresenter().postUserCoupons(params, TYPE_POST_USER_COUPONS);
                }else {
                    //提醒
                    recevierPosition = position;
                    Map<String, Object> params = new HashMap<>();
                    params.put("item_id", entity.getId());
                    showLoadDialog();
                    getPresenter().postShopNotice(params, TYPE_POST_SET_TIP);
                }

            }

            @Override
            public void showPerson(int position) {
                ShopProductItemEntity entity = (ShopProductItemEntity) mListWrapper.getAdapter().getItem(position);
                CardUserDialogFragment cardUserDialogFragment = CardUserDialogFragment.newInstance(entity.getId() + "");
                cardUserDialogFragment.setTargetFragment(ShopListNewFragment.this, 200);
                cardUserDialogFragment.show(getActivity().getSupportFragmentManager(), "USER");
            }

            @Override
            public void checkRecevoerStatus(final int position) {
                ShopProductItemEntity entity = (ShopProductItemEntity) mListWrapper.getAdapter().getItem(position);
                Map<String, Object> params = new HashMap<>();
                params.put("id", entity.getId());
//                getPresenter().getShopItemReceived(params,TYPE_CHECK_RECEIVED, position);
                Observable<ShowApiResponse<ShopItemReceivedEntity>> observable = ShopRetrofitService.getInstance().
                        createShowApi().getShopItemReceived(ShopRetrofitService.getCacheControl(),
                        params.get("id") + "", SignHelper.addSign(params));

                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ChildSubscriber<ShowApiResponse<ShopItemReceivedEntity>>(null) {
                            @Override
                            public void onNext(ShowApiResponse<ShopItemReceivedEntity> o) {
                                int isRecevier = 0;
                                if ("0".equals(o.getCode())) {
                                    isRecevier = 0;
                                } else if ("1".equals(o.getCode())) {
                                    ToastUtil.shortShow(o.getMsg());
                                } else if ("1002".equals(o.getCode())) {
//                                    EventBus.getDefault().post(new TokenFailEvent());
                                } else {
                                    //错误代码处理
                                    if (Constant.FAIL_GET_AGAIN_CODE.equals(o.getCode())) {
                                        isRecevier=1;
                                    }
                                }
                                ShopProductItemEntity item = (ShopProductItemEntity) mListWrapper.getAdapter().getItem(position);
                                item.setIsRecerve(isRecevier);
                                mListWrapper.getAdapter().notifyItemChanged(position + 1);
                            }
                        });
            }

            @Override
            public void showPersonIcon(final int position) {
                ShopProductItemEntity entity = (ShopProductItemEntity) mListWrapper.getAdapter().getItem(position);

                Observable<ShowApiResponse<ShowApiListResponse<CardUserEntity>>> observable = ShopRetrofitService.getInstance().
                        createShowApi().getCardUser(ShopRetrofitService.getCacheControl(), String.valueOf(entity.getId()));

                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ChildSubscriber<ShowApiResponse<ShowApiListResponse<CardUserEntity>>>(null) {
                            @Override
                            public void onNext(ShowApiResponse<ShowApiListResponse<CardUserEntity>> o) {
                                if ("0".equals(o.getCode())) {
                                    ShowApiResponse<ShowApiListResponse<CardUserEntity>> entity=o;
                                    ShopProductItemEntity item = (ShopProductItemEntity) mListWrapper.getAdapter().getItem(position);
                                    if(entity.getData().getData()==null){
                                        item.setUserList(new ArrayList<CardUserEntity>());
                                    }else {
                                        item.setUserList(entity.getData().getData());
                                    }

                                    mListWrapper.getAdapter().notifyItemChanged(position + 1);
                                } else if ("1".equals(o.getCode())) {
                                    ToastUtil.shortShow(o.getMsg());
                                } else if ("1002".equals(o.getCode())) {
//                                    EventBus.getDefault().post(new TokenFailEvent());
                                } else {
                                    //错误代码处理

                                }
                            }
                        });
            }

            @Override
            public void toItemDetail(int position, int childPosition) {
                ShopProductItemEntity entity = (ShopProductItemEntity) mListWrapper.getAdapter().getItem(position);
                ARouter.getInstance().build("/app/ShopDetail").withTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_bottom).
                        withString("id", ((ShopProductItemEntity) mListWrapper.getAdapter().getDataList().get(position)).getItems().get(childPosition).getId())
                        .withLong("startTime", entity.getStart_time())
                        .withInt("type",getType())
                        .withLong("endTime", entity.getEnd_time())
                        .withString("parentId",entity.getId()+"").navigation();
            }

            @Override
            public void showAll(int position) {
                ShopProductItemEntity entity = (ShopProductItemEntity) mListWrapper.getAdapter().getItem(position);
                entity.setShowAll(true);
                mListWrapper.getAdapter().notifyItemChanged(position+1);
            }

            @Override
            public void showPortial(int position) {
                ArrayList<String> imageUri = new ArrayList<>();
                ShopProductItemEntity entity = (ShopProductItemEntity) mListWrapper.getAdapter().getItem(position);
                imageUri.add(entity.getBusiness().getAvatar());
                Intent intent = new Intent(getActivity(), AlbumActivity.class);
                intent.putExtra(AlbumActivity.INTENT_IMAGE, imageUri);
                intent.putExtra(AlbumActivity.INTENT_INDEX, 0);
                startActivity(intent);
            }
        });
        mListAdapter.setStatus(getType());

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
        if ("0".equals("0")) {
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
        Map<String, Object> params = new HashMap<>();
        params.put(Constant.KEY_PAGE, page);
        params.put(Constant.KEY_PER_PAGE, perPage);
        if(getType()==0){
            getPresenter().getShopCurrent(params, TYPE_REQUEST_CURRENT);
        }else if(getType()==1){
            getPresenter().getShopComing(params, TYPE_REQUEST_CURRENT);
        }else if(getType()==2){
            getPresenter().getShopHistory(params,TYPE_REQUEST_CURRENT);
        }
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
        setVisibility(mPtrMaterialStylePtrFrame, View.GONE);
    }

    private void showEmptyData() {
        showEmptyView(getString(R.string.text_fund_empty), R.mipmap.icon_empty);
        setVisibility(mPtrMaterialStylePtrFrame, View.GONE);
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
        onRefreshBegin();
    }

    @Override
    public void onRefreshBegin() {
        setCommonListViewWrapperRefresh(mListWrapper, true);
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
        page++;
        Map<String, Object> params = new HashMap<>();
        params.put(Constant.KEY_PAGE, page);
        params.put(Constant.KEY_PER_PAGE, perPage);
        getData();
    }

    @Override
    public void onLoadError() {
        page--;
    }

    public ShopProductItemEntity getShareItem(){
        return items.get(0);
    }
}
