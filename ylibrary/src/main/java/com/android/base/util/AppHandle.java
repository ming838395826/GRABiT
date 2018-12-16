package com.android.base.util;

import android.app.Activity;
import android.app.Application;
import android.os.StrictMode;

import com.android.base.base.BaseActivity;

import java.util.Enumeration;
import java.util.Hashtable;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.GINGERBREAD;

/**
 * app操作管理类
 * Created by Administrator on 2016/12/9.
 */

public class AppHandle {

    //application下上文
    private Application instance;

    // Activity 栈，保存打开过的所有 Activity
    public static Hashtable<Activity, Activity> hashActivity = new Hashtable<Activity, Activity>();
    // 当前activity
    public static BaseActivity activeActivity;

    public void init(Application instance){
        this.instance = instance;
    }

    /**
     * app重启
     */
    public static void doAppRestart() {
        System.exit(0);
    }

    /**
     * activity 启动时调用
     */
    public static void onAllActivityCreate(Activity activity) {
        hashActivity.put(activity, activity);
    }

    /**
     * activity 销毁时调用
     */
    public static void onAllActivityDestroy(Activity activity) {
        hashActivity.remove(activity);
    }

    /**
     * 严苛模式 线程警告
     */
    private void enabledStrictMode() {
        if (SDK_INT >= GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                    .detectAll()  //
                    .penaltyLog() //
                    .penaltyDeath() //
                    .build());
        }
    }
    /**
     * app退出的相关操作
     */
    public static void doAppExit() {
        // 强制关闭所有页面
        Enumeration<Activity> emu = hashActivity.elements();
        while (emu.hasMoreElements()) {
            Activity ac = emu.nextElement();
            ac.finish();
        }
        // 清除已执行和正在执行的页面列表
        hashActivity.clear();
        activeActivity = null;

    }

}
