package com.android.spin.card.entity;

import com.android.base.mvp.entity.IEntity;

/**
 * 作者：yangqiyun
 * 时间：2017/9/25
 * 1120389276@qq.com
 * 描述：
 */

public class CardItemEntity implements IEntity{


    /**
     * id : 1
     * user_id : 78
     * item_id : 64
     * business_id : 9
     * name : Merl Wisoky
     * code : 092529894260
     * status : 0
     * expired_time : 1507101151
     * created_at : 2017-09-25 15:27:02
     * updated_at : 2017-09-25 15:27:02
     * business : {"id":9,"name":"Zakary VonRueden"}
     * item : {"id":64,"name":"Merl Wisoky","front_cover":"http://lorempixel.com/160/120/?95895","location":"300 Camden Cove Suite 860\nOkunevahaven, NM 50552-4435"}
     */

    private String id;
    private String user_id;
    private String item_id;
    private int business_id;
    private String name;
    private String code;
    private int status;
    private long expired_time;
    private String created_at;
    private String updated_at;
    private BusinessBean business;
    private ItemBean item;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getExpired_time() {
        return expired_time;
    }

    public void setExpired_time(long expired_time) {
        this.expired_time = expired_time;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public BusinessBean getBusiness() {
        return business;
    }

    public void setBusiness(BusinessBean business) {
        this.business = business;
    }

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public static class BusinessBean implements IEntity{
        /**
         * id : 9
         * name : Zakary VonRueden
         */

        private int id;
        private String name;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        private String avatar;



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
    }

    public static class ItemBean implements IEntity{
        /**
         * id : 64
         * name : Merl Wisoky
         * front_cover : http://lorempixel.com/160/120/?95895
         * location : 300 Camden Cove Suite 860
         Okunevahaven, NM 50552-4435
         */

        private int id;
        private String name;
        private String front_cover;
        private String location;

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

        public String getFront_cover() {
            return front_cover;
        }

        public void setFront_cover(String front_cover) {
            this.front_cover = front_cover;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
