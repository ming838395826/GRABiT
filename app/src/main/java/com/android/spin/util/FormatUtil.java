package com.android.spin.util;

/**
 * Created by ming on 2019/1/30.
 */

public class FormatUtil {

    public static String formatTime(long time){
        return time<10?"0"+time:time+"";
    }
}
