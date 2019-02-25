package com.ming.grabit.card.presenter;

import com.android.base.callback.impl.OnNetRequestImplListener;
import com.android.base.module.http.callback.ShowApiListResponse;
import com.android.base.mvp.persenrter.MvpPresenter;
import com.android.base.mvp.view.IView;
import com.ming.grabit.card.entity.CardItemEntity;
import com.ming.grabit.card.model.CardModel;

import java.util.Map;

/**
 * 作者：yangqiyun
 * 时间：2017/8/2
 * 1120389276@qq.com
 * 描述：
 */

public class CardPresenter extends MvpPresenter<IView> {

    private CardModel mCardModel = new CardModel();

    /**
     * 获取我的优惠券
     */
    public void getUserCoupons(Map<String,Object> params, final int type) {
        mCardModel.getUserCoupons(params, new OnNetRequestImplListener<ShowApiListResponse<CardItemEntity>>(getView(), type) {
            @Override
            public void onSuccess(ShowApiListResponse<CardItemEntity> data) {
                super.onSuccess(data);
            }
        });
    }

    /**
     * 领取优惠券
     */
    public void postUserCoupons(Map<String,Object> params, final int type) {
        mCardModel.postUserCoupons(params, new OnNetRequestImplListener<Object>(getView(), type) {
            @Override
            public void onSuccess(Object data) {
                super.onSuccess(data);
            }
        });
    }

    /**
     * 删除优惠券
     */
    public void deleteUserCoupons(String user_coupon_id,  final int type,int position) {
        mCardModel.deleteUserCoupons(user_coupon_id, new OnNetRequestImplListener<Object>(getView(), type) {
            @Override
            public void onSuccess(Object data) {
                super.onSuccess(data);
            }
        });
    }

    /**
     * 设置优惠券
     */
    public void setUserCoupons(String user_coupon_id,  final int type,int position) {
        mCardModel.setUserCoupons(user_coupon_id, new OnNetRequestImplListener<Object>(getView(), type) {
            @Override
            public void onSuccess(Object data) {
                super.onSuccess(data);
            }
        });
    }
}
