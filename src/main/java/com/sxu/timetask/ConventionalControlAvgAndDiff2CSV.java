package com.sxu.timetask;

import com.sxu.constant.ConventionalControlConstant;
import com.sxu.controller.EmissionReduction;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class ConventionalControlAvgAndDiff2CSV extends TimerTask {
    public int period = ConventionalControlConstant.PERIOD;

    @Override
    public void run() {
        String[] industry = {"dianligongre", "gangtie", "guolu", "jiaohua", "others", "shiyouhuagong", "shuiniboli"};
        String[] scheme = {"1", "2", "3"};
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String startDate = sdf.format(new Date());
        String startTime = "0时";
        String timestamp = String.valueOf(System.currentTimeMillis());
        for (int i = 0; i < scheme.length; i++) {
            for (int j = 0; j < industry.length; j++) {

                String avgDayEmissionInPeriodCSVPath = "C:/ftproot/reduction_simulation/000" + scheme[i] + "/" + "emiss_" + industry[j] + "_000" + scheme[i] + ".csv";
                String diffDayEmissionInPeriodCSVPath = "C:/ftproot/reduction_diff_simulation/000" + scheme[i] + "/" + "diff_" + industry[j] + "_000" + scheme[i] + ".csv";
                //判断目录是否存在，不存在则创建
                File avgFile = new File(avgDayEmissionInPeriodCSVPath);
                File avgFileParent = avgFile.getParentFile();
                if (!avgFileParent.exists()) {
                    avgFileParent.mkdirs();
                }
                File diffFile = new File(diffDayEmissionInPeriodCSVPath);
                File diffFileParent = diffFile.getParentFile();
                if (!diffFileParent.exists()) {
                    diffFileParent.mkdirs();
                }
                //判断文件是否存在，存在则删除
                if (avgFile.exists()) {
                    avgFile.delete();
                }
                if (diffFile.exists()) {
                    diffFile.delete();
                }
                try {
                    EmissionReduction.avgAndDiffDayEmissionInPeriod2CSV(startDate, startTime, period, industry[j], scheme[i], timestamp, avgDayEmissionInPeriodCSVPath, diffDayEmissionInPeriodCSVPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
