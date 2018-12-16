package com.android.base.callback.entity;

/**
 * Created by veber on 2016/11/24.
 */

public class PHPShowApiResponse<T> {

    private int code;
    private String msgStr;
    public T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsgStr() {
        return msgStr;
    }

    public void setMsgStr(String msgStr) {
        this.msgStr = msgStr;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
