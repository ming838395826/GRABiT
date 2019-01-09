package com.android.spin.shop.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.base.callback.impl.OnNetRequestImplListener;
import com.android.base.module.http.callback.ShowApiListResponse;
import com.android.base.mvp.view.IView;
import com.android.base.view.layout.PtrMaterialFrameLayout;
import com.android.base.view.listview.CommonListLoadMoreHandler;
import com.android.base.view.listview.CommonListViewWrapper;
import com.android.base.view.listview.CommonListViewWrapperConfig;
import com.android.base.view.listview.ListItemDecoration;
import com.android.spin.R;
import com.android.spin.card.entity.CardItemEntity;
import com.android.spin.shop.adapter.CardUserAdapter;
import com.android.spin.shop.adapter.GoodListAdapter;
import com.android.spin.shop.entity.ShopProductItemEntity;
import com.android.spin.shop.model.ShopModel;
import com.taobao.uikit.feature.view.TImageView;
import com.taobao.uikit.feature.view.TRecyclerView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by ming on 2018/10/3.
 */

public class CardUserDialogFragment extends DialogFragment implements
    CommonListViewWrapper.OnPtrHandlerRefreshListener, CommonListViewWrapper.OnPtrHandlerLoadListener,IView<Object>{


    @Bind(R.id.tr_user_list)
    TRecyclerView mTrUserList;
    @Bind(R.id.ptr_material_style_ptr_frame)
    PtrMaterialFrameLayout mPtrMaterialStylePtrFrame;
    @Bind(R.id.timg_close)
    TImageView mTimgClose;


    private int page = 1;
    private final int perPage = 20;
    private String id;
    private CommonListViewWrapper mListWrapper;
    private ShopModel mShopModel = new ShopModel();

    public static CardUserDialogFragment newInstance(String id) {
        CardUserDialogFragment fragment = new CardUserDialogFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_card_user, container, false);
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ButterKnife.bind(this, view);
        return view;
    }

    public void initRefresh() {
// 兼容 ViewPager，ptr 和 ViewPager 的滑动冲突
        mPtrMaterialStylePtrFrame.disableWhenHorizontalMove(true);
        View headerView = new View(getActivity());
        mTrUserList.addHeaderView(headerView);

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mTrUserList.addItemDecoration(new ListItemDecoration(
                getActivity(), LinearLayout.VERTICAL, getResources().getDrawable(R.drawable.list_divider_h10_tran)));

        CardUserAdapter mListAdapter = new CardUserAdapter(getActivity());

        mListWrapper = new CommonListViewWrapper();
        mListWrapper.setOnPtrHandlerLoadListener(this);
        mListWrapper.setOnPtrHandlerRefreshListener(this);
        mListWrapper.addHeaderCount();
        mListWrapper.setPageSize(perPage);
        CommonListLoadMoreHandler loadMoreHandler = new CommonListLoadMoreHandler();
        CommonListViewWrapperConfig config =
                new CommonListViewWrapperConfig.Builder().setListAdapter(mListAdapter).setListView(mTrUserList)
                        .setLayoutManager(layoutManager).setLoadMoreHandler(loadMoreHandler)
                        .setPtrFrameLayout(mPtrMaterialStylePtrFrame).build();
        mListWrapper.init(config);
//        //显示刷新图标
//        ptrMaterialStylePtrFrame.setStartRefresh(DensityUtil.dp2px(80));
        onRefreshBegin();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRefresh();
        id = getArguments().getString("id");
        getList();
    }

    public void getList() {
        mShopModel.getCardUser(id,new OnNetRequestImplListener<ShowApiListResponse<ShopProductItemEntity>>(this, 1) {
            @Override
            public void onSuccess(ShowApiListResponse<ShopProductItemEntity> data) {
                super.onSuccess(data);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.85f), (int) (dm.heightPixels * 0.7f));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.timg_close)
    public void onViewClicked() {
        dismiss();
    }

    @Override
    public void onRefreshBegin() {
        if(mListWrapper != null){
            mListWrapper.setIsRefresh(true);
        }
        page=1;
        getList();
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

    @Override
    public void onFail(String code, int type) {

    }

    @Override
    public void onComplete(int type) {

    }

    @Override
    public void onSuccess(Object data, int type) {

    }
}
