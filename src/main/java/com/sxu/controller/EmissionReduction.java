package com.sxu.controller;

import com.sxu.dao.JDBCDao;
import com.sxu.data.EnterpriseInfoData;
import com.sxu.entity.DischargeAmount;
import com.sxu.entity.EnterpriseOutletInfo;
import com.sxu.entity.EnterpriseProperty;
import com.sxu.util.TimeUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 管控方案下的排放数据写csv
 */
public class EmissionReduction {

    /**
     * 生成某【管控方案、管控方案生成时间、管控方案开始时间】下每一个排口的排放值
     * 找到同一【管控方案、管控方案生成时间、管控方案开始时间】的所有企业排口排放數據，参数:【管控方案、管控方案生成时间、管控方案开始时间（日期、时间）】
     */
    public static Map<EnterpriseOutletInfo, DischargeAmount> getConventionControlInfo(String scheme, String timestamp, String date, String time) throws Exception {
        ResultSet rs;
        Connection conn = JDBCDao.getConn();
        PreparedStatement selectConventionControlInfo =
                conn.prepareStatement("select enterprise_name,outlet_name,feasible_nox_discharge_amount,feasible_so2_discharge_amount from conventional_control_info where scheme=? and date=? and time=?");
        selectConventionControlInfo.setString(1, scheme);
        selectConventionControlInfo.setString(2, date);
        selectConventionControlInfo.setString(3, time);
        //System.out.println(selectConventionControlInfo.toString());
        rs = selectConventionControlInfo.executeQuery();

        Map<EnterpriseOutletInfo, DischargeAmount> conventionalControlMap = new HashMap<EnterpriseOutletInfo, DischargeAmount>();

        while (rs.next()) {
            String enterpriseName = rs.getString("enterprise_name");
            String outletName = rs.getString("outlet_name");
            Float feasibleNoxDischargeAmount = Float.valueOf(rs.getString("feasible_nox_discharge_amount"));
            Float feasibleSo2DischargeAmount = Float.valueOf(rs.getString("feasible_so2_discharge_amount"));
            conventionalControlMap.put(new EnterpriseOutletInfo(enterpriseName, outletName), new DischargeAmount(feasibleNoxDischargeAmount, feasibleSo2DischargeAmount));

        }
        selectConventionControlInfo.close();
        conn.close();
        rs.close();
        return conventionalControlMap;
    }


    /**
     * @param conventionalControlMap   某种常规减排排放情况
     * @param industry                 行业
     * @param conventionControlCSVPath 路径
     */
    public static void generateConventionControlCSV(Map<EnterpriseOutletInfo, DischargeAmount> conventionalControlMap, String industry, String conventionControlCSVPath) throws Exception {
        String enterpriseLocationFilePath = System.getProperty("user.dir") + "/src/main/resources/data/enterprise_location.csv";
        Map<String, EnterpriseProperty> enterprisePropertyMap = EnterpriseInfoData.getEnterprisePropertyMap(enterpriseLocationFilePath);

        String[] headers = new String[]{"nrow", "ncol", "BC", "CO", "NOx", "OC", "PM10", "PM2.5", "SO2", "VOC", "CO2", "NH3", "TSP"};
        String[][] csvOut = new String[4536][13];
        for (int i = 0; i < csvOut.length; i++) {
            for (int j = 0; j < csvOut[0].length; j++) {
                csvOut[i][j] = "0";
            }
        }
        for (int i = 0; i < csvOut.length; i++) {
            csvOut[i][0] = String.valueOf(i / 63 + 1);
            csvOut[i][1] = String.valueOf(i % 63 + 1);
        }
        for (Map.Entry<EnterpriseOutletInfo, DischargeAmount> entry : conventionalControlMap.entrySet()) {
            EnterpriseOutletInfo eoi = entry.getKey();
            DischargeAmount da = entry.getValue();
            String enterpriseName = eoi.getEnterpriseName();
            String outletName = eoi.getOutletName();
            Float feasibleNoxDischargeAmount = Float.valueOf(da.getFeasibleNoxDischargeAmount());
            Float feasibleSo2DischargeAmount = Float.valueOf(da.getFeasibleSo2DischargeAmount());
            //通过企业名找到行业，企业位置ID
            //先确定企业行业，才能确定写入此文件。确定行业符合则排放数据写入
            //企业位置ID确定写入的位置
            if (industry.equals(enterprisePropertyMap.get(enterpriseName).getIndustry())) {
                Integer cellId = Integer.valueOf(enterprisePropertyMap.get(enterpriseName).getIndustry());
                csvOut[cellId + 1][4] = String.valueOf(Float.valueOf(csvOut[cellId + 1][4]) + feasibleNoxDischargeAmount);
                csvOut[cellId + 1][8] = String.valueOf(Float.valueOf(csvOut[cellId + 1][8]) + feasibleSo2DischargeAmount);
            }
        }
        //写到文件
        EnterpriseInfoData.writeCsv(headers, csvOut, conventionControlCSVPath);
    }

