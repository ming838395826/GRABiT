package com.ming.grabit.common.service;

import com.android.base.module.http.callback.ShowApiResponse;
import com.ming.grabit.common.api.CommonApiInterface;
import com.ming.grabit.common.entity.GetPhoneResultEntity;
import com.ming.grabit.common.entity.QiNiuTokenEntity;
import com.ming.grabit.common.entity.SendSmsResultEntity;

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

public interface CommonShowApi<T> {
    /**
     * 获取手机是否已存在
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @GET(CommonApiInterface.AUTH_PHONE_URL)
    Observable<ShowApiResponse<GetPhoneResultEntity>> getIsExistPhone(@Header("Cache-Control") String cacheControl,
                                                                      @QueryMap Map<String, Object> map);

    /**
     * 退出登陆
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @FormUrlEncoded
    @POST(CommonApiInterface.AUTH_LOGOUT_URL)
    Observable<ShowApiResponse<Object>> doLogout(@Header("Cache-Control") String cacheControl,
                                                                      @FieldMap Map<String, Object> map);

    /**
     * 获取七牛token
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @GET(CommonApiInterface.GET_QINIU_TOKEN_URL)
    Observable<ShowApiResponse<QiNiuTokenEntity>> getQiNiuToken(@Header("Cache-Control") String cacheControl,
                                                                @QueryMap Map<String, Object> map);

    /**
     * 发送验证码
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @GET(CommonApiInterface.DO_SEND_SMS)
    Observable<ShowApiResponse<SendSmsResultEntity>> doSendSms(@Header("Cache-Control") String cacheControl,
                                                               @QueryMap Map<String, Object> map);
}
