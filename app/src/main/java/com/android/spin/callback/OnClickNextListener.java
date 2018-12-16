package com.android.spin.callback;

import android.view.View;

/**
 * 作者：yangqiyun
 * 时间：2017/9/3
 * 1120389276@qq.com
 * 描述：
 */

public interface OnClickNextListener {

    void onClickNext(View view, String phone);

    void onClickNextByCode(View view, String code);

    void onClickNextByPwd(View view, String pwd);
}
