package com.android.base.callback.entity;

import com.android.base.module.http.callback.ShowApiResponse;

/**
 * token实体类
 * Created by YANGQIYUN on 2017/4/12.
 */

public class TokenResultDo extends ShowApiResponse<TokenResultDo> {

    private String sessionToken;
    private String rquestToken;

    public TokenResultDo(){}

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getRquestToken() {
        return rquestToken;
    }

    public void setRquestToken(String rquestToken) {
        this.rquestToken = rquestToken;
    }
}
