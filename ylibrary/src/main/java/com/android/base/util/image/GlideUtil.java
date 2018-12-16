package com.android.base.util.image;

import android.content.Context;
import android.widget.ImageView;

import com.android.base.R;
import com.bumptech.glide.Glide;

/**
 * 作者：yangqiyun
 * 时间：2017/9/5
 * 1120389276@qq.com
 * 描述：glide管理工具
 */

public class GlideUtil {

    /**
     * 默认loading方法
     * @param mcontext
     * @param url
     * @param imageView
     */
    public static void defaultLoad(Context mcontext, String url, ImageView imageView){
        if(mcontext == null || imageView == null){
            return;
        }
        Glide.with(mcontext).load(url).into(imageView);
    }

    /**
     * 默认loading方法
     * @param mcontext
     * @param url
     * @param imageView
     */
    public static void defualtLoad(Context mcontext, String url, int place,ImageView imageView){
        if(mcontext == null || imageView == null){
            return;
        }
        Glide.with(mcontext).load(url).error(place).dontAnimate().into(imageView);
    }
}
