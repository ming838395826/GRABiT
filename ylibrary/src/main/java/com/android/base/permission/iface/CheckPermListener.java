package com.android.base.permission.iface;

/**
 * Created by Administrator on 2017/1/18.
 */

public interface CheckPermListener {

    //权限通过后的回调方法
    void superPermission(int requestCode);
}
