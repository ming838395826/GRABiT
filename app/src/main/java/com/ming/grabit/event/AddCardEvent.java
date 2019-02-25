package com.ming.grabit.event;

/**
 * 作者：yangqiyun
 * 时间：2017/9/28
 * 1120389276@qq.com
 * 描述：
 */

public class AddCardEvent {

    private int statue;
    public AddCardEvent(int statue){
        this.statue = statue;
    }

    public int getStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }

    public boolean isAdd(){
        if(statue == 0){
            return true;
        }
        return false;
    }
}
