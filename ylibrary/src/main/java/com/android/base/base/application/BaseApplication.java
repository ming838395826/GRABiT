package com.android.base.base.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.android.base.entity.AppInfo;
import com.android.base.util.AppHandle;
import com.android.base.util.debug.DebugModeUtil;
import com.facebook.drawee.backends.pipeline.Fresco;


/**
 * 作者：YANGQIYUN
 * 时间 2017 06 21
 * 邮箱：1120389276@qq.com
 * 描述：应用管理工具
 */
public abstract class BaseApplication extends MultiDexApplication {
    //手机信息
    public static AppInfo info;
    //操作手柄
    public static AppHandle mAppHandle;

    public static BaseApplication application;

    public static boolean isInitLanguage = false;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
        //初始化配置
        initConfigure();

        init();

        Fresco.initialize(this);
        //路由器
//        Router.initialize(this);
    }

    //初始化配置
    private void initConfigure() {
        info = new AppInfo();
        info.init(this);
        mAppHandle = new AppHandle();
        mAppHandle.init(this);

        DebugModeUtil.init(this);

    }

    // 获取ApplicationContext
    public static Context getContext() {
        return info.getApplication();
    }

    public static Application getInstance(){
        return application;
    }

    public abstract void init();
}
