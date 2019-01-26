package com.android.base.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import com.android.base.base.application.BaseApplication;

import java.util.Locale;

/**
 * 作者：yangqiyun
 * 时间：2017/7/20
 * 1120389276@qq.com
 * 描述：语言管理工具类
 */

public class AppLanguageManager {

    protected static SharedPreferences sp;
    protected static SharedPreferences.Editor edit;


    /**
     * 切换语言
     * @param act
     */
    public static void changeAppLanguage(Context act) {
        String sta = getLanuage();
        // 本地语言设置
//        Locale myLocale = new Locale(null,sta,null);
//        Resources res = act.getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        Configuration conf = res.getConfiguration();
//        conf.locale = getLocale(sta);
//        res.updateConfiguration(conf, dm);

        Configuration configuration = act.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(getLocale(sta));
        } else {
            configuration.locale = getLocale(sta);
        }
        DisplayMetrics displayMetrics = act.getResources().getDisplayMetrics();
        act.getResources().updateConfiguration(configuration, displayMetrics);
//        setLocale(context, locale);

    }

    private static Locale getLocale(String sta){
        if("EN".equals(sta)){
            return Locale.ENGLISH;
        }else if("HK".equals(sta)){
            return Locale.TAIWAN;
        }
        return Locale.CHINA;
    }

    private static String getLanuage(){
        //默认语言
        return SpUtil.getSharedPreferences().getString("language","HK");

    }

    // AppLanguageUtils.java
    public static Context attachBaseContext(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return AppLanguageManager.updateResources(context, getLanuage());
        } else {
            return context;
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Context updateResources(Context context, String language) {
        Resources resources = context.getResources();
        Locale locale = getLocale(language);

        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLocales(new LocaleList(locale));
        return context.createConfigurationContext(configuration);
    }

    private static void setLanguage(String language){
        SpUtil.getEdit().putString("language",language).commit();
    }

    public static void setLanguageCN(){
        setLanguage("CN");
    }

    public static void setLanguageEN(){
        setLanguage("EN");
    }

    public static void setLanguageHk(){
        setLanguage("HK");
    }


    public static boolean isLanguageCN(){
        if(!"CN".equals(getLanuage())){
            return false;
        }
        return true;
    }

    public static boolean isLanguageHK(){
        if(!"HK".equals(getLanuage())){
            return false;
        }
        return true;
    }

    public static boolean isLanguageEN(){
        if(!"EN".equals(getLanuage())){
            return false;
        }
        return true;
    }

    /**
     * 获取请求头应该添加的语言
     * //zh-cn zh-tw en
     * @return
     */
    public static String getHeaserLanguage(){

        if(isLanguageCN()){
            return "zh-cn";
        }

        if(isLanguageHK()){
            return "zh-tw";
        }

        if(isLanguageEN()){
            return "en";
        }
        return null;
    }
}
