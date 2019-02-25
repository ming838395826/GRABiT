package com.ming.grabit.util.string;

/**
 * 作者：yangqiyun
 * 时间：2017/9/28
 * 1120389276@qq.com
 * 描述：
 */

public class FormatStringUtil {

    public static String getPhone(String phone) {
//        try {
//            StringBuilder sb = new StringBuilder(phone);
//            sb.insert(3, "-");
//            return sb.toString();
//        } catch (Exception e) {
//
//        }
//
//        return null;
        if(phone == null || phone.length() == 0){
            return null;
        }
        return "+852 " + phone;
    }
}
