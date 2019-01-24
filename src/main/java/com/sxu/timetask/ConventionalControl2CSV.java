//package com.sxu.timetask;
//
//import com.sxu.controller.EmissionReduction;
//import com.sxu.entity.DischargeAmount;
//import com.sxu.entity.EnterpriseOutletInfo;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Map;
//import java.util.TimerTask;
//
///**
// * 常规管控数据写入CSV
// * <p>
// * csv按日期，时间，行业分开
// */
//public class ConventionalControl2CSV extends TimerTask {
//
//    @Override
//    public void run() {
//        String[] industry = {"dianligongre", "gangtie", "guolu", "jiaohua", "others", "shiyouhuagong", "shuiniboli"};
//        String[] scheme = {"1", "2", "3"};
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
//        String date = sdf.format(new Date());
//        String timestamp = String.valueOf(System.currentTimeMillis());
//        try {
//            for (int i = 0; i < 24; i++) {
//                String time = i + "时";
//                for (int j = 0; j < scheme.length; j++) {
//                    Map<EnterpriseOutletInfo, DischargeAmount> conventionalControlMap = EmissionReduction.getConventionControlInfo(scheme[j], timestamp, date, time);
//                    for (int k = 0; k < industry.length; k++) {
//                        String conventionalControlCSVPath = "C:/ftproot/reduction/" + scheme[j] + "/" + date + time + "/" + industry[k] + ".csv";
//                        File file = new File(conventionalControlCSVPath);
//                        File fileParent = file.getParentFile();
//                        if (!fileParent.exists()) {
//                            fileParent.mkdirs();
//                        }
//                        EmissionReduction.generateConventionControlCSV(conventionalControlMap, industry[k], conventionalControlCSVPath);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
