package com.why.transportsecurity.utils.commonutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName：DateFormatUtils
 * @Description：todo 事件日期工具类
 * @Author: why
 * @DateTime：2021/12/20 15:08
 */
public class DateFormatUtils {
    /**
     * 返回当前时间的long值
     * @return
     */
    public static long dateLong(){
        return new Date().getTime();
    }

    /**
     * 时间戳long值转换为yyyy-MM-dd HH:mm:ss时间
     * @param time
     * @return
     */
    public static String myFormatTime(long time){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(time);
    }

    public static Date stringToDate(String dateStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date date1 = new java.sql.Date(date.getTime());
        return date1;
    }
}
