package com.android.spin.home.holder;

import android.app.Activity;
import android.os.Handler;

import com.android.base.base.application.BaseApplication;
import com.android.base.util.ToastUtil;
import com.android.spin.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dell on 2017/5/10.
 */

public class HomeBackHolder {


    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    public static void exitBy2Click(Activity activity) {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            ToastUtil.shortShow("双击返回退出" + activity.getString(R.string.app_name));
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            BaseApplication.mAppHandle.doAppExit();
            activity.finish();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    System.exit(0);
                }
            }, 500);
        }
    }
}
