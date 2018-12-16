package com.android.spin.logreg.api;

/**
 * 登录注册模块接口
 * Created by YANGQIYUN on 2017/4/6.
 */

public interface LogregApiInterface {

    /**
     * 登录
     * */
    String DO_AUTH_LOGIN_URL = "auth/login";

    /**
     * facebook登录
     * */
    String DO_AUTH_FB_LOGIN_URL = "auth/fb_login";

    /**
     * 注册
     */
    String DO_REGISET_URL = "auth/register";

    /**
     * 退出
     */
    String DO_AUTH_LOGOUT_URL = "auth/logout";


    /**
     * 重设密码
     */
    String DO_AUTH_RESET_PWD_URL = "auth/reset_password";
}
