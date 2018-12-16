package com.android.base.module.base;

import com.android.base.callback.entity.TokenResultDo;
import com.android.base.module.http.callback.ShowApiResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by YANGQIYUN on 2017/4/6.
 */

public interface BaseShowApi<T> {

    /**
     * 获取sessiontoken
     *
     * @param cacheControl 请求参数
     * @param map          参数集合
     * @return
     */
    @FormUrlEncoded
    @POST(BaseApiInterface.GET_SESSION_TOKEN_URL)
    Observable<ShowApiResponse<TokenResultDo>> getSessionToken(@Header("Cache-Control") String cacheControl,
                                                               @FieldMap Map<String, Object> map);


}
