package com.sxu.controller;

import com.sxu.dao.JDBCDao;
import com.sxu.data.EnterpriseInfoData;
import com.sxu.entity.DischargeAmount;
import com.sxu.entity.EnterpriseOutletInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 减排情况
 */
public class EmissionReduction {

    /**
     * 找到同一时间同一减排方案的所有企业，参数:日期，时间，减排方案
     * 清华提供的常规管控三种方案，每一条数据里各种污染物的效率是不是都是相同的？这里假设是相同的
     */
    public Map<EnterpriseOutletInfo, DischargeAmount> getConventionControlInfo(String date, String time, String removalEfficiency) throws Exception {
        ResultSet rs = null;
        Connection conn = JDBCDao.getConn();
        PreparedStatement selectConventionControlInfo =
                conn.prepareStatement("select enterprise_name,outlet_name,feasible_nox_discharge_amount,feasible_so2_discharge_amount from conventional_control_info where date=? and time=? and feasible_nitrogen_removal_efficiency_value=? and feasible_desulfurization_efficiency_value=?");
        selectConventionControlInfo.setString(1, date);
        selectConventionControlInfo.setString(2, time);
        selectConventionControlInfo.setString(3, removalEfficiency);
        selectConventionControlInfo.setString(4, removalEfficiency);
        rs = selectConventionControlInfo.executeQuery();

        Map<EnterpriseOutletInfo, DischargeAmount> conventionalControlMap = new HashMap<EnterpriseOutletInfo, DischargeAmount>();

        while (rs.next()) {
            String enterpriseName = rs.getString("enterprise_name");
            String outletName = rs.getString("outlet_name");
            Float feasibleNoxDischargeAmount = Float.valueOf(rs.getString("feasible_nox_discharge_amount"));
            Float feasibleSo2DischargeAmount = Float.valueOf(rs.getString("feasible_so2_discharge_amount"));
            conventionalControlMap.put(new EnterpriseOutletInfo(enterpriseName, outletName), new DischargeAmount(feasibleNoxDischargeAmount, feasibleSo2DischargeAmount));

        }
        return conventionalControlMap;
    }


    /**
     * @param conventionalControlMap   某种常规减排排放情况
     * @param industry                 行业
     * @param conventionControlCSVPath 路径
     */
    public void generateConventionControlCSV(Map<EnterpriseOutletInfo, DischargeAmount> conventionalControlMap, String industry, String conventionControlCSVPath) throws Exception {
        String[] headers = new String[]{"nrow", "ncol", "BC", "CO", "NOx", "OC", "PM10", "PM2.5", "SO2", "VOC", "CO2", "NH3", "TSP"};
        String[][] csvOut = new String[4536][13];
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
            if (industry.equals(EnterpriseInfoData.enterprisePropertyMap.get(enterpriseName).getIndustry())) {
                Integer cellId = Integer.valueOf(EnterpriseInfoData.enterprisePropertyMap.get(enterpriseName).getIndustry());
                csvOut[cellId + 1][4] += feasibleNoxDischargeAmount;
                csvOut[cellId + 1][8] += feasibleSo2DischargeAmount;
            }
        }
        //写到文件
        EnterpriseInfoData.writeCsv(headers, csvOut, conventionControlCSVPath);
    }
}
