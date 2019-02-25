package com.ming.grabit.mine.service;

import com.android.base.module.http.callback.ShowApiResponse;
import com.ming.grabit.common.util.Constant;
import com.ming.grabit.mine.api.MineApiInterface;
import com.ming.grabit.mine.entity.ContactUsEntity;
import com.ming.grabit.mine.entity.UserEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by YANGQIYUN on 2017/4/6.
 */

public interface MineShowApi<T> {

    /**
     * 获取用户信息
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @GET("/users/me")
    Observable<ShowApiResponse<UserEntity>> getUserInfo(@Header("Cache-Control") String cacheControl,
                                                        @QueryMap Map<String, Object> map);

    /**
     * 更新用户信息
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @Headers({Constant.HEADER_PUT_CONTENT_TYPE})
    @FormUrlEncoded
    @PUT(MineApiInterface.PUT_USER_INFO_URL)
    Observable<ShowApiResponse<UserEntity>> putUserInfo(@Header("Cache-Control") String cacheControl,
                                                        @Path("userId") String userId,
                                                        @FieldMap Map<String, Object> map);

    /**
     * 更换手机号码
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @FormUrlEncoded
    @POST(MineApiInterface.POST_CHANGE_PHONE_URL)
    Observable<ShowApiResponse<Object>> doChangePhone(@Header("Cache-Control") String cacheControl,
                                                         @FieldMap Map<String, Object> map);

    /**
     * 绑定facebook
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @FormUrlEncoded
    @POST(MineApiInterface.PUT_FB_BINDING_URL)
    Observable<ShowApiResponse<Object>> doFacebookBind(@Header("Cache-Control") String cacheControl,
                                                        @FieldMap Map<String, Object> map);

    /**
     * 解绑facebook
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @FormUrlEncoded
    @POST(MineApiInterface.PUT_FB_UNBIND_URL)
    Observable<ShowApiResponse<Object>> doFacebookUnBind(@Header("Cache-Control") String cacheControl,
                                                       @FieldMap Map<String, Object> map);

    /**
     * 验证手机号码
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @FormUrlEncoded
    @POST(MineApiInterface.POST_VERIFY_USER_URL)
    Observable<ShowApiResponse<Object>> doVerifyPhone(@Header("Cache-Control") String cacheControl,
                                                         @FieldMap Map<String, Object> map);

    /**
     * 获取联系信息
     * @param cacheControl
     * @return
     */
    @GET(MineApiInterface.getContactUsInfo)
    Observable<ShowApiResponse<ContactUsEntity>> getContactUsInfo(@Header("Cache-Control") String cacheControl);

    /**
     * 意见反馈
     * @param cacheControl
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(MineApiInterface.feedback)
    Observable<ShowApiResponse<Object>> feedback(@Header("Cache-Control") String cacheControl,
                                                       @FieldMap Map<String, Object> map);

}
