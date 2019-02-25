package com.ming.grabit.mine.api;

/**
 * 登录注册模块接口
 * Created by YANGQIYUN on 2017/4/6.
 */

public interface MineApiInterface {

    /**
     * 获取用户信息
     * */
    String GET_USER_INFO_URL = "users/me";
    /**
     * 获取用户信息
     * */
    String PUT_USER_INFO_URL = "users/{userId}";

    /**
     * 绑定facebook
     * */
    String PUT_FB_BINDING_URL = "auth/fb_binding";

    /**
     * 解绑facebook
     * */
    String PUT_FB_UNBIND_URL = "auth/fb_unbind";

    /**
     * 验证手机号码
     * */
    String POST_VERIFY_USER_URL = "auth/verify_user";

    /**
     * 更换手机号码
     * */
    String POST_CHANGE_PHONE_URL = "auth/change_phone";

    /**
     * 获取联系信息
     * */
    String getContactUsInfo = "configs/contact";

    /**
     * 意见反馈
     * */
    String feedback= "feedback";

}
