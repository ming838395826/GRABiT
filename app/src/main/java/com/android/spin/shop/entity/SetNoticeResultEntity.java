package com.android.spin.shop.entity;

import java.io.Serializable;

/**
 * 作者：yangqiyun
 * 时间：2017/10/18
 * 1120389276@qq.com
 * 描述：
 */

public class SetNoticeResultEntity implements Serializable{

    /**
     * item_id : 63
     * user_id : 62
     * language : zh-cn
     */

    private String item_id;
    private int user_id;
    private String language;

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
