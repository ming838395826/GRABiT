package com.android.spin.logreg.entity;

import com.android.base.mvp.entity.IEntity;

import java.io.Serializable;

/**
 * 作者：yangqiyun
 * 时间：2017/9/7
 * 1120389276@qq.com
 * 描述：
 */

public class RegisterResultEntity implements IEntity {

    /**
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJndWFyZCI6ImFwaSIsImlkIjo2Miwic3ViIjo2MiwiaXNzIjoiaHR0cHM6Ly9hcGkuc3Bpbi1oay5jb20vYXV0aC9yZWdpc3RlciIsImlhdCI6MTUwNDc1NTc0OCwiZXhwIjoxNTM2MjkxNzQ4LCJuYmYiOjE1MDQ3NTU3NDgsImp0aSI6IjNMc0lrUU9xSTVmNzZhNG0ifQ.vSYQBg28vsxzLwRNLJwFxS5SbSBlE5JJL2l5zb9_HmY
     * user : {"name":"123123","phone":"91234567","id":62}
     */

    private String token;
    private UserBean user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean implements Serializable{
        /**
         * name : 123123
         * phone : 91234567
         * id : 62
         */

        private String name;
        private String phone;
        private String id;
        private String fb_openid;
        private long birthday;
        private String spin_money;
        private String addressee;
        private String address;
        private String contact_phone;
        private String tags;
        private int gender;//0:未设置,1:男；2：女

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

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
