package com.android.spin.shop.presenter;

import com.alibaba.fastjson.JSONObject;
import com.android.base.callback.impl.OnNetRequestImplListener;
import com.android.base.module.http.callback.ShowApiListResponse;
import com.android.base.mvp.persenrter.MvpPresenter;
import com.android.base.mvp.view.IView;
import com.android.spin.card.model.CardModel;
import com.android.spin.common.model.CommonModel;
import com.android.spin.db.UserManager;
import com.android.spin.home.entity.NoticeResult;
import com.android.spin.mine.entity.UserEntity;
import com.android.spin.mine.model.MineModel;
import com.android.spin.shop.entity.SetNoticeResultEntity;
import com.android.spin.shop.entity.ShopHistroyItemEntity;
import com.android.spin.shop.entity.ShopProductDetailEntity;
import com.android.spin.shop.entity.ShopProductItemEntity;
import com.android.spin.shop.model.ShopModel;
import com.orhanobut.logger.Logger;

import java.util.Map;

/**
 * 作者：yangqiyun
 * 时间：2017/9/8
 * 1120389276@qq.com
 * 描述：
 */

public class ShopPresenter extends MvpPresenter<IView> {

    private ShopModel mShopModel = new ShopModel();

    private CardModel mCardModel;

    private CardModel getCardModel(){
        if(mCardModel == null){
            mCardModel = new CardModel();
        }
        return mCardModel;
    }

    /**
     * 获取抢购中的商品
     */
    public void getShopCurrent(Map<String, Object> params, final int type) {
        mShopModel.getShopCurrent(params, new OnNetRequestImplListener<ShowApiListResponse<ShopProductItemEntity>>(getView(), type) {
            @Override
            public void onSuccess(ShowApiListResponse<ShopProductItemEntity> data) {
                super.onSuccess(data);

            }
        });
    }

    /**
     * 获取即将开抢的商品
     */
    public void getShopComing(Map<String, Object> params, final int type) {
        mShopModel.getShopComing(params, new OnNetRequestImplListener<ShowApiListResponse<ShopProductItemEntity>>(getView(), type) {
            @Override
            public void onSuccess(ShowApiListResponse<ShopProductItemEntity> data) {
                //基础数据持久化
                super.onSuccess(data);
            }
        });
    }

    /**
     * 获取历史商品
     */
    public void getShopHistory(Map<String, Object> params, final int type) {
        mShopModel.getShopHistory(params, new OnNetRequestImplListener<ShowApiListResponse<ShopHistroyItemEntity>>(getView(), type) {
            @Override
            public void onSuccess(ShowApiListResponse<ShopHistroyItemEntity> data) {
                //基础数据持久化
                super.onSuccess(data);
            }
        });
    }

    /**
     * 获取商品详情
     */
    public void getShopItemDetail(Map<String, Object> params, final int type) {
        mShopModel.getShopItemDetail(params, new OnNetRequestImplListener<ShopProductDetailEntity>(getView(), type) {
            @Override
            public void onSuccess(ShopProductDetailEntity data) {
                super.onSuccess(data);
            }
        });
    }


    /**
     * 获取商品详情
     * 兼容接口
     */
    public void getShopItemDetailNew(Map<String, Object> params, final int type) {
        mShopModel.getShopItemDetailNew(params, new OnNetRequestImplListener<ShopProductItemEntity>(getView(), type) {
            @Override
            public void onSuccess(ShopProductItemEntity data) {
                super.onSuccess(data);
            }
        });
    }

    /**
     * 设置提醒
     */
    public void postShopNotice(Map<String, Object> params, final int type) {
        mShopModel.postShopNotice(params, new OnNetRequestImplListener<SetNoticeResultEntity>(getView(), type) {
            @Override
            public void onSuccess(SetNoticeResultEntity data) {
                super.onSuccess(data);
            }
        });
    }


    /**
     * 领取优惠券
     */
    public void postUserCoupons(Map<String,Object> params, final int type) {
        getCardModel().postUserCoupons(params, new OnNetRequestImplListener<Object>(getView(), type) {
            @Override
            public void onSuccess(Object data) {
                super.onSuccess(data);
                Logger.d(data);
            }
        });
    }

    /**
     * 获取首页公告
     */
    public void getConfigsNotice(Map<String,Object> params, final int type) {
        mShopModel.getConfigsNotice(params, new OnNetRequestImplListener<NoticeResult>(getView(), type) {
            @Override
            public void onSuccess(NoticeResult data) {
                super.onSuccess(data);
            }
        });
    }

    /**
     * 判断是否抢过这个商品
     */
    public void getShopItemReceived(Map<String,Object> params, final int type) {
        mShopModel.getShopItemReceived(params, new OnNetRequestImplListener<NoticeResult>(getView(), type) {
            @Override
            public void onSuccess(NoticeResult data) {
                super.onSuccess(data);
            }
        });
    }

    /**
     * 领取优惠券人员
     */
    public void getCardUser(String id, final int type) {
        mShopModel.getCardUser(id, new OnNetRequestImplListener<NoticeResult>(getView(), type) {
            @Override
            public void onSuccess(NoticeResult data) {
                super.onSuccess(data);
            }
        });
    }

    /**
     * 判断是否抢过这个商品
     */
    public void getShopItemReceived(Map<String,Object> params, final int type, final int position) {
        mShopModel.getShopItemReceived(params, new OnNetRequestImplListener<NoticeResult>(getView(), type) {
            @Override
            public void onSuccess(NoticeResult data) {
                data.setPosition(position);
                super.onSuccess(data);
            }
        });
    }

}
