package com.android.base.callback.entity;

/**
 * Created by veber on 2016/11/24.
 */

public class OtherShowApiResponse<T> {
    /**
      {
     "code":1000,
     "msg":"广告获取成功",
     "adList":[
     {
     "picUrl":"http://oap1h8rgp.bkt.clouddn.com/assets/images/user_avatar/avatar1479895674850.jpeg",
     "adType":null,
     "articleId":null,
     "text":""
     }
     ]
     }
     */

    private String code;
    private String msg;
    public T adList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return adList;
    }

    public void setData(T adList) {
        this.adList = adList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
