package com.android.spin.common.entity;

import com.android.spin.logreg.entity.RegisterQuestEntity;

import java.io.Serializable;

/**
 * 作者：yangqiyun
 * 时间：2017/8/2
 * 1120389276@qq.com
 * 描述：
 */

public class LoginResultEntity implements Serializable{

    private String token;
    private RegisterQuestEntity user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RegisterQuestEntity getUser() {
        return user;
    }

    public void setUser(RegisterQuestEntity user) {
        this.user = user;
    }
}
