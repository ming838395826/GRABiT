package com.android.spin.logreg.entity;

import com.android.base.mvp.entity.IEntity;
import com.android.spin.mine.entity.UserEntity;

/**
 * 作者：yangqiyun
 * 时间：2017/9/7
 * 1120389276@qq.com
 * 描述：
 */

public class LoginResultEntity implements IEntity {

    private String token;

    private UserEntity user;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
