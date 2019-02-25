package com.ming.grabit.base;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.base.base.application.BaseApplication;
import com.android.base.util.AppUtil;
import com.ming.grabit.BuildConfig;
import com.facebook.FacebookSdk;
import com.mob.MobSDK;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.Config;
import com.umeng.socialize.UMShareAPI;

import cn.jpush.android.api.JPushInterface;

/**
 * 作者：yangqiyun
 * 时间：2017/7/20
 * 1120389276@qq.com
 * 描述：
 */

public class SpinApplication extends BaseApplication {

    private static SpinApplication instance;

    public static boolean isNeedLoad = true;
    public static boolean isFirstLoad=true;//是否第一次加载 刚启动页面

    @Override
    public void onCreate() {
        if(AppUtil.isMainProcess(this)) {
            //为主进程才执行，避免重复初始化
            super.onCreate();
            CrashReport.initCrashReport(getApplicationContext(), "3f411f6972", true);
            FacebookSdk.sdkInitialize(this);
            JPushInterface.setDebugMode(true);
            JPushInterface.init(this);
            Config.DEBUG = false;
            UMShareAPI.get(this);
//            MobSDK.init(this,"21d0f66d142e1","e83c8771200804dc54fa22d5bcfd248d");
            MobSDK.init(this,"29fc443b73fa0","b773458182b27fc4a1e09a8c49ee783e");
//            AppSecretMobSDK
        }

        initRouter();
    }

    private void initRouter(){
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
    }
    @Override
    public void init() {

    }

    // Activity and Application
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(AppLanguageManager.attachBaseContext(newBase));
//    }

}
