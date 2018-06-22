package com.jadyer.demo.comm.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 * ----------------------------------------------------------------------------------------------------------------
 * @version v1.3
 * @version v1.3-->增加getDistanceDay()方法，用於計算兩個日期相隔的天數
 * @version v1.2-->增加getCrossDayList()方法，用于获取两个日期之间的所有日期列表
 * @history v1.1-->增加根据日期获得星期的方法getWeekName()
 * @history v1.0-->新建不添加若干方法
 * ----------------------------------------------------------------------------------------------------------------
 * Created by 玄玉<https://jadyer.github.io/> on 2017/5/19 11:05.
 */
public final class DateUtil {
    private DateUtil(){}

    /**
     * 判断是否本月第一天
     */
    public static boolean isFirstDayOfMonth(){
        return "01".equals(DateFormatUtils.format(new Date(), "dd"));
    }


    /**
     * 判断是否本周第一天
     */
    public static boolean isFirstDayOfWeek(){
        return 0 == DateUtils.truncatedCompareTo(new Date(), getFirstDayOfWeek(), Calendar.DAY_OF_MONTH);
    }


    /**
     * 获取本周第一天
     */
    public static Date getFirstDayOfWeek(){
        //使用默认时区和语言环境获得一个基于当前时间的日历
        Calendar cal = Calendar.getInstance();
        //设置一个星期的第一天是哪一天（也可以用cal.add(Calendar.DAY_OF_MONTH, -1)，二者都是解决周日时获取到的是下一周的情况）
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        //将给定的日历字段设置为给定值
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }


    /**
     * 获取前一天日期yyyyMMdd
     * @see 经测试，针对闰年02月份或跨年等情况，该代码仍有效。测试代码如下
     * @see calendar.set(Calendar.YEAR, 2013);
     * @see calendar.set(Calendar.MONTH, 0);
     * @see calendar.set(Calendar.DATE, 1);
     * @see 测试时，将其放到<code>calendar.add(Calendar.DATE, -1);</code>前面即可
     * @return 返回的日期格式为yyyyMMdd
     */
    public static String getYestoday(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
    }


    /**
     * 获取格式化的详细日期
     * @param dateStr yyyyMMdd格式的日期字符串
     * @return yyyy-MM-dd格式的日期字符串
     */
    public static String getDetailDate(String dateStr){
        try {
            return String.format("%tF", DateUtils.parseDate(dateStr, "yyyyMMdd"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取当前的日期yyyyMMdd
     */
    public static String getCurrentDate(){
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }


    /**
     * 获取当前的时间yyyyMMddHHmmss
     */
    public static String getCurrentTime(){
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }


    /**
     * 获取本周开始的时间
     */
    public static Date getCurrentWeekStartDate(){
        Calendar currentDate = Calendar.getInstance();
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return currentDate.getTime();
    }


    /**
     * 获取本周结束的时间
     */
    public static Date getCurrentWeekEndDate(){
        Calendar currentDate = Calendar.getInstance();
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return currentDate.getTime();
    }


    /**
     * 计算两个日期的相差时间
     * @param begin 起始日期
     * @param end   终止日期
     * @return xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(Date begin, Date end) {
        long time = end.getTime() - begin.getTime();
        long day = time / (24 * 60 * 60 * 1000);
        long hour = (time / (60 * 60 * 1000) - day * 24);
        long minute = ((time / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long second = (time / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - minute * 60);
        return day + "天" + hour + "小时" + minute + "分" + second + "秒";
    }


    /**
     * 计算两个日期的相隔的天數
     * <ul>
     *     關於相隔和相差（getDistanceTime()）
     *     <li>开始日期20170824235959，结束日期20170825000000，差一秒，但其相差天數==0，相隔天數==1</li>
     *     <li>开始日期20170824110000，结束日期20170825105959，差一秒，但其相差天數==0，相隔天數==1</li>
     *     <li>开始日期20170824110000，结束日期20170825110000，相同，但其相差天數==1，相隔天數==1</li>
     * </ul>
     * @param begin 起始日期
     * @param end   终止日期
     * @return 相隔的天數
     */
    public static long getDistanceDay(Date begin, Date end) {
        String pattern = "yyyyMMdd";
        try {
            begin = DateUtils.parseDate(DateFormatUtils.format(begin, pattern), pattern);
            end = DateUtils.parseDate(DateFormatUtils.format(end, pattern), pattern);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = end.getTime() - begin.getTime();
        return time / (24 * 60 * 60 * 1000);
    }


    /**
     * 获取指定日期相隔一定天数后的日期
     * @see 该方法等效于org.apache.commons.lang3.time.DateUtils.addDays(startDate, days)
     * @param startDate 参照日期
     * @param days      相隔的天数，正数时则往后计算，负数则往前计算
     */
    public static Date getIncreaseDate(Date startDate, int days){
        final Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }


    /**
     * 根据日期获得星期
     * @param date 字符串格式的日期，可传入20170719或者2017-07-19
     * @return 星期日、星期一、星期二、星期三、星期四、星期五、星期六
     */
    public static String getWeekName(String dateStr){
        dateStr = dateStr.replaceAll("-", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("无效入参，格式应为20170719或者2017-07-19");
        }
        return getWeekName(date);
    }


    /**
     * 根据日期获得星期
     * @return 星期日、星期一、星期二、星期三、星期四、星期五、星期六
     */
    public static String getWeekName(Date date){
        String[] weekNames = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNameIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if(weekNameIndex < 0){
            weekNameIndex = 0;
        }
        return weekNames[weekNameIndex];
    }


    /**
     * 获取两个日期之间的所有日期列表
     * @param startDate 起始日期，格式为yyyyMMdd
     * @param  endDate  结束日期，格式为yyyyMMdd
     * @return 返回值不包含起始日期和结束日期
     */
    public static List<Integer> getCrossDayList(String startDate, String endDate){
        List<Integer> dayList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar startDay = Calendar.getInstance();
        Calendar endDay = Calendar.getInstance();
        try {
            startDay.setTime(sdf.parse(startDate));
            endDay.setTime(sdf.parse(endDate));
        } catch (ParseException e) {
            throw new IllegalArgumentException("无效入参：" + e.getMessage());
        }
        //起始日期大于等于结束日期，则返回空列表
        if(startDay.compareTo(endDay) >= 0){
            return dayList;
        }
        while(true){
            //日期+1，并判断是否到达了结束日
            startDay.add(Calendar.DATE, 1);
            if(startDay.compareTo(endDay) == 0){
                break;
            }
            dayList.add(Integer.parseInt(DateFormatUtils.format(startDay.getTime(), "yyyyMMdd")));
        }
        return dayList;
    }
}