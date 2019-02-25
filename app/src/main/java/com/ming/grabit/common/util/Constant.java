package com.ming.grabit.common.util;

/**
 * 作者：yangqiyun
 * 时间：2017/9/25
 * 1120389276@qq.com
 * 描述：
 */

public class Constant {

    public static String SCUCCESS_CODE = "0";//成功
    public static String FAIL_DATA_CODE = "1";//数据校验错误
    public static String FAIL_SYSTEM_CODE = "1000";//系统繁忙
    public static String FAIL_LOGIN_CODE = "1001";//账号或者密码错误
    public static String FAIL_TOKEN_CODE = "1002";//token无效
    public static String FAIL_SEND_SMS_CODE = "1003";//验证码发送失败
    public static String FAIL_ADD_GOODS_CODE = "1004";//抢购时间未到
    public static String FAIL_GET_AGAIN_CODE = "1005";//不能重复领取
    public static String FAIL_LOW_STOCKS_CODE = "1006";//库存不足
    public static String WAIT_SEND_SMS_CODE = "1007";//消息已发送，请耐心等待

    public static final String KEY_PAGE = "page";
    public static final String KEY_PER_PAGE = "per_page";

    public static final String HEADER_PUT_CONTENT_TYPE = "content-type:application/x-www-form-urlencoded";//消息已发送，请耐心等待

    public static String URL_ABOUT_GRABIT = "http://api.grabithk.com/docs/关于grabit.html";
    public static String URL_SERVICE_AGREEMENT_CN = "http://api.grabithk.com/docs/grabit-%E6%9C%8D%E5%8B%99%E6%A2%9D%E6%AC%BE-r2.html";
    public static String URL_SERVICE_AGREEMENT_EN = "http://api.grabithk.com/docs/grabit-terms-of-service-r2.html";
}
