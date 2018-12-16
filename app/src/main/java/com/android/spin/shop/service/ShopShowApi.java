package com.android.spin.shop.service;

import com.android.base.module.http.callback.ShowApiListResponse;
import com.android.base.module.http.callback.ShowApiResponse;
import com.android.spin.home.entity.NoticeResult;
import com.android.spin.mine.api.MineApiInterface;
import com.android.spin.mine.entity.UserEntity;
import com.android.spin.shop.api.ShopApiInterface;
import com.android.spin.shop.entity.SetNoticeResultEntity;
import com.android.spin.shop.entity.ShopHistroyItemEntity;
import com.android.spin.shop.entity.ShopItemReceivedEntity;
import com.android.spin.shop.entity.ShopProductDetailEntity;
import com.android.spin.shop.entity.ShopProductItemEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by YANGQIYUN on 2017/4/6.
 */

public interface ShopShowApi<T> {

    /**
     * 获取抢购中的商品
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @GET(ShopApiInterface.GET_SHOP_ITEMS_CURRENT_URL)
    Observable<ShowApiResponse<ShowApiListResponse<ShopProductItemEntity>>> getShopCurrent(@Header("Cache-Control") String cacheControl,
                                                                                           @QueryMap Map<String, Object> map);

    /**
     * 获取即将开抢的商品
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @GET(ShopApiInterface.GET_SHOP_ITEMS_COMING_URL)
    Observable<ShowApiResponse<ShowApiListResponse<ShopProductItemEntity>>> getShopComing(@Header("Cache-Control") String cacheControl,
                                                          @QueryMap Map<String, Object> map);

    /**
     * 获取历史的商品
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @GET(ShopApiInterface.GET_SHOP_ITEMS_HISTORY_URL)
    Observable<ShowApiResponse<ShowApiListResponse<ShopHistroyItemEntity>>> getShopHistory(@Header("Cache-Control") String cacheControl,
                                                                                           @QueryMap Map<String, Object> map);

    /**
     * 判断是否抢购过该商品
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @GET(ShopApiInterface.GET_SHOP_ITEMS_RECEIVED_URL)
    Observable<ShowApiResponse<ShopItemReceivedEntity>> getShopItemReceived(@Header("Cache-Control") String cacheControl,
                                                                            @Path("ID") String ID,
                                                                            @QueryMap Map<String, Object> map);

    /**
     * 获取抢购商品的详情
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @GET(ShopApiInterface.GET_SHOP_ITEMS_DETAIL_URL)
    Observable<ShowApiResponse<ShopProductDetailEntity>> getShopItemDetail(@Header("Cache-Control") String cacheControl,
                                                                           @Path("ID") String ID,
                                                                           @QueryMap Map<String, Object> map);

    /**
     * 获取抢购商品的详情
     * 兼容
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @GET(ShopApiInterface.GET_SHOP_ITEMS_DETAIL_URL)
    Observable<ShowApiResponse<ShopProductItemEntity>> getShopItemDetailNew(@Header("Cache-Control") String cacheControl,
                                                                           @Path("ID") String ID,
                                                                           @QueryMap Map<String, Object> map);

    /**
     * 领取优惠券
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @FormUrlEncoded
    @GET(ShopApiInterface.GET_COUPONS_URL)
    Observable<ShowApiResponse<UserEntity>> getCoupon(@Header("Cache-Control") String cacheControl,
                                                              @Path("ID") String ID,
                                                              @FieldMap Map<String, Object> map);

    /**
     * 获取我的优惠券
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @FormUrlEncoded
    @GET(ShopApiInterface.GET_MINE_COUPONS_URL)
    Observable<ShowApiResponse<UserEntity>> getCouponList(@Header("Cache-Control") String cacheControl,
                                                              @Path("ID") String ID,
                                                              @FieldMap Map<String, Object> map);
    /**
     * 获取我的优惠券统计
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @FormUrlEncoded
    @GET(ShopApiInterface.GET_MINE_COUPONS_SUMMARY_URL)
    Observable<ShowApiResponse<UserEntity>> getMineCouponsSummary(@Header("Cache-Control") String cacheControl,
                                                          @FieldMap Map<String, Object> map);

    /**
     * 设置抢购提醒
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @FormUrlEncoded
    @POST(ShopApiInterface.POST_SHOP_NOTICE_URL)
    Observable<ShowApiResponse<SetNoticeResultEntity>> postShopNotice(@Header("Cache-Control") String cacheControl,
                                                                      @Path("ID") String ID,
                                                                      @FieldMap Map<String, Object> map);

    /**
     * 获取公告
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @GET(ShopApiInterface.GET_CONFIGS_NOTICE_URL)
    Observable<ShowApiResponse<NoticeResult>> getConfigsNotice(@Header("Cache-Control") String cacheControl,
                                                               @QueryMap Map<String, Object> map);
}
