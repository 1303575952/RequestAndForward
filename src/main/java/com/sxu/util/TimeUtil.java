package com.sxu.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 两个任务执行的时间
 */
public class TimeUtil {
    public synchronized static Date getSynDBTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 01);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        Date time = calendar.getTime();

        return time;
    }

    public synchronized static Date getSynCSVTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 05);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        Date time = calendar.getTime();

        return time;
    }

    public static Date get2DBDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, 01);
        calendar.set(Calendar.DAY_OF_MONTH, 01);
        calendar.set(Calendar.HOUR_OF_DAY, 05);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        Date time = calendar.getTime();

        return time;
    }

    /**
     * DB数据转csv定时任务的时间
     * @return
     */
    public static Date get2CSVDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, 00);
        calendar.set(Calendar.DAY_OF_MONTH, 25);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        Date time = calendar.getTime();
        return time;
    }

    /**
     * date加一天
     *
     * @param date
     * @return
     */
    public static String dateDayIncrement(String date, int amount) throws Exception {
        String newDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date sDate = sdf.parse(date);
        Calendar c = Calendar.getInstance();
        c.setTime(sDate);
        c.add(Calendar.DAY_OF_MONTH, amount);
        sDate = c.getTime();
        newDate = sdf.format(sDate);
        return newDate;

    }

    public static String dateDeleteChinese(String date){
        return date.replaceAll("[\u4e00-\u9fa5]+", "");
    }

    public static String timeHourIncrement(String time, int amount) {
        String newTime = Integer.valueOf(time.substring(0, time.length() - 1)) + amount + "时";
        return newTime;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(TimeUtil.dateDayIncrement("2018年01月01日", 3));
        System.out.println(TimeUtil.timeHourIncrement("0时", 7));
    }
}
