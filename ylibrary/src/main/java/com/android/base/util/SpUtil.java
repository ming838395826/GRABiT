package com.android.base.util;

import android.content.SharedPreferences;

import com.android.base.base.application.BaseApplication;

/**
 * 作者：yangqiyun
 * 时间：2017/7/20
 * 1120389276@qq.com
 * 描述：SharedPreferences 封装
 */

public class SpUtil {

    protected static SharedPreferences sp;
    protected static SharedPreferences.Editor edit;

    public static SharedPreferences getSharedPreferences(){
        if (sp == null) {
            sp = BaseApplication.getContext().getSharedPreferences("spin", 0);
        }
        return sp;
    }

    public static SharedPreferences.Editor getEdit() {
        if(edit == null){
            edit = getSharedPreferences().edit();
        }
        return edit;
    }

    public void commit(){
        getEdit().commit();
    }

    public SpUtil putString(String key,String value){
        if(key == null){
            return this;
        }
        getEdit().putString(key,value);
        return this;
    }

    public SpUtil putStringCommit(String key,String value){
        putString(key,value).commit();
        return this;
    }

    public SpUtil putBoolean(String key,boolean value){
        if(key == null){
            return this;
        }
        getEdit().putBoolean(key,value);
        return this;
    }

    public SpUtil putBooleanCommit(String key,boolean value){
        putBoolean(key,value).commit();
        return this;
    }

    public SpUtil putFloat(String key,float value){
        if(key == null){
            return this;
        }
        getEdit().putFloat(key,value);
        return this;
    }

    public SpUtil putFloatCommit(String key,float value){
        putFloat(key,value).commit();
        return this;
    }

    public SpUtil putInt(String key,int value){
        if(key == null){
            return this;
        }
        getEdit().putInt(key,value);
        return this;
    }

    public SpUtil putIntCommit(String key,int value){
        putInt(key,value).commit();
        return this;
    }

    public SpUtil putLong(String key,long value){
        if(key == null){
            return this;
        }
        getEdit().putLong(key,value);
        return this;
    }

    public SpUtil putLongCommit(String key,long value){
        putLong(key,value).commit();
        return this;
    }
}
