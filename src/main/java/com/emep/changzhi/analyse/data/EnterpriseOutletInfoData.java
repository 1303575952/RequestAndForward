package com.emep.changzhi.analyse.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emep.changzhi.analyse.dao.EnterpriseOutletInfo2DB;
import com.emep.changzhi.analyse.utils.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

public class EnterpriseOutletInfoData {
    public void getEntterpriseOutletJson(String url, String json) {
        //拿到企业名称和排口
        JSONObject jo = HttpClientUtil.httpPostWithJSON(url, json);
        JSONArray results = jo.getJSONArray("results");
        if (results.size() > 0) {
            for (int i = 0; i < results.size(); i++) {
                JSONObject enterpriseOutlet = results.getJSONObject(i);
                String enterpriseName = String.valueOf(enterpriseOutlet.get("企业名称"));
                JSONArray outlets = enterpriseOutlet.getJSONArray("监控点名称");
                for (int j = 0; j < outlets.size(); j++) {
                    Object outletName = outlets.get(j);
                    Map<String, Object> map = new HashMap<>();
                    map.put("enterpriseName", enterpriseName);
                    map.put("outletName", outletName);
                    new EnterpriseOutletInfo2DB().add(map);
                }
            }
        }
    }

    public static void main(String[] args) {
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
        new EnterpriseOutletInfoData().getEntterpriseOutletJson(url, jsonStr);
    }
}
