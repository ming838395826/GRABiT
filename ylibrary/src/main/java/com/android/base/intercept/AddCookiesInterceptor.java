package com.android.base.intercept;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.base.base.application.BaseApplication;
import com.android.base.db.BaseManager;
import com.android.base.util.AppLanguageManager;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * cooki拦截器
 * Created by YANGQIYUN on 2017/4/15.
 */

public class AddCookiesInterceptor implements Interceptor{

    private static final String COOKIE_PREF = "cookies_prefs";
    private static final String KEY_TOKEN = "token";

    private static final String FILE_NAME = "userInfo";
    private Context mContext;

    private SharedPreferences sp;

    private SharedPreferences getSharedPreferences(){
        if(sp == null){
            sp = BaseApplication.getInstance().getSharedPreferences(FILE_NAME, 0);
        }
        return sp;
    }

    public AddCookiesInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        String cookie = getCookie(request.url().toString(), request.url().host());
        Logger.d(request.url().toString());
        if (!TextUtils.isEmpty(cookie)) {
            builder.addHeader("Cookie", cookie);
        }
        builder.header("Authorization", "Bearer " + getSharedPreferences().getString(KEY_TOKEN,""));
        //zh-cn zh-tw en
        builder.header("Accept-Language", AppLanguageManager.getHeaserLanguage());

        return chain.proceed(builder.build());
    }

    private String getCookie(String url, String domain) {
        SharedPreferences sp = mContext.getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
        if (!TextUtils.isEmpty(url)&&sp.contains(url)&&!TextUtils.isEmpty(sp.getString(url,""))) {
            return sp.getString(url, "");
        }
        if (!TextUtils.isEmpty(domain)&&sp.contains(domain) && !TextUtils.isEmpty(sp.getString(domain, ""))) {
            return sp.getString(domain, "");
        }

        return null;
    }
}