    /**
     * 从startDate+startTime开始的period天内的排放数据，period个表格，每个表格存一天的年化总量（不存其他数据）
     * @param startDate
     * @param startTime
     * @param period
     * @param industry
     * @param scheme
     * @param timestamp
     * @return
     * @throws Exception
     */

    public static String[][][] generateConventionControlArray(String startDate, String startTime, int period, String industry, String scheme, String timestamp) throws Exception {
        String[][][] perDayEmissionInPeriod = new String[period][4536][11];
        String enterpriseLocationFilePath = System.getProperty("user.dir") + "/src/main/resources/data/enterprise_location.csv";
        Map<String, EnterpriseProperty> enterprisePropertyMap = EnterpriseInfoData.getEnterprisePropertyMap(enterpriseLocationFilePath);
        //enterprisePropertyMap是否有值？是
        /*System.out.println("enterprisePropertyMap:" + enterprisePropertyMap.size());
        for (Map.Entry<String, EnterpriseProperty> entry : enterprisePropertyMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().getIndustry());
        }*/
        //初始化为零
        for (int i = 0; i < period; i++) {
            for (int j = 0; j < perDayEmissionInPeriod[0].length; j++) {
                for (int k = 0; k < perDayEmissionInPeriod[0][0].length; k++) {
                    perDayEmissionInPeriod[i][j][k] = "0";
                }
            }
        }
        //把每天的数据求和
        for (int i = 0; i < period; i++) {
            String date = TimeUtil.dateDayIncrement(startDate, i);
            //24小时的数据累加
            for (int j = 0; j < 24; j++) {
                String time = "0-12时";
                if (j < 12) {
                    time = "0-12时";
                } else {
                    time = "12-24时";
                }
                Map<EnterpriseOutletInfo, DischargeAmount> conventionControlInfoMap = EmissionReduction.getConventionControlInfo(scheme, timestamp, date, time);
                //System.out.println("conventionControlInfoMap size=========="+conventionControlInfoMap.size());
                //打印查库数据
                for (Map.Entry<EnterpriseOutletInfo, DischargeAmount> entry : conventionControlInfoMap.entrySet()) {
                    EnterpriseOutletInfo eoi = entry.getKey();
                    DischargeAmount da = entry.getValue();
                    String enterpriseName = eoi.getEnterpriseName();
                    String outletName = eoi.getOutletName();
                    Float feasibleNoxDischargeAmount = Float.valueOf(da.getFeasibleNoxDischargeAmount());
                    Float feasibleSo2DischargeAmount = Float.valueOf(da.getFeasibleSo2DischargeAmount());
                    System.out.println("enterpriseName:" + enterpriseName + ";outletName:" + outletName + ";feasibleNoxDischargeAmount:" + feasibleNoxDischargeAmount + ";feasibleSo2DischargeAmount:" + feasibleSo2DischargeAmount);
                }
                for (Map.Entry<EnterpriseOutletInfo, DischargeAmount> entry : conventionControlInfoMap.entrySet()) {
                    EnterpriseOutletInfo eoi = entry.getKey();
                    DischargeAmount da = entry.getValue();
                    String enterpriseName = eoi.getEnterpriseName();
                    String outletName = eoi.getOutletName();
                    Float feasibleNoxDischargeAmount = Float.valueOf(da.getFeasibleNoxDischargeAmount());
                    Float feasibleSo2DischargeAmount = Float.valueOf(da.getFeasibleSo2DischargeAmount());
                    //enterprisePropertyMap的值
                    //System.out.println("enterpriseName:" + enterpriseName + ";outletName:" + outletName + ";feasibleNoxDischargeAmount:" + feasibleNoxDischargeAmount + ";feasibleSo2DischargeAmount:" + feasibleSo2DischargeAmount);
                    //通过企业名找到行业，企业位置ID
                    //先确定企业行业，才能确定写入此文件。确定行业符合则排放数据写入
                    //企业位置ID确定写入的位置
                    /*if (enterprisePropertyMap.get(enterpriseName) != null) {
                        System.out.println("industry:" + industry);
                        System.out.println("getIndustry:" + enterprisePropertyMap.get(enterpriseName).getIndustry());
                    }*/
                    if ((enterprisePropertyMap.get(enterpriseName) != null) && (industry.equals(enterprisePropertyMap.get(enterpriseName).getIndustry()))) {
                        Integer cellId = Integer.valueOf(enterprisePropertyMap.get(enterpriseName).getCellId());
                        System.out.println("cellId:" + cellId);
                        if (cellId.intValue() >= 0) {
                            perDayEmissionInPeriod[i][cellId + 1][2] = String.valueOf(Float.valueOf(perDayEmissionInPeriod[i][cellId + 1][2]) + feasibleNoxDischargeAmount);
                            perDayEmissionInPeriod[i][cellId + 1][6] = String.valueOf(Float.valueOf(perDayEmissionInPeriod[i][cellId + 1][6]) + feasibleSo2DischargeAmount);
                        }
                        //System.out.println("==============" + perDayEmissionInPeriod[i][cellId + 1][2] + " " + perDayEmissionInPeriod[i][cellId + 1][6]);
                    }
                }
            }
        }
        //计算perDayEmissionInPeriod是否正常
        /*for(int i=0;i<perDayEmissionInPeriod[0].length;i++){
            for(int j=0;j<perDayEmissionInPeriod[0][0].length;j++){
                System.out.print(perDayEmissionInPeriod[0][i][j]+" ");
            }
            System.out.println();
        }*/
        //计算年化
        for (int i = 0; i < period; i++) {
            for (int j = 0; j < perDayEmissionInPeriod[0].length; j++) {
                for (int k = 0; k < perDayEmissionInPeriod[0][0].length; k++) {
                    perDayEmissionInPeriod[i][j][k] = String.valueOf(Float.valueOf(perDayEmissionInPeriod[i][j][k]) * 365.0f / period / 1000.0f);
                }
            }
        }
        return perDayEmissionInPeriod;
    }

