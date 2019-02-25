package com.ming.grabit.common.entity;

import com.android.base.mvp.entity.IEntity;

/**
 * 作者：yangqiyun
 * 时间：2017/9/29
 * 1120389276@qq.com
 * 描述：
 */

public class SendSmsResultEntity implements IEntity {

    private long expried;

    public SendSmsResultEntity(){

    }

    public long getExpried() {
        return expried;
    }

    public void setExpried(long expried) {
        this.expried = expried;
    }
}
