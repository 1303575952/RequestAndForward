package com.sxu.timetask;

import com.sxu.controller.EmissionReduction;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class ConventionalControlAvgAndDiff2CSV extends TimerTask {
    public int period = 3;

    @Override
    public void run() {
        String[] industry = {"dianligongre", "gangtie", "guolu", "jiaohua", "others", "shiyouhuagong", "shuiniboli"};
        String[] removalEfficiency = {"25", "50", "75"};
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String startDate = sdf.format(new Date());
        String startTime = "0时";
        for (int i = 0; i < removalEfficiency.length; i++) {
            for (int j = 0; j < industry.length; j++) {
                String removalId = "";
                if ("25".equals(removalEfficiency[i])) {
                    removalId = "0001";
                } else if ("50".equals(removalEfficiency[i])) {
                    removalId = "0002";
                } else if ("75".equals(removalEfficiency[i])) {
                    removalId = "0003";
                }
                String avgDayEmissionInPeriodCSVPath = "C:/ftproot/reduction/" + removalId + "/" + "emiss_" + industry[j] + "_" + removalId + ".csv";
                String diffDayEmissionInPeriodCSVPath = "C:/ftproot/reduction_diff/" + removalId + "/" + "diff_" + industry[j] + "_" + removalId + ".csv";
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
                    EmissionReduction.avgAndDiffDayEmissionInPeriod2CSV(startDate, startTime, period, industry[j], removalEfficiency[i], avgDayEmissionInPeriodCSVPath, diffDayEmissionInPeriodCSVPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