    /**
     * period时间内的平均值
     *
     * @param perDayEmissionInPeriod
     * @return
     */
    public static String[][] generateConventionControlAvgArray(String[][][] perDayEmissionInPeriod) {
        String[][] avgDayEmissionInPeriod = new String[perDayEmissionInPeriod[0].length][perDayEmissionInPeriod[0][0].length];
        for (int i = 0; i < avgDayEmissionInPeriod.length; i++) {
            for (int j = 0; j < avgDayEmissionInPeriod[0].length; j++) {
                float sum = 0;
                for (int k = 0; k < perDayEmissionInPeriod.length; k++) {
                    sum = sum + Float.valueOf(perDayEmissionInPeriod[k][i][j]);
                }
                avgDayEmissionInPeriod[i][j] = String.valueOf(sum / perDayEmissionInPeriod.length);
            }
        }
        return avgDayEmissionInPeriod;
    }

    /**
     * 差值
     *
     * @param perDayEmissionInPeriod
     * @param avgDayEmissionInPeriod
     * @return
     */
    public static String[][][] generateDifferenceConventionControlArray(String[][][] perDayEmissionInPeriod, String[][] avgDayEmissionInPeriod) {
        String[][][] diffPerDayEmissionInPeriod = new String[perDayEmissionInPeriod.length][perDayEmissionInPeriod[0].length][perDayEmissionInPeriod[0][0].length];
        for (int i = 0; i < diffPerDayEmissionInPeriod.length; i++) {
            for (int j = 0; j < diffPerDayEmissionInPeriod[0].length; j++) {
                for (int k = 0; k < diffPerDayEmissionInPeriod[0][0].length; k++) {
                    diffPerDayEmissionInPeriod[i][j][k] = String.valueOf(Float.valueOf(perDayEmissionInPeriod[i][j][k]) - Float.valueOf(avgDayEmissionInPeriod[j][k]));
                }
            }
        }
        return diffPerDayEmissionInPeriod;
    }

