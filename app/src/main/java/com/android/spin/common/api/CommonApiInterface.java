package com.android.spin.common.api;

/**
 * 登录注册模块接口
 * Created by YANGQIYUN on 2017/4/6.
 */

public interface CommonApiInterface {

    /**
     * 验证手机号码是否存在
     * */
    String AUTH_PHONE_URL = "auth/is_phone_exist";

    /**
     * 退出登陆
     * */
    String AUTH_LOGOUT_URL = "auth/logout";

    /**
     * 获取七牛token
     * */
    String GET_QINIU_TOKEN_URL = "/common/upload_token";

    /**
     * 发送验证码
     * */
    String DO_SEND_SMS = "/common/sms";
}
