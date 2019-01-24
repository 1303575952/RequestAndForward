package com.sxu.timetask;

import com.sxu.constant.ConventionalControlConstant;
import com.sxu.data.ConventionalControlInfoData;
import com.sxu.util.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

/**
 * post清华常规管控接口
 */
public class ConventionalControlData2DB extends TimerTask {
    public int period = ConventionalControlConstant.PERIOD;

    @Override
    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String date = sdf.format(new Date());
        ConventionalControlInfoData conventionalControlInfoData = new ConventionalControlInfoData();
        try {
            /*for (int i = 0; i < period; i++) {
                date = TimeUtil.dateDayIncrement(date, i);
                conventionalControlInfoData.putDailyOutletConventionalControlInfo2DB(date);
            }*/
            date = TimeUtil.dateDayIncrement(date, period);
            conventionalControlInfoData.putDailyOutletConventionalControlInfo2DB(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
