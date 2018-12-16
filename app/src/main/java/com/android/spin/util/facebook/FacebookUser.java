package com.android.spin.util.facebook;

import java.io.Serializable;

/**
 * 作者：yangqiyun
 * 时间：2017/10/11
 * 1120389276@qq.com
 * 描述：
 */

public class FacebookUser implements Serializable{

    private String id;
    private String name;
    private String gender;
    private String emali;
    private String avatar;

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    private long birthday;



    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    private String locale;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmali() {
        return emali;
    }

    public void setEmali(String emali) {
        this.emali = emali;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isMale(){
        if("male".equals(gender)){
            return true;
        }
        return false;
    }

    public boolean isZH(){
        return false;
    }
}
