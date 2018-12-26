package com.sxu.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sxu.dao.JDBCDao;
import com.sxu.entity.JsonEntity;
import com.sxu.util.HttpClientUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TaskControlInfoData {
    public void getOutletTaskControlInfo2DB(String url, String json) throws Exception {
        HttpClientUtil.httpPostWithJSON(url, json);
        JSONObject jo = JsonEntity.jsonEntity;
        System.out.println("jo" + jo);
        JSONArray results = jo.getJSONArray("results");
        if (results.size() > 0) {
            Connection conn = JDBCDao.getConn();
            PreparedStatement addTaskControlInfo = null;
            for (int i = 0; i < results.size(); i++) {
                JSONArray ja = results.getJSONArray(i);
                for (int j = 0; j < ja.size(); j++) {
                    JSONObject eachJO = ja.getJSONObject(j);
                    String enterpriseName = eachJO.getString("企业名称");
                    String outletName = eachJO.getString("监控点名称");
                    String date = eachJO.getString("日期");
                    String time = eachJO.getString("时间");
                    JSONObject taskJson = eachJO.getJSONObject("任务");
                    //氮
                    String taskNoxDischargeUnit = "0";
                    String taskNoxDischargeAmount = "0";
                    String taskNitrogenRemovalEquipmentEfficiencyUnit = "0";
                    String taskNitrogenRemovalEquipmentEfficiencyValue = "0";
                    if (taskJson.getJSONObject("氮氧化物") != null && !"{}".equals(taskJson.getJSONObject("氮氧化物").toString())) {
                        taskNoxDischargeUnit = taskJson.getJSONObject("氮氧化物").getJSONObject("排放量").getString("单位");
                        taskNoxDischargeAmount = taskJson.getJSONObject("氮氧化物").getJSONObject("排放量").getString("数值");
                    }
                    if (taskJson.getJSONObject("脱氮设备治理效率") != null && !"{}".equals(taskJson.getJSONObject("脱氮设备治理效率").toString())) {
                        taskNitrogenRemovalEquipmentEfficiencyUnit = taskJson.getJSONObject("脱氮设备治理效率").getString("单位");
                        taskNitrogenRemovalEquipmentEfficiencyValue = taskJson.getJSONObject("脱氮设备治理效率").getString("数值");
                    }

                    //硫
                    String taskSo2DischargeUnit = "0";
                    String taskSo2DischargeAmount = "0";
                    String taskDesulfurizationEquipmentEfficiencyUnit = "0";
                    String taskDesulfurizationEquipmentEfficiencyValue = "0";
                    if (taskJson.getJSONObject("二氧化硫") != null && !"{}".equals(taskJson.getJSONObject("二氧化硫").toString())) {
                        taskSo2DischargeUnit = taskJson.getJSONObject("二氧化硫").getJSONObject("排放量").getString("单位");
                        taskSo2DischargeAmount = taskJson.getJSONObject("二氧化硫").getJSONObject("排放量").getString("数值");
                    }
                    if (taskJson.getJSONObject("脱硫设备治理效率") != null && !"{}".equals(taskJson.getJSONObject("脱硫设备治理效率").toString())) {
                        taskDesulfurizationEquipmentEfficiencyUnit = taskJson.getJSONObject("脱硫设备治理效率").getString("单位");
                        taskDesulfurizationEquipmentEfficiencyValue = taskJson.getJSONObject("脱硫设备治理效率").getString("单位");
                    }
                    //尘
                    String taskDustDischargeUnit = "0";
                    String taskDustDischargeAmount = "0";
                    String taskDustRemovalEquipmentEfficiencyUnit = "0";
                    String taskDustRemovalEquipmentEfficiencyValue = "0";
                    if (taskJson.getJSONObject("烟尘") != null && !"{}".equals(taskJson.getJSONObject("烟尘").toString())) {
                        taskDustDischargeUnit = taskJson.getJSONObject("烟尘").getJSONObject("排放量").getString("单位");
                        taskDustDischargeAmount = taskJson.getJSONObject("烟尘").getJSONObject("排放量").getString("数值");
                    }
                    if (taskJson.getJSONObject("除尘设备治理效率") != null && !"{}".equals(taskJson.getJSONObject("除尘设备治理效率").toString())) {
                        taskDustRemovalEquipmentEfficiencyUnit = taskJson.getJSONObject("除尘设备治理效率").getString("单位");
                        taskDustRemovalEquipmentEfficiencyValue = taskJson.getJSONObject("除尘设备治理效率").getString("数值");
                    }
                    //限产量
                    String limitedYieldUnit = "0";
                    String limitedYieldValue = "0";
                    if (taskJson.getJSONObject("限产量") != null && !"{}".equals(taskJson.getJSONObject("限产量").toString())) {
                        limitedYieldUnit = taskJson.getJSONObject("限产量").getString("单位");
                        limitedYieldValue = taskJson.getJSONObject("限产量").getString("数值");
                    }

                    String isStopProduction = taskJson.getString("是否停产");

                    addTaskControlInfo = conn.prepareStatement("insert into task_control_info values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    addTaskControlInfo.setString(1, enterpriseName);
                    addTaskControlInfo.setString(2, outletName);
                    addTaskControlInfo.setString(3, date);
                    addTaskControlInfo.setString(4, time);
                    addTaskControlInfo.setString(5, taskNoxDischargeUnit);
                    addTaskControlInfo.setString(6, taskNoxDischargeAmount);
                    addTaskControlInfo.setString(7, taskNitrogenRemovalEquipmentEfficiencyUnit);
                    addTaskControlInfo.setString(8, taskNitrogenRemovalEquipmentEfficiencyValue);
                    addTaskControlInfo.setString(9, taskSo2DischargeUnit);
                    addTaskControlInfo.setString(10, taskSo2DischargeAmount);
                    addTaskControlInfo.setString(11, taskDesulfurizationEquipmentEfficiencyUnit);
                    addTaskControlInfo.setString(12, taskDesulfurizationEquipmentEfficiencyValue);
                    addTaskControlInfo.setString(13, taskDustDischargeUnit);
                    addTaskControlInfo.setString(14, taskDustDischargeAmount);
                    addTaskControlInfo.setString(15, taskDustRemovalEquipmentEfficiencyUnit);
                    addTaskControlInfo.setString(16, taskDustRemovalEquipmentEfficiencyValue);
                    addTaskControlInfo.setString(17, limitedYieldUnit);
                    addTaskControlInfo.setString(18, limitedYieldValue);
                    addTaskControlInfo.setString(19, isStopProduction);
                    addTaskControlInfo.execute();
                }
            }
            conn.close();
            System.out.println("连接关闭");
        }
    }

    /**
     * 根据企业名和排口，把任务管控数据入库
     */
    public String generateTaskControlInfoRequestJson(String method, String enterpriseName, String outletName) {
        String jsonStr = "{\n" +
                "\"async\": 0,\n" +
                "\"callback\": \"\",\n" +
                "\"method\": \"" + method + "\",\n" +
                "\"param\": {\"企业名称\" : \"" + enterpriseName + "\",\n" +
                "\t\"监控点名称\" : \"" + outletName + "\",\n" +
                "\t\"起始日期\" : \"2018年12月15日\",\n" +
                "\t\"天数\" : \"1\"}\n" +
                "}";
        return jsonStr;
    }

    public void putDailyOutletTaskControlInfo2DB() throws Exception {
        String url = "http://119.90.57.34:9680/channel/do";
        HashMap<String, HashSet<String>> enterpriseOutletMap = EnterpriseOutletInfoData.getEnterpriseOutletMap();
        for (Map.Entry<String, HashSet<String>> entry : enterpriseOutletMap.entrySet()) {
            String enterpriseName = entry.getKey();
            HashSet<String> hs = entry.getValue();
            for (String str : hs) {
                String outletName = str;
                String jsonStr = generateTaskControlInfoRequestJson("任务管控", enterpriseName, outletName);
                getOutletTaskControlInfo2DB(url, jsonStr);
            }
        }
    }

    public static void main(String[] args) throws Exception {
//        String url = "http://119.90.57.34:9680/channel/do";
//        String jsonStr = new TaskControlInfoData().generateTaskControlInfoRequestJson("任务管控","山西襄矿集团沁县华安焦化有限公司","脱硫脱硝设施出口");
//        new TaskControlInfoData().getOutletTaskControlInfo2DB(url, jsonStr);
        new TaskControlInfoData().putDailyOutletTaskControlInfo2DB();
    }
}
