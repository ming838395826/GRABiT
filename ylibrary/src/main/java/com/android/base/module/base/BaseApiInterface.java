package com.android.base.module.base;

/**
 * 公共模块接口
 * Created by YANGQIYUN on 2017/4/6.
 */

public interface BaseApiInterface {

    /**
     * 获取questtoken
     * */
    String GET_REQUEST_TOKEN_URL = "api/getRequestToken.json";

    /**
     * 获取sessiontoken
     * */
    String GET_SESSION_TOKEN_URL = "api/getSessionToken.json";

    /**
     * 获取服务器APP版本信息
     */
    String GET_WEB_APP_VERSION_INFO = "api/checkLatestVersion.json";



}
