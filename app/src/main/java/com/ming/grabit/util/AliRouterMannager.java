package com.ming.grabit.util;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ming.grabit.R;

/**
 * 作者：yangqiyun
 * 时间：2017/9/24
 * 1120389276@qq.com
 * 描述：
 */

public class AliRouterMannager {

    public static void baseNavigation(String path){
        ARouter.getInstance().build(path).withTransition(R.anim.app_slide_right_in, R.anim.app_slide_left_out).navigation();
    }
}
