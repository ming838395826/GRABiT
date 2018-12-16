package com.android.spin.common.entity;

import com.android.base.mvp.entity.IEntity;

/**
 * 作者：yangqiyun
 * 时间：2017/9/7
 * 1120389276@qq.com
 * 描述：获取电话号码是否已存在
 */

public class GetPhoneResultEntity implements IEntity {

    /**
     * is_exist : true
     */

    private boolean is_exist;

    public boolean isIs_exist() {
        return is_exist;
    }

    public void setIs_exist(boolean is_exist) {
        this.is_exist = is_exist;
    }
}
