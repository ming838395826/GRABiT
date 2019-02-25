package com.ming.grabit.mine.entity;

import com.ming.grabit.logreg.entity.RegisterQuestEntity;

/**
 * 作者：yangqiyun
 * 时间：2017/8/2
 * 1120389276@qq.com
 * 描述：用户信息
 */

public class UserEntity extends RegisterQuestEntity {

    private String id;
    private String fb_openid;
    private long birthday;
    private String spin_money;
    private String addressee;
    private String address;
    private String contact_phone;
    private String tags;
    private int gender;//0:未设置,1:男；2：女


    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFb_openid() {
        return fb_openid;
    }

    public void setFb_openid(String fb_openid) {
        this.fb_openid = fb_openid;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getSpin_money() {
        return spin_money;
    }

    public void setSpin_money(String spin_money) {
        this.spin_money = spin_money;
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public boolean isMan(){
        if(1 == gender){
            return true;
        }
        return false;
    }

    public boolean isWomen(){
        if(2 == gender){
            return true;
        }
        return false;
    }
}
