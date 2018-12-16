package com.android.base.entity;

import android.app.Application;
import android.os.Build;
import android.util.DisplayMetrics;

import com.android.base.BuildConfig;
import com.android.base.base.application.BaseApplication;

/**
 * 作者：YANGQIYUN
 * 时间 2017 06 21
 * 邮箱：1120389276@qq.com
 * 描述：app信息实体
 */

public class AppInfo {

    private String API = BuildConfig.VISITOR_API_HOST;
//    private String API = "http://47.52.95.70:8080";
    //application下上文
    private Application instance;
    // 系统版本
    private String systemVersion;
    /**
     * 屏幕参数
     */
    private float ScreenDensity = 0;
    private int ScreenWidth = 480;
    private int ScreenHeight = 800;

    public AppInfo() {

    }

    public void init(Application instance) {
        this.instance = instance;
        //初始化屏幕变量
        initScreenParams();
        //初始化配置信息
        initDebugManager();
    }

    public String getAPI(){
        return API;
    }

    /**
     * 调试模式
     */
    private void initDebugManager(){
        BaseApplication.info.setSystemVersion(Build.VERSION.RELEASE);
    }

    /**
     * 屏幕参数初始化
     */
    private void initScreenParams(){
        DisplayMetrics metrics = BaseApplication.getContext().getResources().getDisplayMetrics();
        BaseApplication.info.setScreenDensity(metrics.density);
        if (metrics.widthPixels > metrics.heightPixels) {
            BaseApplication.info.setScreenWidth(metrics.heightPixels);
            BaseApplication.info.setScreenHeight(metrics.widthPixels);
        } else {
            BaseApplication.info.setScreenWidth(metrics.widthPixels);
            BaseApplication.info.setScreenHeight(metrics.heightPixels);
        }
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public float getScreenDensity() {
        return ScreenDensity;
    }

    public void setScreenDensity(float screenDensity) {
        ScreenDensity = screenDensity;
    }

    public int getScreenWidth() {
        return ScreenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        ScreenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return ScreenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        ScreenHeight = screenHeight;
    }

    public Application getApplication() {
        return instance;
    }

    public void setApplication(Application instance) {
        this.instance = instance;
    }

}
