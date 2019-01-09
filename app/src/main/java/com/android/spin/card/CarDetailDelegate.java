package com.android.spin.card;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class CarDetailDelegate extends MvpDelegate<IView, ShopPresenter>  {


    @Bind(R.id.img_card_avatar)
    TImageView mImgCardAvatar;
    @Bind(R.id.ttv_card_title)
    TTextView mTtvCardTitle;
    @Bind(R.id.ttv_card_content)
    TTextView mTtvCardContent;
    @Bind(R.id.img_shop_avatar)
    CircleImageView mImgShopAvatar;
    @Bind(R.id.ttv_shop_name)
    TTextView mTtvShopName;
    @Bind(R.id.ttv_code)
    TTextView mTtvCode;
    @Bind(R.id.iv_line)
    ImageView mIvLine;
    @Bind(R.id.ttv_card_shop_date)
    TTextView mTtvCardShopDate;
    @Bind(R.id.ttv_card_exchange_addr)
    TTextView mTtvCardExchangeAddr;
    @Bind(R.id.ll_card_bottom)
    LinearLayout mLlCardBottom;
    private ShopProductDetailEntity mShopProductDetailEntity;

    @Override
    public void onFail(String code, int type) {
        if (Constant.FAIL_GET_AGAIN_CODE.equals(code)) {
            //已抢到
//            setVisibility(ttvStatus, View.VISIBLE);
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

    private int getShopItemId() {
        return getActivity().getIntent().getIntExtra("id", 0);
    }

    @Override
    public void initWidget() {


        initData();

    }

    private void initData(ShopProductDetailEntity entity) {
        try {
            this.mShopProductDetailEntity = entity;
            if (entity != null) {
                GlideUtil.defaultLoad(this.getActivity(), entity.getFront_cover(), mImgCardAvatar);
                mTtvCardTitle.setText(entity.getName());
                GlideUtil.defaultLoad(this.getActivity(), entity.getBusiness().getAvatar(), mImgShopAvatar);
                mTtvShopName.setText(entity.getBusiness().getName());
                mTtvCardExchangeAddr.setText(getString(R.string.text_home_garb_address)+" : "+entity.getLocation());
                mTtvCardShopDate.setText(getString(R.string.text_card_expiration_date) + "："+entity.getContact_phone());
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

//    @OnClick({R.id.timg_back, R.id.ll_shop_info})
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.timg_back:
//                finish();
//                break;
//            case R.id.ll_shop_info:
//                if (mShopProductDetailEntity == null) {
//                    break;
//                }
//                if (mShopProductDetailEntity.getBusiness().getUrl() != null && !TextUtils.isEmpty(mShopProductDetailEntity.getBusiness().getUrl())) {
//                    CommonWebActivity.star(this.getActivity(), mShopProductDetailEntity.getBusiness().getUrl());
//                }
//                break;
//        }
//    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_card_detail;
    }

}
