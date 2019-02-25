package com.ming.grabit.shop.entity;

import com.android.base.mvp.entity.IEntity;

/**
 * 作者：yangqiyun
 * 时间：2017/9/26
 * 1120389276@qq.com
 * 描述：
 */

public class ShopHistroyItemEntity implements IEntity{

    /**
     * id : 1
     * business_id : 2
     * name : Moshe Koch
     * front_cover : http://lorempixel.com/160/120/?71473
     * business : {"id":2,"name":"Isabella Mayer","avatar":"http://lorempixel.com/160/120/?52486"}
     */

    private int id;
    private int business_id;
    private String name;
    private String front_cover;
    private BusinessBean business;
    private UserCoupon user_coupon;

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    private long start_time;

    public UserCoupon getUser_coupon() {
        return user_coupon;
    }

    public void setUser_coupon(UserCoupon user_coupon) {
        this.user_coupon = user_coupon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFront_cover() {
        return front_cover;
    }

    public void setFront_cover(String front_cover) {
        this.front_cover = front_cover;
    }

    public BusinessBean getBusiness() {
        return business;
    }

    public void setBusiness(BusinessBean business) {
        this.business = business;
    }

    public static class BusinessBean implements IEntity{
        /**
         * id : 2
         * name : Isabella Mayer
         * avatar : http://lorempixel.com/160/120/?52486
         */

        private int id;
        private String name;
        private String avatar;
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public static class UserCoupon implements IEntity{

        /**
         * id : 124
         * name : Mac 麦金塔电脑
         * item_id : 69
         * status : 2
         * created_at : 2017-11-01 17:16:12
         */

        private int id;
        private String name;
        private int item_id;
        private int status;
        private String created_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getItem_id() {
            return item_id;
        }

        public void setItem_id(int item_id) {
            this.item_id = item_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}
