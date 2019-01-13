package com.android.spin.shop.delegate;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.base.base.delegate.MvpDelegate;
import com.android.base.mvp.view.IView;
import com.android.base.util.DensityUtil;
import com.android.base.util.image.GlideUtil;
import com.android.spin.R;
import com.android.spin.common.CommonWebActivity;
import com.android.spin.common.selector.view.CircleImageView;
import com.android.spin.common.util.Constant;
import com.android.spin.shop.entity.ShopProductDetailEntity;
import com.android.spin.shop.presenter.ShopPresenter;
import com.taobao.uikit.feature.view.TImageView;
import com.taobao.uikit.feature.view.TTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 作者：yangqiyun
 * 时间：2017/10/14
 * 1120389276@qq.com
 * 描述：
 */

public class ShopFundDetailDelegate extends MvpDelegate<IView, ShopPresenter> implements View.OnClickListener {

    @Bind(R.id.timg_avatar)
    TImageView timgAvatar;
    @Bind(R.id.ttv_status)
    TTextView ttvStatus;
    @Bind(R.id.ttv_goods_title)
    TTextView ttvGoodsTitle;
    @Bind(R.id.timg_shop_avatar)
    CircleImageView timgShopAvatar;
    @Bind(R.id.ttv_shop_name)
    TTextView ttvShopName;
    @Bind(R.id.ttv_goods_content)
    TTextView ttvGoodsContent;
    @Bind(R.id.ttv_goods_count)
    TTextView ttvGoodsCount;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;
    @Bind(R.id.timg_back)
    TImageView timgBack;
    @Bind(R.id.ttv_address)
    TTextView ttvAddress;
    @Bind(R.id.ttv_tel)
    TTextView ttvTel;

    private ShopProductDetailEntity mShopProductDetailEntity;

    @Override
    public void onFail(String code, int type) {
        if (Constant.FAIL_GET_AGAIN_CODE.equals(code)) {
            //已抢到
            setVisibility(ttvStatus, View.VISIBLE);
        }
    }

    @Override
    public void onComplete(int type) {

    }

    @Override
    public void onSuccess(Object data, int type) {
        switch (type) {
            case 0:
                ShopProductDetailEntity entity = (ShopProductDetailEntity) data;
                initData(entity);
                break;
            case 1:
                break;
        }
    }

    @NonNull
    @Override
    protected ShopPresenter createPresenter() {
        return new ShopPresenter();
    }

    private String getShopItemId() {
        return getActivity().getIntent().getStringExtra("id");
    }

    @Override
    public void initWidget() {

//        llBottom.setVisibility(View.GONE);

        ViewGroup.LayoutParams vpParams = timgAvatar.getLayoutParams();
        vpParams.height = (int) (DensityUtil.getWidth() * 0.752);
        timgAvatar.setLayoutParams(vpParams);

        initData();

    }

    private void initData(ShopProductDetailEntity entity) {
        try {
            this.mShopProductDetailEntity = entity;
            if (entity != null) {
                GlideUtil.defaultLoad(this.getActivity(), entity.getFront_cover(), timgAvatar);
                ttvGoodsTitle.setText(entity.getName());
                ttvGoodsContent.setText(entity.getDescription());
                GlideUtil.defaultLoad(this.getActivity(), entity.getBusiness().getAvatar(), timgShopAvatar);
                ttvShopName.setText(entity.getBusiness().getName());
                ttvGoodsCount.setText(entity.getCurrent_stock() + "");
                ttvAddress.setText(entity.getLocation());
                ttvTel.setText(entity.getContact_phone());
            }
        } catch (Exception e) {
        }
    }

    private void initData() {

        Map<String, Object> params = new HashMap<>();
        params.put("id", getShopItemId());
        getPrensenter().getShopItemDetail(params, 0);

        Map<String, Object> params1 = new HashMap<>();
        params1.put("id", getShopItemId());
        getPrensenter().getShopItemReceived(params, 1);
    }

    @OnClick({R.id.timg_back,R.id.ll_shop_info})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.timg_back:
                finish();
                break;
            case R.id.ll_shop_info:
                if(mShopProductDetailEntity == null){
                    break;
                }
                if(mShopProductDetailEntity.getBusiness().getUrl() != null && !TextUtils.isEmpty(mShopProductDetailEntity.getBusiness().getUrl())){
                    CommonWebActivity.star(this.getActivity(),mShopProductDetailEntity.getBusiness().getUrl());
                }
                break;
        }
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_shop_fund_detail;
    }

}
