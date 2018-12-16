package com.android.spin.event;

/**
 * 作者：yangqiyun
 * 时间：2017/9/28
 * 1120389276@qq.com
 * 描述：
 */

public class UpdateCardEvent {

    private int id;

    public UpdateCardEvent(int id){
        this.id = id;
    }

    public boolean equals(int id){
        if(this.id == id){
            return true;
        }
        return false;
    }
}
