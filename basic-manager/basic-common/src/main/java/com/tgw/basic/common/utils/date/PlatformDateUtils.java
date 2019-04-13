package com.tgw.basic.common.utils.date;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zjg on 2019/4/14.
 */
public class PlatformDateUtils {

    public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YMDH = "yyyy-MM-dd HH";
    public static final String DATE_FORMAT_YMDHM = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取今天
     * @return Date
     * */
    public static Date getToday(){
        return new Date();
    }

    /**
     * 获取昨天
     * @return Date
     * */
    public static Date getYestoday(){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        return cal.getTime();
    }

    /**
     * 获取本月开始日期
     * @return Date
     * **/
    public static Date getMonthStart(){
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        return cal.getTime();
    }

    /**
     * 获取本月最后一天
     * @return Date
     * **/
    public static Date getMonthEnd(){
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY,cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE,cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND,cal.getActualMaximum(Calendar.SECOND));
        return cal.getTime();
    }

    /**
     * 获取本周的第一天
     * @return Date
     * **/
    public static Date getWeekStart(){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_MONTH, 0);
        cal.set(Calendar.DAY_OF_WEEK, 2);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        return cal.getTime();
    }

    /**
     * 获取本周的最后一天
     * @return Date
     * **/
    public static Date getWeekEnd(){
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
        cal.add(Calendar.DAY_OF_WEEK, 1);
        cal.set(Calendar.HOUR_OF_DAY,cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE,cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND,cal.getActualMaximum(Calendar.SECOND));
        return cal.getTime();
    }

    /**
     * 获取本年的第一天
     * @return Date
     * **/
    public static Date getYearStart(){
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.MONTH,0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        return cal.getTime();
    }

    /**
     * 获取本年的最后一天
     * @return Date
     * **/
    public static Date getYearEnd(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH,cal.getActualMaximum(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY,cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE,cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND,cal.getActualMaximum(Calendar.SECOND));
        return cal.getTime();
    }
}
