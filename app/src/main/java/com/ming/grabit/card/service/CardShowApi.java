package com.ming.grabit.card.service;

import com.android.base.module.http.callback.ShowApiListResponse;
import com.android.base.module.http.callback.ShowApiResponse;
import com.ming.grabit.card.api.CardApiInterface;
import com.ming.grabit.card.entity.CardItemEntity;
import com.ming.grabit.shop.entity.RecevierResultEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.DELETE;
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

public interface CardShowApi<T> {
    /**
     * 获取我们的优惠券
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @GET(CardApiInterface.GET_USER_COUPONS_URL)
    Observable<ShowApiResponse<ShowApiListResponse<CardItemEntity>>> getUserCoupons(@Header("Cache-Control") String cacheControl,
                                                                                    @QueryMap Map<String, Object> map);

    /**
     * 领取优惠券
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @FormUrlEncoded
    @POST(CardApiInterface.GET_USER_COUPONS_URL)
    Observable<ShowApiResponse<RecevierResultEntity>> postUserCoupons(@Header("Cache-Control") String cacheControl,
                                                                      @FieldMap Map<String, Object> map);

    /**
     * 删除优惠券
     * @param cacheControl
     * @param user_coupon_id
     * @return
     */
    @DELETE(CardApiInterface.DELETE_USER_COUPONS_URL)
    Observable<ShowApiResponse<Object>> deleteUserCoupons(@Header("Cache-Control") String cacheControl,
                                                        @Path("user_coupon_id") String user_coupon_id);

    /**
     * 获取优惠券使用
     * @param cacheControl
     * @param user_coupon_id
     * @return
     */
    @PUT(CardApiInterface.SET_USER_COUPONS_URL)
    Observable<ShowApiResponse<Object>> setUserCoupons(@Header("Cache-Control") String cacheControl,
                                                          @Path("user_coupon_id") String user_coupon_id);

}
