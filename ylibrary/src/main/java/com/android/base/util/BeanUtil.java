package com.android.base.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：yangqiyun
 * 时间：2017/8/2
 * 1120389276@qq.com
 * 描述：
 */

public class BeanUtil {

//    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
//    public static Map<String, Object> transBean2Map(Object obj) {
//
//        if(obj == null){
//            return null;
//        }
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
//            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//            for (PropertyDescriptor property : propertyDescriptors) {
//                String key = property.getName();
//
//                // 过滤class属性
//                if (!key.equals("class")) {
//                    // 得到property对应的getter方法
//                    Method getter = property.getReadMethod();
//                    Object value = getter.invoke(obj);
//
//                    map.put(key, value);
//                }
//
//            }
//        } catch (Exception e) {
//            System.out.println("transBean2Map Error " + e);
//        }
//
//        return map;
//
//    }
//
//    /**
//     * 将对象装换为map
//     * @param bean
//     * @return
//     */
//    public static <T> Map<String, Object> beanToMap(T bean) {
//        Map<String, Object> map = Maps.newHashMap();
//        if (bean != null) {
//            BeanMap beanMap = BeanMap.create(bean);
//            for (Object key : beanMap.keySet()) {
//                map.put(key+"", beanMap.get(key));
//            }
//        }
//        return map;
//    }
}
