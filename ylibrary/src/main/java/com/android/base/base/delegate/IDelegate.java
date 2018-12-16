package com.android.base.base.delegate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：YANGQIYUN
 * 时间 2017 06 21
 * 邮箱：1120389276@qq.com
 * 描述：
 */

public interface IDelegate {

    void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    int getOptionsMenuId();

    View getRootView();

    void onDestroy();
    void onResume();
    void onPause();
}
