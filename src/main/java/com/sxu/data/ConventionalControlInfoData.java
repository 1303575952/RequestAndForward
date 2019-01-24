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

/**
 * 企业排口数据入库
 */
public class ConventionalControlInfoData {
    /**
     * 每一个监控点数据常规管控数据入库
     *
     * @param url  接口地址
     * @param json 构造好的请求json
     * @throws Exception
     */
    public void getOutletConventionalControlInfo2DB(String url, String json) throws Exception {
        HttpClientUtil.httpPostWithJSON(url, json);
        JSONObject jo = JsonEntity.jsonEntity;
        System.out.println("jo" + jo);
        JSONArray results = jo.getJSONArray("results");
        //results正常情况下存放三种管控方案
        if (results.size() > 0) {
            Connection conn = JDBCDao.getConn();
            PreparedStatement addConventionalControlInfo = null;
            for (int i = 0; i < results.size(); i++) {
                JSONArray ja = results.getJSONArray(i);
                for (int j = 0; j < ja.size(); j++) {
                    JSONObject eachJO = ja.getJSONObject(j);
                    String enterpriseName = eachJO.getString("企业名称");
                    String outletName = eachJO.getString("监控点名称");
                    //添加方案几字段
                    String scheme = String.valueOf(i + 1);
                    //添加入库时间字段，目前是时间戳
                    String timestamp = String.valueOf(System.currentTimeMillis());
                    String date = eachJO.getString("日期");
                    String time = eachJO.getString("时间");
                    JSONObject feasibleJson = eachJO.getJSONObject("可行");
                    //System.out.println("可行" + feasibleJson);
                    //氮
                    String feasibleNoxDischargeUnit = "0";
                    String feasibleNoxDischargeAmount = "0";
                    String feasibleNitrogenRemovalEfficiencyUnit = "0";
                    String feasibleNitrogenRemovalEfficiencyValue = "0";
                    String feasibleNitrogenRemovalCostUnit = "0";
                    String feasibleNitrogenRemovalCostValue = "0";

                    if (feasibleJson.getJSONObject("氮氧化物") != null && !"{}".equals(feasibleJson.getJSONObject("氮氧化物").toString())) {
                        feasibleNoxDischargeUnit = feasibleJson.getJSONObject("氮氧化物").getJSONObject("排放量").getString("单位");
                        feasibleNoxDischargeAmount = feasibleJson.getJSONObject("氮氧化物").getJSONObject("排放量").getString("数值");
                    }
                    if (feasibleJson.getJSONObject("脱氮效率") != null && !"{}".equals(feasibleJson.getJSONObject("脱氮效率").toString())) {
                        feasibleNitrogenRemovalEfficiencyUnit = feasibleJson.getJSONObject("脱氮效率").getString("单位");
                        feasibleNitrogenRemovalEfficiencyValue = feasibleJson.getJSONObject("脱氮效率").getString("数值");
                    }
                    if (feasibleJson.getJSONObject("脱氮所需成本") != null && !"{}".equals(feasibleJson.getJSONObject("脱氮所需成本").toString())) {
                        feasibleNitrogenRemovalCostUnit = feasibleJson.getJSONObject("脱氮所需成本").getString("单位");
                        feasibleNitrogenRemovalCostValue = feasibleJson.getJSONObject("脱氮所需成本").getString("数值");
                    }
                    //硫
                    String feasibleSo2DischargeUnit = "0";
                    String feasibleSo2DischargeAmount = "0";
                    String feasibleDesulfurizationEfficiencyUnit = "0";
                    String feasibleDesulfurizationEfficiencyValue = "0";
                    String feasibleDesulfurizationCostUnit = "0";
                    String feasibleDesulfurizationCostValue = "0";
                    if (feasibleJson.getJSONObject("二氧化硫") != null && !"{}".equals(feasibleJson.getJSONObject("二氧化硫").toString())) {
                        feasibleSo2DischargeUnit = feasibleJson.getJSONObject("二氧化硫").getJSONObject("排放量").getString("单位");
                        feasibleSo2DischargeAmount = feasibleJson.getJSONObject("二氧化硫").getJSONObject("排放量").getString("数值");
                    }
                    if (feasibleJson.getJSONObject("脱硫效率") != null && !"{}".equals(feasibleJson.getJSONObject("脱硫效率").toString())) {
                        feasibleDesulfurizationEfficiencyUnit = feasibleJson.getJSONObject("脱硫效率").getString("单位");
                        feasibleDesulfurizationEfficiencyValue = feasibleJson.getJSONObject("脱硫效率").getString("数值");
                    }
                    if (feasibleJson.getJSONObject("脱硫所需成本") != null && !"{}".equals(feasibleJson.getJSONObject("脱硫所需成本").toString())) {
                        feasibleDesulfurizationCostUnit = feasibleJson.getJSONObject("脱硫所需成本").getString("单位");
                        feasibleDesulfurizationCostValue = feasibleJson.getJSONObject("脱硫所需成本").getString("数值");
                    }
                    //尘
                    String feasibleDustDischargeUnit = "0";
                    String feasibleDustDischargeAmount = "0";
                    String feasibleDustRemovalEfficiencyUnit = "0";
                    String feasibleDustRemovalEfficiencyValue = "0";
                    String feasibleDustRemovalCostUnit = "0";
                    String feasibleDustRemovalCostValue = "0";
                    if (feasibleJson.getJSONObject("烟尘") != null && !"{}".equals(feasibleJson.getJSONObject("烟尘").toString())) {
                        feasibleDustDischargeUnit = feasibleJson.getJSONObject("烟尘").getJSONObject("排放量").getString("单位");
                        feasibleDustDischargeAmount = feasibleJson.getJSONObject("烟尘").getJSONObject("排放量").getString("数值");
                    }
                    if (feasibleJson.getJSONObject("除尘效率") != null && !"{}".equals(feasibleJson.getJSONObject("除尘效率").toString())) {
                        feasibleDustRemovalEfficiencyUnit = feasibleJson.getJSONObject("除尘效率").getString("单位");
                        feasibleDustRemovalEfficiencyValue = feasibleJson.getJSONObject("除尘效率").getString("数值");
                    }
                    if (feasibleJson.getJSONObject("除尘所需成本") != null && !"{}".equals(feasibleJson.getJSONObject("除尘所需成本").toString())) {
                        feasibleDustRemovalCostUnit = feasibleJson.getJSONObject("除尘所需成本").getString("单位");
                        feasibleDustRemovalCostValue = feasibleJson.getJSONObject("除尘所需成本").getString("数值");
                    }

                    addConventionalControlInfo = conn.prepareStatement("insert into conventional_control_info values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    addConventionalControlInfo.setString(1, enterpriseName);
                    addConventionalControlInfo.setString(2, outletName);

                    addConventionalControlInfo.setString(3, scheme);
                    addConventionalControlInfo.setString(4, timestamp);

                    addConventionalControlInfo.setString(5, date);
                    addConventionalControlInfo.setString(6, time);
                    addConventionalControlInfo.setString(7, feasibleNoxDischargeUnit);
                    addConventionalControlInfo.setString(8, feasibleNoxDischargeAmount);
                    addConventionalControlInfo.setString(9, feasibleNitrogenRemovalEfficiencyUnit);
                    addConventionalControlInfo.setString(10, feasibleNitrogenRemovalEfficiencyValue);
                    addConventionalControlInfo.setString(11, feasibleNitrogenRemovalCostUnit);
                    addConventionalControlInfo.setString(12, feasibleNitrogenRemovalCostValue);
                    addConventionalControlInfo.setString(13, feasibleSo2DischargeUnit);
                    addConventionalControlInfo.setString(14, feasibleSo2DischargeAmount);
                    addConventionalControlInfo.setString(15, feasibleDesulfurizationEfficiencyUnit);
                    addConventionalControlInfo.setString(16, feasibleDesulfurizationEfficiencyValue);
                    addConventionalControlInfo.setString(17, feasibleDesulfurizationCostUnit);
                    addConventionalControlInfo.setString(18, feasibleDesulfurizationCostValue);
                    addConventionalControlInfo.setString(19, feasibleDustDischargeUnit);
                    addConventionalControlInfo.setString(20, feasibleDustDischargeAmount);
                    addConventionalControlInfo.setString(21, feasibleDustRemovalEfficiencyUnit);
                    addConventionalControlInfo.setString(22, feasibleDustRemovalEfficiencyValue);
                    addConventionalControlInfo.setString(23, feasibleDustRemovalCostUnit);
                    addConventionalControlInfo.setString(24, feasibleDustRemovalCostValue);
                    addConventionalControlInfo.execute();
                }
            }
            addConventionalControlInfo.close();
            conn.close();
            System.out.println("连接关闭");
        }
    }

    /**
     * 构造请求json
     */
    public String generateConventionalControlInfoRequestJson(String method, String enterpriseName, String outletName, String date) {
        String jsonStr = "{\n" +
                "\"async\":0,\n" +
                "\"callback\":\"\",\n" +
                "\"method\":\"" + method + "\",\n" +
                "\"param\": {\"企业名称\":\"" + enterpriseName + "\",\n" +
                "\"监控点名称\":\"" + outletName + "\",\n" +
                "\"起始日期\":\"" + date + "\",\n" +
                "\"天数\":\"1\"}\n" +
                "}";
        return jsonStr;
    }

    /**
     * 把所有企业及对应排口的常规管控数据入库
     *
     * @param date
     * @throws Exception
     */
    public void putDailyOutletConventionalControlInfo2DB(String date) throws Exception {
        String url = "http://119.90.57.34:9680/channel/do";
        HashMap<String, HashSet<String>> enterpriseOutletMap = EnterpriseOutletInfoData.getEnterpriseOutletMap();
        for (Map.Entry<String, HashSet<String>> entry : enterpriseOutletMap.entrySet()) {
            String enterpriseName = entry.getKey();
            HashSet<String> hs = entry.getValue();
            for (String str : hs) {
                String outletName = str;
                String jsonStr = generateConventionalControlInfoRequestJson("常规管控", enterpriseName, outletName, date);
                getOutletConventionalControlInfo2DB(url, jsonStr);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        //String url = "http://119.90.57.34:9680/channel/do";
        //String jsonStr = new ConventionalControlInfoData().generateConventionalControlInfoRequestJson("常规管控", "山西长信工业有限公司", "2号烧结机尾废气排放口");
        //new ConventionalControlInfoData().getOutletConventionalControlInfo2DB(url, jsonStr);
        String date = "2019年01月27日";
        new ConventionalControlInfoData().putDailyOutletConventionalControlInfo2DB(date);
    }
}
