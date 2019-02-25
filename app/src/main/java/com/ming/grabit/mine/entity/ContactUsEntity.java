package com.ming.grabit.mine.entity;

/**
 * Created by ming on 2019/1/6.
 */

public class ContactUsEntity {

    /**
     * key : contact
     * value : {"phone":"","email":""}
     * status : 0
     */

    private String key;
    private ValueBean value;
    private int status;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ValueBean getValue() {
        return value;
    }

    public void setValue(ValueBean value) {
        this.value = value;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class ValueBean {
        /**
         * phone :
         * email :
         */

        private String phone;
        private String email;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
