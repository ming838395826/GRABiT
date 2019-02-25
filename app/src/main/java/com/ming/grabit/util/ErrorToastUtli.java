package com.ming.grabit.util;

import com.android.base.util.ToastUtil;

/**
 * 作者：yangqiyun
 * 时间：2017/9/7
 * 1120389276@qq.com
 * 描述：
 */

public class ErrorToastUtli {

    public static void showErrorToast(String code){

        if(code == null){
            return;
        }

        if("1001".equals(code)){
            ToastUtil.shortShow("用戶不存在或密碼錯誤");
        }
    }
}
