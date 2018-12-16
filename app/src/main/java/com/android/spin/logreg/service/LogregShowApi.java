package com.android.spin.logreg.service;

import com.android.base.module.http.callback.ShowApiResponse;
import com.android.spin.logreg.api.LogregApiInterface;
import com.android.spin.common.entity.LoginResultEntity;
import com.android.spin.logreg.entity.RegisterResultEntity;
import com.android.spin.mine.entity.UserEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by YANGQIYUN on 2017/4/6.
 */

public interface LogregShowApi<T> {
    /**
     * 注册
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @FormUrlEncoded
    @POST(LogregApiInterface.DO_REGISET_URL)
    Observable<ShowApiResponse<RegisterResultEntity>> doRegister(@Header("Cache-Control") String cacheControl,
                                                                 @FieldMap Map<String,Object> map);

    /**
     * 登陆
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @FormUrlEncoded
    @POST(LogregApiInterface.DO_AUTH_LOGIN_URL)
    Observable<ShowApiResponse<RegisterResultEntity>> doLogin(@Header("Cache-Control") String cacheControl,
                                                    @FieldMap Map<String,Object> map);

    /**
     * 登陆
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @FormUrlEncoded
    @POST(LogregApiInterface.DO_AUTH_FB_LOGIN_URL)
    Observable<ShowApiResponse<RegisterResultEntity>> doFbLogin(@Header("Cache-Control") String cacheControl,
                                                              @FieldMap Map<String,Object> map);

    /**
     * 退出登陆
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @FormUrlEncoded
    @POST(LogregApiInterface.DO_AUTH_LOGOUT_URL)
    Observable<ShowApiResponse<UserEntity>> doLogout(@Header("Cache-Control") String cacheControl,
                                                    @FieldMap Map<String,Object> map);

    /**
     * 重设密码
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @FormUrlEncoded
    @POST(LogregApiInterface.DO_AUTH_RESET_PWD_URL)
    Observable<ShowApiResponse<Object>> doResetPwd(@Header("Cache-Control") String cacheControl,
                                                     @FieldMap Map<String,Object> map);
}
