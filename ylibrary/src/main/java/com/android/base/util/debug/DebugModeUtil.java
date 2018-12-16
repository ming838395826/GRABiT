package com.android.base.util.debug;

import android.app.Application;
import android.content.pm.ApplicationInfo;

/**
 * debug模式
 * Created by YANGQIYUN on 2017/4/24.
 */

public class DebugModeUtil {

    //当前是否处于debug模式
    public static boolean DBG = false;

    public static void init(Application application){
        try {
            ApplicationInfo info = application.getApplicationInfo();
            DBG = ((info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
