package com.android.base.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.Window;

/**
 * 作者：YANGQIYUN
 * 时间 2017 06 21
 * 邮箱：1120389276@qq.com
 * 描述：设备管理工具
 */

public class DeviceManagerUtil {

    /**
     * 获取通知栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Rect frame = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        return statusBarHeight;
    }

    /**
     * 获取标题栏高度
     *
     * @param context
     * @return
     */
    public static int getTitleBarHeight(Context context) {
        int contentTop = ((Activity) context).getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        // statusBarHeight是上面所求的通知栏的高度
        int titleBarHeight = contentTop - getStatusBarHeight(context);
        return titleBarHeight;
    }
}
