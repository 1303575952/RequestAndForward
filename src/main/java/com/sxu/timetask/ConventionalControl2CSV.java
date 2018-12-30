package com.sxu.timetask;

import com.sxu.controller.EmissionReduction;
import com.sxu.entity.DischargeAmount;
import com.sxu.entity.EnterpriseOutletInfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimerTask;

/**
 * 常规管控数据写入CSV
 * <p>
 * csv按日期，时间，行业分开
 */
public class ConventionalControl2CSV extends TimerTask {

    @Override
    public void run() {
        String[] industry = {"dianligongre", "gantie", "guolu", "jiaohua", "others", "shiyouhuagong", "shuiniboli"};
        String[] removalEfficiency = {"25", "50", "75"};
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String date = sdf.format(new Date());
        try {
            for (int i = 0; i < 24; i++) {
                String time = i + "时";
                for (int j = 0; j < removalEfficiency.length; j++) {
                    String removalId = "";
                    if ("25".equals(removalEfficiency[j])) {
                        removalId = "1";
                    } else if ("50".equals(removalEfficiency[j])) {
                        removalId = "2";
                    } else if ("75".equals(removalEfficiency[j])) {
                        removalId = "3";
                    }
                    Map<EnterpriseOutletInfo, DischargeAmount> conventionalControlMap = EmissionReduction.getConventionControlInfo(date, time, removalEfficiency[j]);
                    for (int k = 0; k < industry.length; k++) {
                        String conventionalControlCSVPath = "C:/ftproot/reduction/" + date + time + "/" + industry[k] + removalId + ".csv";
                        File file = new File(conventionalControlCSVPath);
                        File fileParent = file.getParentFile();
                        if (!fileParent.exists()) {
                            fileParent.mkdirs();
                        }
                        EmissionReduction.generateConventionControlCSV(conventionalControlMap, industry[k], conventionalControlCSVPath);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
