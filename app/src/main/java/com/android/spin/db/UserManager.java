package com.android.spin.db;

import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.base.base.application.BaseApplication;
import com.android.base.db.BaseManager;
import com.android.spin.mine.entity.UserEntity;

/**
 * 作者：yangqiyun
 * 时间：2017/9/7
 * 1120389276@qq.com
 * 描述：
 */

public class UserManager extends BaseManager {

    public static BaseApplication application;

    private static final String KEY_TOKEN = "token";
    private static final String KEY_QINIU_TOKEN = "qiniu_token";

    private static final String KEY_USER_INFO = "userInfo";

    private static final String FILE_NAME = "userInfo";

    private UserEntity user;

    public static UserManager getInstance() {
        application = BaseApplication.application;
        return ManagerHolder.instance;
    }

    private static class ManagerHolder {
        private static final UserManager instance = new UserManager();
    }

    @Override
    protected SharedPreferences getSp() {
        return application.getSharedPreferences(FILE_NAME, 0);
    }

    /**
     * 保存token
     * @param token
     */
    public void saveToken(String token){
        baseToken = token;
        putString(KEY_TOKEN,token).commit();
    }

    /**
     * 获取token
     * @return
     */
    public String getToken(){
        return getKeyString(KEY_TOKEN);
    }

    /**
     * 保存token
     * @param token
     */
    public void saveQiNiuToken(String token){
        putString(KEY_QINIU_TOKEN,token).commit();
    }

    /**
     * 获取token
     * @return
     */
    public String getQiNiuToken(){
        return getKeyString(KEY_QINIU_TOKEN);
    }

    /**
     * 保存用户基础信息
     * @param json
     */
    public void setUserInfo(String json){
        putString(KEY_USER_INFO,json).commit();
        user = null;
    }

    /**
     * 获取用户信息json
     * @return
     */
    public String getUserInfoJson(){
        return getKeyString(KEY_USER_INFO);
    }

    public UserEntity getUser(){
        if(user == null){
            String userInfo = getUserInfoJson();
            user = JSONObject.parseObject(userInfo == null || "".equals(userInfo)?"{}":userInfo,UserEntity.class);
        }
        if(user == null){
            user = new UserEntity();
        }
        return user;
    }

    /**
     * 退出
     */
    public void logout(){
        saveToken(null);
        setUserInfo(null);
    }

}
