package com.android.spin.mine.entity;

import com.android.base.module.http.callback.ShowApiResponse;

/**
 * 作者：yangqiyun
 * 时间：2017/9/22
 * 1120389276@qq.com
 * 描述：
 */

public class TestEntity extends ShowApiResponse{

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }

    private Object data;


}
