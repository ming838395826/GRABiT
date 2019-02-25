package com.ming.grabit.shop.api;

/**
 * 登录注册模块接口
 * Created by YANGQIYUN on 2017/4/6.
 */

public interface ShopApiInterface {

    /**
     * 获取抢购中的商品
     * */
    String GET_SHOP_ITEMS_CURRENT_URL = "items/current";
    /**
     * 获取即将开抢的商品
     * */
    String GET_SHOP_ITEMS_COMING_URL = "items/coming";
    /**
     * 获取历史的商品
     * */
    String GET_SHOP_ITEMS_HISTORY_URL = "items/history";
    /**
     * 判断是否抢购过该商品
     * */
    String GET_SHOP_ITEMS_RECEIVED_URL = "items/{ID}/is_received";
    /**
     * 获取抢购商品的详情
     * */
    String GET_SHOP_ITEMS_DETAIL_URL = "items/{ID}";

    /**
     * 领取优惠券
     * */
    String GET_COUPONS_URL = "user_coupons";

    /**
     * 获取个人优惠券
     * */
    String GET_MINE_COUPONS_URL = "user_coupons";
    /**
     * 获取我的优惠券统计
     * */
    String GET_MINE_COUPONS_SUMMARY_URL = "user_coupons/me/summary";

    /**
     * 获取我的优惠券统计
     * */
    String POST_SHOP_NOTICE_URL = "items/{ID}/notice";


    /**
     * 获取首页公告
     * */
    String GET_CONFIGS_NOTICE_URL = "configs/notice";

    /**
     * 获取领取优惠券人员
     * */
    String GET_CARDS_USER = "cards/{id}/users";


}
