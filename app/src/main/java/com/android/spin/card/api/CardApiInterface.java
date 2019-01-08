package com.android.spin.card.api;

/**
 * 卡包模块接口
 * Created by YANGQIYUN on 2017/4/6.
 */

public interface CardApiInterface {

    /**
     * 获取用户优惠券
     * */
    String GET_USER_COUPONS_URL = "/user_coupons";

    /**
     * 删除用户优惠券
     * */
    String DELETE_USER_COUPONS_URL = "/user_coupons/{user_coupon_id}";

    /**
     * 删除用户优惠券
     * */
    String SET_USER_COUPONS_URL = "/user_coupons/{user_coupon_id}/used";

}
