package com.zkd.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * describe: 日期相关的工具类
 * creator: keding.zhou
 * date: 2018/4/16 8:37
 */
public class MyDateUtils {
    public static SimpleDateFormat FORMAT_TYPE_1 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    public static SimpleDateFormat FORMAT_TYPE_2 = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
    public static SimpleDateFormat FORMAT_TYPE_3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    public static SimpleDateFormat FORMAT_TYPE_4 = new SimpleDateFormat("MM-dd", Locale.CHINA);
    public static SimpleDateFormat FORMAT_TYPE_5 = new SimpleDateFormat("HH:mm", Locale.CHINA);
    public static SimpleDateFormat FORMAT_TYPE_6 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    public static SimpleDateFormat FORMAT_TYPE_7 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);

    private static Calendar mCalendar;

    public MyDateUtils() {
        if (mCalendar == null) {
            mCalendar = Calendar.getInstance();
        }
    }

    private static Calendar getCalendar() {
        if (mCalendar == null) {
            mCalendar = Calendar.getInstance();
        }
        return mCalendar;
    }

    /**
     * date转string
     *
     * @param date 时间 Date
     * @return Strin
     */
    public static String getDate2String(Date date) {
        if (date != null) {
            return getDate2String(date, FORMAT_TYPE_1);
        } else {
            return "";
        }
    }

    /**
     * date转string
     *
     * @param date 时间 Date
     * @return String
     */
    public static String getDate2String(Date date, SimpleDateFormat format) {
        if (date != null) {
            return format.format(date);
        } else {
            return "";
        }
    }

    /**
     * string 转 date
     *
     * @param dateStr 时间 String
     * @return Date
     */
    public static Date getDate(String dateStr) {
        try {
            return getDate(dateStr, FORMAT_TYPE_1);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * string 转 date
     *
     * @param dateStr 时间 String
     * @return Date
     */
    public static Date getDate(String dateStr, SimpleDateFormat format) {
        try {
            return format.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断是否为今天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     */
    public static boolean isToday(String day) {
        Calendar pre = getCalendar();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = getDate(day);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);
            return diffDay == 0;
        }
        return false;
    }

    /**
     * 判断是否为昨天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     */
    public static boolean isYesterday(String day) {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = getDate(day);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);
            return diffDay == -1;
        }
        return false;
    }

    /**
     * 比较两个日期大小
     *
     * @param date1 2017-11-20
     * @param date2 2017-11-20
     * @return true：date1>=date2
     */
    public static boolean isDateBigger(Date date1, Date date2) {
        try {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date1);
            int year1 = calendar1.get(Calendar.YEAR);
            int month1 = (calendar1.get(Calendar.MONTH)) + 1;
            int day1 = calendar1.get(Calendar.DAY_OF_MONTH);

            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(date2);
            int year2 = calendar2.get(Calendar.YEAR);
            int month2 = (calendar2.get(Calendar.MONTH)) + 1;
            int day2 = calendar2.get(Calendar.DAY_OF_MONTH);

            if (year1 > year2) {
                return true;
            } else if (year1 == year2) {
                if (month1 > month2) {
                    return true;
                } else if (month1 == month2) {
                    return day1 >= day2;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

    public static Date getCurrentDate() {
        return getCalendar().getTime();
    }

    public static String getCurrentDateString(SimpleDateFormat format) {
        Date date = getCalendar().getTime();
        return format.format(date);
    }

    public static String getYearMonthDayString() {
        return getCurrentYear() + getCurrentMonth() + getCurrentDay();
    }

    public static String getCurrentYear() {
        return getCalendar().get(Calendar.YEAR) + "";
    }

    public static String getCurrentMonth() {
        return (getCalendar().get(Calendar.MONTH) + 1) + "";
    }

    public static String getCurrentDay() {
        return getCalendar().get(Calendar.DAY_OF_MONTH) + "";
    }
}
