package com.android.base.util.debug;

import android.app.Application;

/**
 *
 * Created by YANGQIYUN on 2017/4/24.
 */

public class DebugAction {

    public boolean init(Application application){
        DebugModeUtil.init(application);
        return true;
    }
}
