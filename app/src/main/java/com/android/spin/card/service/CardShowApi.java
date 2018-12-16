package com.android.spin.card.service;

import com.android.base.module.http.callback.ShowApiListResponse;
import com.android.base.module.http.callback.ShowApiResponse;
import com.android.spin.card.api.CardApiInterface;
import com.android.spin.card.entity.CardItemEntity;
import com.android.spin.logreg.api.LogregApiInterface;
import com.android.spin.mine.entity.UserEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
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
    Observable<ShowApiResponse<Object>> postUserCoupons(@Header("Cache-Control") String cacheControl,
                                                       @FieldMap Map<String, Object> map);

}
