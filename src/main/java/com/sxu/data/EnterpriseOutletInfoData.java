package com.sxu.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sxu.dao.JDBCDao;
import com.sxu.entity.JsonEntity;
import com.sxu.util.HttpClientUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 把企业名称和监控点名称入库
 */
public class EnterpriseOutletInfoData {
    public void getEntterpriseOutletInfoJson(String url, String json) throws Exception {
        //拿到企业名称和排口
        HttpClientUtil.httpPostWithJSON(url, json);
        JSONObject jo = JsonEntity.jsonEntity;
        System.out.println("jo" + jo);
        JSONArray results = jo.getJSONArray("results");
        if (results.size() > 0) {
            ResultSet rs = null;
            Connection conn = JDBCDao.getConn();
            for (int i = 0; i < results.size(); i++) {
                JSONObject enterpriseOutlet = results.getJSONObject(i);
                String enterpriseName = String.valueOf(enterpriseOutlet.get("企业名称"));
                JSONArray outlets = enterpriseOutlet.getJSONArray("监控点名称");
                for (int j = 0; j < outlets.size(); j++) {
                    String outletName = String.valueOf(outlets.get(j));
                    PreparedStatement addEnterpriseOutletInfo = conn.prepareStatement("insert into enterprise_outlet_info values (?,?,?,?)");
                    addEnterpriseOutletInfo.setString(1,enterpriseName);
                    addEnterpriseOutletInfo.setString(2,outletName);
                    addEnterpriseOutletInfo.setString(3,String.valueOf(System.currentTimeMillis()));
                    addEnterpriseOutletInfo.setString(4,"0");
                    addEnterpriseOutletInfo.execute();
                }
            }
            conn.close();
            System.out.println("连接关闭");
        }
    }

    public static void main(String[] args) throws Exception {
        String url = "http://119.90.57.34:9680/channel/do";
        String jsonStr = "{\n" +
                "\t\"async\": 0,\n" +
                "\t\"callback\": \"\",\n" +
                "\t\"method\": \"企业列表\",\n" +
                "\t\"param\": {}\n" +
                "}";
        new EnterpriseOutletInfoData().getEntterpriseOutletInfoJson(url, jsonStr);
    }
}
