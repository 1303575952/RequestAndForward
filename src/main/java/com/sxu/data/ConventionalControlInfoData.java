package com.sxu.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sxu.dao.JDBCDao;
import com.sxu.entity.JsonEntity;
import com.sxu.util.HttpClientUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConventionalControlInfoData {
    public void getConventionalControlInfoJson(String url, String json) throws Exception {
        HttpClientUtil.httpPostWithJSON(url, json);
        JSONObject jo = JsonEntity.jsonEntity;
        System.out.println("jo" + jo);
        JSONArray results = jo.getJSONArray("results");
        if (results.size() > 0) {
            ResultSet rs = null;
            Connection conn = JDBCDao.getConn();
            PreparedStatement addConventionalControlInfo = null;
            for (int i = 0; i < results.size(); i++) {
                JSONArray ja = results.getJSONArray(i);
                for (int j = 0; j < ja.size(); j++) {
                    JSONObject eachJO = ja.getJSONObject(j);
                    String enterpriseName = eachJO.getString("企业名称");
                    String outletName = eachJO.getString("监控点名称");
                    String date = eachJO.getString("日期");
                    String time = eachJO.getString("时间");
                    JSONObject feasibleJson = eachJO.getJSONObject("可行");
                    //氮
                    String feasibleNoxDischargeUnit = feasibleJson.getJSONObject("氮氧化物").getJSONObject("排放量").getString("单位");
                    String feasibleNoxDischargeAmount = feasibleJson.getJSONObject("氮氧化物").getJSONObject("排放量").getString("数值");
                    String feasibleNitrogenRemovalEfficiencyUnit = feasibleJson.getJSONObject("脱氮效率").getString("单位");
                    String feasibleNitrogenRemovalEfficiencyValue = feasibleJson.getJSONObject("脱氮效率").getString("数值");
                    String feasibleNitrogenRemovalCostUnit = feasibleJson.getJSONObject("脱氮所需成本").getString("单位");
                    String feasibleNitrogenRemovalCostValue = feasibleJson.getJSONObject("脱氮所需成本").getString("数值");
                    //硫
                    String feasibleSo2DischargeUnit = feasibleJson.getJSONObject("二氧化硫").getJSONObject("排放量").getString("单位");
                    String feasibleSo2DischargeAmount = feasibleJson.getJSONObject("二氧化硫").getJSONObject("排放量").getString("数值");
                    String feasibleDesulfurizationEfficiencyUnit = feasibleJson.getJSONObject("脱硫效率").getString("单位");
                    String feasibleDesulfurizationEfficiencyValue = feasibleJson.getJSONObject("脱硫效率").getString("单位");
                    String feasibleDesulfurizationCostUnit = feasibleJson.getJSONObject("脱硫所需成本").getString("单位");
                    String feasibleDesulfurizationCostValue = feasibleJson.getJSONObject("脱硫所需成本").getString("数值");
                    //尘
                    String feasibleDustDischargeUnit = feasibleJson.getJSONObject("烟尘").getJSONObject("排放量").getString("单位");
                    String feasibleDustDischargeAmount = feasibleJson.getJSONObject("烟尘").getJSONObject("排放量").getString("数值");
                    String feasibleDustRemovalEfficiencyUnit = feasibleJson.getJSONObject("除尘效率").getString("单位");
                    String feasibleDustRemovalEfficiencyValue = feasibleJson.getJSONObject("除尘效率").getString("数值");
                    String feasibleDustRemovalCostUnit = feasibleJson.getJSONObject("除尘所需成本").getString("单位");
                    String feasibleDustRemovalCostValue = feasibleJson.getJSONObject("除尘所需成本").getString("数值");

                    addConventionalControlInfo = conn.prepareStatement("insert into conventional_control_info values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    addConventionalControlInfo.setString(1,enterpriseName);
                    addConventionalControlInfo.setString(2,outletName);
                    addConventionalControlInfo.setString(3,date);
                    addConventionalControlInfo.setString(4,time);
                    addConventionalControlInfo.setString(5,feasibleNoxDischargeUnit);
                    addConventionalControlInfo.setString(6,feasibleNoxDischargeAmount);
                    addConventionalControlInfo.setString(7,feasibleNitrogenRemovalEfficiencyUnit);
                    addConventionalControlInfo.setString(8,feasibleNitrogenRemovalEfficiencyValue);
                    addConventionalControlInfo.setString(9,feasibleNitrogenRemovalCostUnit);
                    addConventionalControlInfo.setString(10,feasibleNitrogenRemovalCostValue);
                    addConventionalControlInfo.setString(11,feasibleSo2DischargeUnit);
                    addConventionalControlInfo.setString(12,feasibleSo2DischargeAmount);
                    addConventionalControlInfo.setString(13,feasibleDesulfurizationEfficiencyUnit);
                    addConventionalControlInfo.setString(14,feasibleDesulfurizationEfficiencyValue);
                    addConventionalControlInfo.setString(15,feasibleDesulfurizationCostUnit);
                    addConventionalControlInfo.setString(16,feasibleDesulfurizationCostValue);
                    addConventionalControlInfo.setString(17,feasibleDustDischargeUnit);
                    addConventionalControlInfo.setString(18,feasibleDustDischargeAmount);
                    addConventionalControlInfo.setString(19,feasibleDustRemovalEfficiencyUnit);
                    addConventionalControlInfo.setString(20,feasibleDustRemovalEfficiencyValue);
                    addConventionalControlInfo.setString(21,feasibleDustRemovalCostUnit);
                    addConventionalControlInfo.setString(22,feasibleDustRemovalCostValue);
                    addConventionalControlInfo.execute();
                }
            }
            conn.close();
            System.out.println("连接关闭");
        }
    }

    public static void main(String[] args) throws Exception {
        String url = "http://119.90.57.34:9680/channel/do";
        String jsonStr = "{\n" +
                "\"async\":0,\n" +
                "\"callback\":\"\",\n" +
                "\"method\":\"常规管控\",\n" +
                "\"param\": {\"企业名称\":\"沁源县兴茂煤化有限公司\",\n" +
                "\"监控点名称\":\"焦炉烟囱排口\",\n" +
                "\"起始日期\":\"2018年12月16日\",\n" +
                "\"天数\":\"1\"}\n" +
                "}";
        new ConventionalControlInfoData().getConventionalControlInfoJson(url, jsonStr);
    }
}