    /**
     * 均值写入csv
     * @param startDate
     * @param startTime
     * @param period
     * @param industry
     * @param scheme
     * @param timestamp
     * @param avgDayEmissionInPeriodCSVPath
     * @param diffDayEmissionInPeriodCSVPath
     * @throws Exception
     */
    public static void avgAndDiffDayEmissionInPeriod2CSV(String startDate, String startTime, int period, String industry, String scheme, String timestamp, String avgDayEmissionInPeriodCSVPath, String diffDayEmissionInPeriodCSVPath) throws Exception {
        String[][][] perDayEmissionInPeriod = EmissionReduction.generateConventionControlArray(startDate, startTime, period, industry, scheme, timestamp);
        String[][] avgDayEmissionInPeriod = EmissionReduction.generateConventionControlAvgArray(perDayEmissionInPeriod);
        String[][][] diffPerDayEmissionInPeriod = EmissionReduction.generateDifferenceConventionControlArray(perDayEmissionInPeriod, avgDayEmissionInPeriod);

        String[] headers = new String[]{TimeUtil.dateDeleteChinese(startDate) + "00"};
        String[][] csvOut = new String[4537][13];

        csvOut[0][0] = "nrow";
        csvOut[0][1] = "ncol";
        csvOut[0][2] = "BC";
        csvOut[0][3] = "CO";
        csvOut[0][4] = "NOx";
        csvOut[0][5] = "OC";
        csvOut[0][6] = "PM10";
        csvOut[0][7] = "PM2.5";
        csvOut[0][8] = "SO2";
        csvOut[0][9] = "VOC";
        csvOut[0][10] = "CO2";
        csvOut[0][11] = "NH3";
        csvOut[0][12] = "TSP";

        for (int i = 1; i < csvOut.length; i++) {
            csvOut[i][0] = String.valueOf((i - 1) / 63 + 1);
            csvOut[i][1] = String.valueOf((i - 1) % 63 + 1);
        }

        String[][] csvOutAvg = new String[4537][13];
        for (int i = 0; i < csvOut.length; i++) {
            for (int j = 0; j < csvOut[0].length; j++) {
                csvOutAvg[i][j] = csvOut[i][j];
            }
        }
        for (int i = 1; i < csvOutAvg.length; i++) {
            for (int j = 2; j < csvOutAvg[0].length; j++) {
                csvOutAvg[i][j] = avgDayEmissionInPeriod[i - 1][j - 2];
            }
        }

        String[][] csvOutDiff = new String[4537][13];
        for (int i = 0; i < csvOut.length; i++) {
            for (int j = 0; j < csvOut[0].length; j++) {
                csvOutDiff[i][j] = csvOut[i][j];
            }
        }
        for (int i = 1; i < csvOutDiff.length; i++) {
            for (int j = 2; j < csvOutDiff[0].length; j++) {
                csvOutDiff[i][j] = "";
                for (int k = 0; k < diffPerDayEmissionInPeriod.length; k++) {
                    csvOutDiff[i][j] += diffPerDayEmissionInPeriod[k][i - 1][j - 2] + ";";
                }
            }
        }

        EnterpriseInfoData.writeCsv(headers, csvOutAvg, avgDayEmissionInPeriodCSVPath);
        EnterpriseInfoData.writeCsv(headers, csvOutDiff, diffDayEmissionInPeriodCSVPath);
    }

}
