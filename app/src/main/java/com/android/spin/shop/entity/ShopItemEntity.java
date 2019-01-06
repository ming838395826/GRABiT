package com.android.spin.shop.entity;

/**
 * Created by ming on 2019/1/6.
 */

public class ShopItemEntity {


    /**
     * id : 45
     * business_id : 1
     * tag : 0
     * name : Matt Mayert
     * front_cover : http://lorempixel.com/160/120/?52669
     * images : http://lorempixel.com/640/480/?78344,http://lorempixel.com/640/480/?71477
     * stock : 1054
     * current_stock : 5
     * exchange_deadline : 1547280888
     * exchange_count : 0
     * description : Libero et et omnis fugit vero. Iure nesciunt laborum aliquam iure voluptas quo. Nemo et voluptates quibusdam commodi. Provident velit inventore est aut repellendus in.
     Aspernatur qui hic dolor. Modi est repellat quis aliquam rerum explicabo tempore culpa. Molestias delectus amet inventore odit et. Natus similique libero est nam culpa qui neque nisi.
     Quaerat et explicabo provident. Officia suscipit velit dicta et voluptate. Quo est ad deleniti aut eum.
     * contact_phone : 82010032
     * location : 125 Eloisa Haven
     Pacochabury, NC 16246
     * url :
     * status : 0
     * created_at : 2019-01-04 16:14:48
     * updated_at : 2019-01-04 16:14:48
     */

    private String id;
    private String business_id;
    private int tag;
    private String name;
    private String front_cover;
    private String images;
    private int stock;
    private int current_stock;
    private int exchange_deadline;
    private int exchange_count;
    private String description;
    private String contact_phone;
    private String location;
    private String url;
    private int status;
    private String created_at;
    private String updated_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
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

    public int getExchange_deadline() {
        return exchange_deadline;
    }

    public void setExchange_deadline(int exchange_deadline) {
        this.exchange_deadline = exchange_deadline;
    }

    public int getExchange_count() {
        return exchange_count;
    }

    public void setExchange_count(int exchange_count) {
        this.exchange_count = exchange_count;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
