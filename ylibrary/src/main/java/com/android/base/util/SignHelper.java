package com.android.base.util;


import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 校对工具，生成网络请求的校对参数，如 time、appkey、sign
 *
 * @author WilliamChik on 15/11/4.
 */
public class SignHelper {

  public static Map<String, Object> addSign(Map<String, Object> bizParams) {
    try {
      if(bizParams == null){
        return bizParams = new HashMap<>();
      }
      Logger.d(bizParams);
      return bizParams;
    } catch (Exception e) {
      e.printStackTrace();
      return bizParams;
    }
  }

}
