package com.android.base.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.Window;

import com.android.base.base.application.BaseApplication;

/**
 * 手机屏幕密度工具类
 *
 * @author WilliamChik on 2015/7/22
 */
public class DensityUtil {

  /**
   * dp转px
   */
  public static int dp2px(float dpValue) {
    if (BaseApplication.info.getScreenDensity() == 0.0f) {
      BaseApplication.info.setScreenDensity(BaseApplication.getContext().getResources().getDisplayMetrics().density);
    }
    final float scale = BaseApplication.info.getScreenDensity();
    return (int) (dpValue * scale + 0.5f);
  }

  /**
   * px转dp
   */
  public static int px2dp(float pxValue) {
    if (BaseApplication.info.getScreenDensity() == 0.0f) {
      BaseApplication.info.setScreenDensity(BaseApplication.getContext().getResources().getDisplayMetrics().density);
    }
    final float scale = BaseApplication.info.getScreenDensity();
    int result = (int) (pxValue / scale + 0.5f);
    return result;
  }
  /**
   * 屏幕宽
   * */
  public static int getWidth(){
    return BaseApplication.info.getScreenWidth();
  }
  /**
   * 屏幕高
   * */
  public static int getHeight(){
    return BaseApplication.info.getScreenHeight();
  }
  /**
   * 获取图片高度像素
   */
  public static int getImageHeightPx() {
    int screenWidth = BaseApplication.info.getScreenWidth();
    int space = dp2px(12);
    return (screenWidth - space * 3) / 2;
  }

  public static int getRecommendItemHeightPx() {
    int screenWidth = BaseApplication.info.getScreenWidth();
    int space = dp2px(10);
    int padding = dp2px(12);
    return (screenWidth - space * 2 - padding * 2) / 3;
  }

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

  public static double change(double a){
    return a * Math.PI  / 180;
  }
}
