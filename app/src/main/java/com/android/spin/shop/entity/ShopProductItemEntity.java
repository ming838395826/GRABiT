package com.android.spin.shop.entity;

import com.android.base.mvp.entity.IEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：yangqiyun
 * 时间：2017/9/20
 * 1120389276@qq.com
 * 描述：
 */

public class ShopProductItemEntity implements IEntity{


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
    private long start_time;
    private long end_time;
    private long exchange_deadline;
    private String description;
    private String contact_phone;
    private String location;
    private int status;
    private String created_at;
    private String updated_at;
    private BusinessBean business;
    private List<ShopItemEntity> items=new ArrayList<>();

    private UserCouponBean user_coupon;
    private userItemNoticeBean user_item_notice;

    private Integer isRecerve;//是否已经领取,通知
    private List<CardUserEntity> userList=new ArrayList<>();
    private boolean isShowAll=false;

    public boolean isShowAll() {
        return isShowAll;
    }

    public void setShowAll(boolean showAll) {
        isShowAll = showAll;
    }

    public List<CardUserEntity> getUserList() {
        return userList;
    }

    public void setUserList(List<CardUserEntity> userList) {
        this.userList = userList;
    }

    public Integer getIsRecerve() {
        return isRecerve;
    }

    public void setIsRecerve(Integer isRecerve) {
        this.isRecerve = isRecerve;
    }

    public int getId() {
        return id;
    }

    public UserCouponBean getUser_coupon() {
        return user_coupon;
    }

    public void setUser_coupon(UserCouponBean user_coupon) {
        this.user_coupon = user_coupon;
    }

    public userItemNoticeBean getUser_item_notice() {
        return user_item_notice;
    }

    public void setUser_item_notice(userItemNoticeBean user_item_notice) {
        this.user_item_notice = user_item_notice;
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

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public long getExchange_deadline() {
        return exchange_deadline;
    }

    public void setExchange_deadline(long exchange_deadline) {
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

    public List<ShopItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ShopItemEntity> items) {
        this.items = items;
    }

    public static class BusinessBean implements IEntity{
        /**
         * id : 7
         * name : Lamar Auer
         * avatar : http://lorempixel.com/160/120/?95366
         */

        private String id;
        private String name;
        private String avatar;
        private String url;
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public static class userItemNoticeBean implements IEntity{


        /**
         * user_id : 62
         * item_id : 67
         * language : en
         * created_at : 2017-10-24 14:12:40
         */

        private int user_id;
        private int item_id;
        private String language;
        private String created_at;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getItem_id() {
            return item_id;
        }

        public void setItem_id(int item_id) {
            this.item_id = item_id;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }

    public static class UserCouponBean implements IEntity{

        /**
         * id : 87
         * name : 白鹅绒羽绒被 | 裸睡首选 加拿大抗菌除螨技术
         * item_id : 64
         * status : 0
         * created_at : 2017-10-18 09:35:03
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
