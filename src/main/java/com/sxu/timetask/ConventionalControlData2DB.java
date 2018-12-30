package com.sxu.timetask;

import com.sxu.data.ConventionalControlInfoData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

/**
 * post清华常规管控接口
 */
public class ConventionalControlData2DB extends TimerTask {

    @Override
    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String date = sdf.format(new Date());
        try {
            new ConventionalControlInfoData().putDailyOutletConventionalControlInfo2DB(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
