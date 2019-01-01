package com.sxu.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sxu.dao.JDBCDao;
import com.sxu.entity.JsonEntity;
import com.sxu.util.HttpClientUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 请求接口把企业名称和监控点名称入库
 */
public class EnterpriseOutletInfoData {
    public void getEntterpriseOutletInfoJson(String url, String json) throws Exception {
        //拿到企业名称和排口
        HttpClientUtil.httpPostWithJSON(url, json);
        JSONObject jo = JsonEntity.jsonEntity;
        System.out.println("jo" + jo);
        JSONArray results = jo.getJSONArray("results");
        if (results.size() > 0) {
            Connection conn = JDBCDao.getConn();
            PreparedStatement addEnterpriseOutletInfo = null;
            for (int i = 0; i < results.size(); i++) {
                JSONObject enterpriseOutlet = results.getJSONObject(i);
                String enterpriseName = String.valueOf(enterpriseOutlet.get("企业名称"));
                JSONArray outlets = enterpriseOutlet.getJSONArray("监控点名称");
                for (int j = 0; j < outlets.size(); j++) {
                    String outletName = String.valueOf(outlets.get(j));
                    addEnterpriseOutletInfo = conn.prepareStatement("insert into enterprise_outlet_info values (?,?,?,?)");
                    addEnterpriseOutletInfo.setString(1, enterpriseName);
                    addEnterpriseOutletInfo.setString(2, outletName);
                    addEnterpriseOutletInfo.setString(3, String.valueOf(System.currentTimeMillis()));
                    addEnterpriseOutletInfo.setString(4, "0");
                    addEnterpriseOutletInfo.execute();
                }
            }
            addEnterpriseOutletInfo.close();
            conn.close();
            System.out.println("连接关闭");
        }
    }

    /**
     * 通过读数据库拿到企业和对应排口数据
     *
     * @param
     * @throws Exception
     */
    public static HashMap<String, HashSet<String>> getEnterpriseOutletMap() throws Exception {
        ResultSet rs = null;
        Connection conn = JDBCDao.getConn();
        PreparedStatement selectEnterpriseOutletInfo = conn.prepareStatement("select enterprise_name,outlet_name from enterprise_outlet_info");
        rs = selectEnterpriseOutletInfo.executeQuery();
        //rs数据存入map
        HashMap<String, HashSet<String>> enterpriseOutletMap = new HashMap<String, HashSet<String>>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        //for (int i = 1; i < count; i++) {
        while (rs.next()) {
            String enterpriseName = rs.getString("enterprise_name");
            String outletName = rs.getString("outlet_name");
            if (enterpriseOutletMap.containsKey(enterpriseName)) {
                enterpriseOutletMap.get(enterpriseName).add(outletName);
            } else {
                HashSet<String> hs = new HashSet<String>();
                hs.add(outletName);
                enterpriseOutletMap.put(enterpriseName, hs);
            }
        }
        selectEnterpriseOutletInfo.close();
        conn.close();
        rs.close();
        System.out.println("连接关闭");
        return enterpriseOutletMap;
    }

    public static void putEnterpriseOutletInfo2DB() throws Exception {
        String url = "http://119.90.57.34:9680/channel/do";
        String jsonStr = "{\n" +
                "\t\"async\": 0,\n" +
                "\t\"callback\": \"\",\n" +
                "\t\"method\": \"企业列表\",\n" +
                "\t\"param\": {}\n" +
                "}";
        new EnterpriseOutletInfoData().getEntterpriseOutletInfoJson(url, jsonStr);
    }

    public static void main(String[] args) throws Exception {
        EnterpriseOutletInfoData.putEnterpriseOutletInfo2DB();
        //查看数据库中的企业和排口
        /*HashMap<String, HashSet<String>> enterpriseOutletMap = EnterpriseOutletInfoData.getEnterpriseOutletMap();
        for (Map.Entry<String, HashSet<String>> entry : enterpriseOutletMap.entrySet()) {
            System.out.print(entry.getKey() + "\t");
            HashSet<String> hs = entry.getValue();
            for (String str : hs) {
                System.out.print(str + ";");
            }
            System.out.println();
        }*/
    }
}
