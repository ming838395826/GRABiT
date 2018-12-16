package com.android.base.db.inter;

/**
 * 存取
 * Created by YANGQIYUN on 2017/5/9.
 */

public interface CacheInterface<T> {

    T putString(String key, String value);

    T putInt(String key, int value);

    T putFloat(String key, float value);

    T putBoolean(String key, boolean value);

    T commit();
}
