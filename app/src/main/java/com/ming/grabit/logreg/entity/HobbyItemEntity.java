package com.ming.grabit.logreg.entity;

/**
 * 作者：yangqiyun
 * 时间：2017/7/23
 * 1120389276@qq.com
 * 描述：
 */

public class HobbyItemEntity {

    private int resId;
    private String content;
    private boolean isChecked = false;

    public HobbyItemEntity(int resId,String content){
        this.resId = resId;
        this.content = content;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
