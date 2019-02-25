package com.ming.grabit.shop.presenter;

import com.android.base.callback.impl.OnNetRequestImplListener;
import com.android.base.module.http.callback.ShowApiListResponse;
import com.android.base.mvp.persenrter.MvpPresenter;
import com.android.base.mvp.view.IView;
import com.ming.grabit.card.model.CardModel;
import com.ming.grabit.home.entity.NoticeResult;
import com.ming.grabit.shop.entity.RecevierResultEntity;
import com.ming.grabit.shop.entity.SetNoticeResultEntity;
import com.ming.grabit.shop.entity.ShopProductDetailEntity;
import com.ming.grabit.shop.entity.ShopProductItemEntity;
import com.ming.grabit.shop.model.ShopModel;

import java.util.Map;

/**
 * 作者：yangqiyun
 * 时间：2017/9/8
 * 1120389276@qq.com
 * 描述：
 */

public class ShopPresenter extends MvpPresenter<IView> {

    private ShopModel mShopModel = new ShopModel();

    private CardModel mCardModel= new CardModel();

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
        mShopModel.getShopHistory(params, new OnNetRequestImplListener<ShowApiListResponse<ShopProductItemEntity>>(getView(), type) {
            @Override
            public void onSuccess(ShowApiListResponse<ShopProductItemEntity> data) {
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
        getCardModel().postUserCoupons(params, new OnNetRequestImplListener<RecevierResultEntity>(getView(), type) {
            @Override
            public void onSuccess(RecevierResultEntity data) {
                super.onSuccess(data);
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
