package com.sxu.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static Date getSynDBTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 01);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        Date time = calendar.getTime();

        return time;
    }

    public static Date getSynCSVTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 05);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        Date time = calendar.getTime();

        return time;
    }
}
