package com.ming.grabit.logreg.entity;

import java.io.Serializable;

/**
 * 作者：yangqiyun
 * 时间：2017/7/21
 * 1120389276@qq.com
 * 描述：
 */

public class RegisterQuestEntity implements Serializable{

    private String name;

    private String phone;

    private String password;

    private String code;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private String avatar;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
