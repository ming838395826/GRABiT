package com.android.base.db;

import android.content.SharedPreferences;

import com.android.base.db.inter.CacheInterface;

/**
 * 本地化数据管理类超类
 * Created by YANGQIYUN on 2017/5/9.
 */

public abstract class BaseManager implements CacheInterface<BaseManager> {
    //二级缓存用
    protected SharedPreferences sp;
    protected SharedPreferences.Editor edit;

    public static String baseToken = "";

    public BaseManager(){
        sp = getSp();
        edit = getEditor();
    }

    protected abstract SharedPreferences getSp();

    protected SharedPreferences.Editor getEditor(){
        if(edit == null){
            edit = sp.edit();
        }
        return edit;
    }

    /**
     * 存字段
     */
    @Override
    public BaseManager putString(String key, String value) {
        if(!sp.getString(key,"").equals(value)){
            edit.putString(key, value);
        }
        return this;
    }

    /**
     * 存字段
     */
    @Override
    public BaseManager putInt(String key, int value) {
        if(value != sp.getInt(key,0)){
            edit.putInt(key, value);
        }
        return this;
    }

    /**
     * 存字段
     */
    @Override
    public BaseManager putFloat(String key, float value) {
        if(value != sp.getFloat(key,0f)){
            edit.putFloat(key, value);
        }
        return this;
    }

    /**
     * 存字段
     */
    @Override
    public BaseManager putBoolean(String key, boolean value) {
        if(value != sp.getBoolean(key,false)){
            edit.putBoolean(key, value);
        }
        return this;
    }

    /**
     * 存字段
     */
    public BaseManager putKeyString(String key, String value) {
        if(!value.equals(sp.getString(key,""))){
            edit.putString(key, value);
        }
        return this;
    }
    @Override
    public BaseManager commit(){
        edit.commit();
        return this;
    }

    public String getString(String key,String def){
        if(def != null){
            return def;
        }
        return sp.getString(key,null);
    }

    /**
     * 获取
     */
    private int getInt(String key) {
        return sp.getInt(key, -1);
    }

    /**
     * 获取
     */
    public boolean getBoolean(String key) {
        return sp.getBoolean(key, true);
    }


    /**
     * 获取
     */
    public String getKeyString(String key) {
        return sp.getString(key,"");
    }

    public String getHeaderToken(){
        return null;
    }
}
