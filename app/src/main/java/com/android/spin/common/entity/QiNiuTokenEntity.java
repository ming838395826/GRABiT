package com.android.spin.common.entity;

import com.android.base.mvp.entity.IEntity;

/**
 * 作者：yangqiyun
 * 时间：2017/9/27
 * 1120389276@qq.com
 * 描述：
 */

public class QiNiuTokenEntity implements IEntity{

    private String upload_token;

    private String cdn_host;

    public String getCdn_host() {
        return cdn_host;
    }

    public void setCdn_host(String cdn_host) {
        this.cdn_host = cdn_host;
    }

    public String getUpload_token() {
        return upload_token;
    }

    public void setUpload_token(String upload_token) {
        this.upload_token = upload_token;
    }
}
