package com.ming.grabit.shop.model;

import com.android.base.module.http.callback.ChildSubscriber;
import com.android.base.module.http.callback.OnNetRequestListener;
import com.android.base.module.http.callback.ShowApiListResponse;
import com.android.base.module.http.callback.ShowApiResponse;
import com.android.base.util.SignHelper;
import com.ming.grabit.home.entity.NoticeResult;
import com.ming.grabit.shop.entity.CardUserEntity;
import com.ming.grabit.shop.entity.SetNoticeResultEntity;
import com.ming.grabit.shop.entity.ShopItemReceivedEntity;
import com.ming.grabit.shop.entity.ShopProductDetailEntity;
import com.ming.grabit.shop.entity.ShopProductItemEntity;
import com.ming.grabit.shop.service.ShopRetrofitService;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：yangqiyun
 * 时间：2017/8/2
 * 1120389276@qq.com
 * 描述：
 */

public class ShopModel {

    /**
     * 获取抢购中的商品
     * @param params
     * @param listener
     */
    public void getShopCurrent(Map<String,Object> params, OnNetRequestListener listener){
        Observable<ShowApiResponse<ShowApiListResponse<ShopProductItemEntity>>> observable = ShopRetrofitService.getInstance().
                createShowApi().getShopCurrent(ShopRetrofitService.getCacheControl(),SignHelper.addSign(params));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<ShowApiListResponse<ShopProductItemEntity>>>(listener) {
                });
    }

    /**
     * 获取即将开抢的商品
     * @param params
     * @param listener
     */
    public void getShopComing(Map<String,Object> params, OnNetRequestListener listener){
        Observable<ShowApiResponse<ShowApiListResponse<ShopProductItemEntity>>> observable = ShopRetrofitService.getInstance().
                createShowApi().getShopComing(ShopRetrofitService.getCacheControl(),SignHelper.addSign(params));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<ShowApiListResponse<ShopProductItemEntity>>>(listener) {
                });
    }

    /**
     * 获取历史的商品
     * @param params
     * @param listener
     */
    public void getShopHistory(Map<String,Object> params, OnNetRequestListener listener){
        Observable<ShowApiResponse<ShowApiListResponse<ShopProductItemEntity>>> observable = ShopRetrofitService.getInstance().
                createShowApi().getShopHistory(ShopRetrofitService.getCacheControl(),SignHelper.addSign(params));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<ShowApiListResponse<ShopProductItemEntity>>>(listener) {
                });
    }

    /**
     * 判断是否抢购过该商品
     * @param params
     * @param listener
     */
    public void getShopItemReceived(Map<String,Object> params, OnNetRequestListener listener){
        Observable<ShowApiResponse<ShopItemReceivedEntity>> observable = ShopRetrofitService.getInstance().
                createShowApi().getShopItemReceived(ShopRetrofitService.getCacheControl(),
                params.get("id") + "",SignHelper.addSign(params));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<ShopItemReceivedEntity>>(listener) {
                });
    }

    /**
     * 获取抢购商品的详情
     * @param params
     * @param listener
     */
    public void getShopItemDetailNew(Map<String,Object> params, OnNetRequestListener listener){
        Observable<ShowApiResponse<ShopProductDetailEntity>> observable = ShopRetrofitService.getInstance().
                createShowApi().getShopItemDetailNew(ShopRetrofitService.getCacheControl(),
                params.get("id") + "",SignHelper.addSign(params));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<ShopProductDetailEntity>>(listener) {
                });
    }

    /**
     * 获取抢购商品的详情
     * 兼容
     * @param params
     * @param listener
     */
    public void getShopItemDetail(Map<String,Object> params, OnNetRequestListener listener){
        Observable<ShowApiResponse<ShopProductDetailEntity>> observable = ShopRetrofitService.getInstance().
                createShowApi().getShopItemDetail(ShopRetrofitService.getCacheControl(),
                params.get("id") + "",SignHelper.addSign(params));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<ShopProductDetailEntity>>(listener) {
                });
    }

    /**
     * 设置提醒
     * @param params
     * @param listener
     */
    public void postShopNotice(Map<String,Object> params, OnNetRequestListener listener){
        Observable<ShowApiResponse<SetNoticeResultEntity>> observable = ShopRetrofitService.getInstance().
                createShowApi().postShopNotice(ShopRetrofitService.getCacheControl(),
                params.get("item_id") + "",SignHelper.addSign(params));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<SetNoticeResultEntity>>(listener) {
                });
    }

    /**
     * 获取公告
     * @param params
     * @param listener
     */
    public void getConfigsNotice(Map<String,Object> params, OnNetRequestListener listener){
        Observable<ShowApiResponse<NoticeResult>> observable = ShopRetrofitService.getInstance().
                createShowApi().getConfigsNotice(ShopRetrofitService.getCacheControl(),
               SignHelper.addSign(params));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<NoticeResult>>(listener) {
                });
    }

    /**
     * 获取抢购中的商品
     * @param listener
     */
    public void getCardUser(String id, OnNetRequestListener listener){
        Observable<ShowApiResponse<ShowApiListResponse<CardUserEntity>>> observable = ShopRetrofitService.getInstance().
                createShowApi().getCardUser(ShopRetrofitService.getCacheControl(),id);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<ShowApiListResponse<CardUserEntity>>>(listener) {
                });
    }
}
