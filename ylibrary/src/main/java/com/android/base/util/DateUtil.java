package com.android.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.lang.System.currentTimeMillis;

/**
 * Created by codeest on 16/8/13.
 */

public class DateUtil {

    //SimpleDateFormat 有线程安全的问题 by lailiao.fll
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private static SimpleDateFormat sdfbyh = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA);
    private static SimpleDateFormat signSdf = new SimpleDateFormat("yyyyMMddHHmm", Locale.CHINA);
    private static SimpleDateFormat shortTime = new SimpleDateFormat("HH:mm", Locale.CHINA);
    private static SimpleDateFormat ymdSdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);

    public static Date convertStrToDate(String str) {
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * 获取当前日期
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(new Date());
    }

    /**
     * 获取当前日期
     * @return
     */
    public static String getTomorrowDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return String.valueOf(Integer.valueOf(df.format(new Date())) + 1);
    }

    /**
     * 获取当前日期字符串
     * @return
     */
    public static String getCurrentDateString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        return df.format(new Date());
    }

    /**
     * 获取当前年
     * @return
     */
    public static int getCurrentYear() {
        Calendar cal=Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前月
     * @return
     */
    public static int getCurrentMonth() {
        Calendar cal=Calendar.getInstance();
        return cal.get(Calendar.MONTH);
    }

    /**
     * 获取当前日
     * @return
     */
    public static int getCurrentDay() {
        Calendar cal=Calendar.getInstance();
        return cal.get(Calendar.DATE);
    }

    /**
     * 将时间戳转化为字符串
     * @param showTime
     * @return
     */
    public static String formatTime2String(long showTime) {
        return formatTime2String(showTime,false);
    }

    public static String formatTime2String(long showTime , boolean haveYear) {
        String str = "";
        long distance = currentTimeMillis()/1000 - showTime;
        if(distance < 300){
            str = "刚刚";
        }else if(distance >= 300 && distance < 600){
            str = "5分钟前";
        }else if(distance >= 600 && distance < 1200){
            str = "10分钟前";
        }else if(distance >= 1200 && distance < 1800){
            str = "20分钟前";
        }else if(distance >= 1800 && distance < 2700){
            str = "半小时前";
        }else if(distance >= 2700){
            Date date = new Date(showTime * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            str = formatDateTime(sdf.format(date) , haveYear);
        }
        return str;
    }

    public static String formatDate2String(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(time == null){
            return "未知";
        }
        try {
            long createTime = format.parse(time).getTime() / 1000;
            long currentTime = System.currentTimeMillis() / 1000;
            if (currentTime - createTime - 24 * 3600 > 0) { //超出一天
                return (currentTime - createTime) / (24 * 3600) + "天前";
            } else {
                return (currentTime - createTime) / 3600 + "小时前";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "未知";
    }

    public static String formatDateTime(String time ,boolean haveYear) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(time == null){
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH)-1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);
        if(current.after(today)){
            return "今天 "+time.split(" ")[1];
        }else if(current.before(today) && current.after(yesterday)){
            return "昨天 "+time.split(" ")[1];
        }else{
            if(haveYear) {
                int index = time.indexOf(" ");
                return time.substring(0,index);
            }else {
                int yearIndex = time.indexOf("-")+1;
                int index = time.indexOf(" ");
                return time.substring(yearIndex,time.length()).substring(0,index);
            }
        }
    }

    /**
     * 专用于 temptime 请求参数的格式化
     *
     * @param date 时间戳
     */
    public static String signFormat(long date) {
        return signSdf.format(date);
    }

    public static String ymdSdfFormat(long date) {
        return ymdSdf.format(date);
    }

    public static String ymdSdfFormatByh(long date) {
        return sdfbyh.format(date);
    }

    public static String shortTimeFormat(long date) {
        return shortTime.format(date);
    }

    public static String format(long date) {
        return sdf.format(date);
    }

    public static String format1(long date) {
        return sdf1.format(date);
    }

    public static String format(String pattern, Date date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            if (date != null) {
                return format.format(date);
            }
            return format.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String format(String pattern, long time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(new Date(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long formatToLong(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(time);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long formatToLong(String time,String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date date = sdf.parse(time);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 取时间差
     */
    public static long diffDate(Date begin, Date end, int type) {
        long between = end.getTime() - begin.getTime();
        long s = (between / 1000);
        if (type == 1) {//天
            return between / (24 * 60 * 60 * 1000);
        } else if (type == 2) {//小时
            return (between / (60 * 60 * 1000));
        } else if (type == 3) {//分钟
            return (between / (60 * 1000));
        } else if (type == 4) {//秒
            return s;
        } else if (type == 5) {//毫秒
            return between;
        }
        return between / (24 * 60 * 60 * 1000);
    }

    /**
     * 判断两日期是否同一天
     */
    public static boolean isSameDay(Date dateA, Date dateB) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(dateA);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(dateB);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR) && calDateA.get(Calendar.MONTH) == calDateB
                .get(Calendar.MONTH) && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @param tailDate 后一个时间
     * @param preDate  前一个时间
     */
    public static String buildTimeDistance(Date tailDate, Date preDate, String defaultString) {
        long diffTime = tailDate.getTime() - preDate.getTime();
        if (diffTime > 0) {
            int seconds = (int) (diffTime / 1000);
            if (seconds <= 0) {
                return "";
            }
            if (seconds < 60) {
                return seconds + "秒前";
            }

            int minutes = (int) (diffTime / 1000 / 60);
            if (minutes <= 0) {
                return "";
            }

            if (minutes > 60) {
                int hours = minutes / 60;
                if (hours > 24) {
                    return sdf.format(preDate);
                } else {
                    return hours + "小时前";
                }

            } else {
                return minutes + "分钟前";
            }
        }
        return defaultString;
    }

    public static String getWeekDay(long off) {
        String result = "";
        final Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(off);
        String day = String.valueOf(instance.get(Calendar.DAY_OF_WEEK));
        if("1".equals(day)){
            result ="周日";
        }else if("2".equals(day)){
            result ="周一";
        }else if("3".equals(day)){
            result ="周二";
        }else if("4".equals(day)){
            result ="周三";
        }else if("5".equals(day)){
            result ="周四";
        }else if("6".equals(day)){
            result ="周五";
        }else if("7".equals(day)){
            result ="周六";
        }
        return result;
    }

    public static String getEnWeekDay(long off) {
        String result = "";
        final Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(off);
        String day = String.valueOf(instance.get(Calendar.DAY_OF_WEEK));
        if("1".equals(day)){
            result ="Sun";
        }else if("2".equals(day)){
            result ="Mon";
        }else if("3".equals(day)){
            result ="Tue";
        }else if("4".equals(day)){
            result ="Wed";
        }else if("5".equals(day)){
            result ="Thu";
        }else if("6".equals(day)){
            result ="Fri";
        }else if("7".equals(day)){
            result ="Sat";
        }
        return result;
    }
}
