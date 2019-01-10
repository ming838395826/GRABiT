package com.android.spin.home.entity;

import com.android.base.mvp.entity.IEntity;

/**
 * 作者：yangqiyun
 * 时间：2017/10/12
 * 1120389276@qq.com
 * 描述：
 */

public class NoticeResult implements IEntity {

    /**
     * key : notice
     * value : {"img":"http://owkpbbfyj.bkt.clouddn.com/o_1bs885ivm1u6r1rj81m571dtp12qc9.jpg","url":"https://google.com"}
     * status : 0
     * created_at : 2017-10-11 11:47:40
     * updated_at : 2017-10-12 12:16:12
     */

    private String key;
    private ValueBean value;
    private int status;
    private String created_at;
    private String updated_at;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

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

    public static class ValueBean {
        /**
         * img : http://owkpbbfyj.bkt.clouddn.com/o_1bs885ivm1u6r1rj81m571dtp12qc9.jpg
         * url : https://google.com
         */

        private String img;
        private String url;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
