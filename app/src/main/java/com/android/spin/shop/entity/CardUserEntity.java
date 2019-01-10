package com.android.spin.shop.entity;

/**
 * Created by Administrator on 2019/1/10.
 */

public class CardUserEntity {


    /**
     * id : 63
     * user_id : 62
     * card_id : 3
     * item_id : 51
     * business_id : 10
     * name : Rupert Conn
     * code : 011029507879
     * status : 0
     * expired_time : 1547367288
     * created_at : 2019-01-10 18:23:32
     * updated_at : 2019-01-10 18:23:32
     * user : {"id":62,"name":"明","avatar":""}
     */

    private int id;
    private int user_id;
    private int card_id;
    private int item_id;
    private int business_id;
    private String name;
    private String code;
    private int status;
    private int expired_time;
    private String created_at;
    private String updated_at;
    private UserBean user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
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

    public int getExpired_time() {
        return expired_time;
    }

    public void setExpired_time(int expired_time) {
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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * id : 62
         * name : 明
         * avatar :
         */

        private int id;
        private String name;
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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
