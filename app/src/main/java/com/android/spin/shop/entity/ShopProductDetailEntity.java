package com.android.spin.shop.entity;

import com.android.base.mvp.entity.IEntity;

/**
 * 作者：yangqiyun
 * 时间：2017/9/26
 * 1120389276@qq.com
 * 描述：
 */

public class ShopProductDetailEntity implements IEntity{

    /**
     * id : 13
     * business_id : 7
     * name : Mrs. Viva Shanahan
     * front_cover : http://lorempixel.com/160/120/?30314
     * images : http://lorempixel.com/640/480/?22131,http://lorempixel.com/640/480/?85971
     * stock : 5798
     * current_stock : 35
     * start_time : 1504614379
     * end_time : 1505564779
     * exchange_deadline : 1505564779
     * description : Non sed ullam temporibus optio illum facilis. In soluta facere eos totam magni. Sit iste fuga molestiae quia ut in.
     Quae quidem voluptatem earum nesciunt est est. Incidunt dolor quo iste eos. Possimus sed quasi velit in ut.
     Temporibus est tempore aut facere aliquam similique qui. Et culpa et et saepe molestiae dicta aut aut. Debitis sint aut fuga.
     Molestiae ratione voluptatum necessitatibus vel. Placeat aut vel maiores. Animi quia quis esse est natus magnam.
     * contact_phone : 87940091
     * location : 9056 Krajcik Hills
     Larryville, KS 49346
     * status : 0
     * created_at : 2017-09-06 20:26:19
     * updated_at : 2017-09-06 20:26:19
     * business : {"id":7,"name":"Lamar Auer","avatar":"http://lorempixel.com/160/120/?95366"}
     */

    private int id;
    private int business_id;
    private String name;
    private String front_cover;
    private String images;
    private int stock;
    private int current_stock;
    private int start_time;
    private int end_time;
    private int exchange_deadline;
    private String description;
    private String contact_phone;
    private String location;
    private int status;
    private String created_at;
    private String updated_at;
    private BusinessBean business;

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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCurrent_stock() {
        return current_stock;
    }

    public void setCurrent_stock(int current_stock) {
        this.current_stock = current_stock;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public int getExchange_deadline() {
        return exchange_deadline;
    }

    public void setExchange_deadline(int exchange_deadline) {
        this.exchange_deadline = exchange_deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public BusinessBean getBusiness() {
        return business;
    }

    public void setBusiness(BusinessBean business) {
        this.business = business;
    }

    public static class BusinessBean implements IEntity{
        /**
         * id : 7
         * name : Lamar Auer
         * avatar : http://lorempixel.com/160/120/?95366
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
}
