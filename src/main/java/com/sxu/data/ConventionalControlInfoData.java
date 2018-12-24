package com.sxu.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sxu.dao.JDBCDao;
import com.sxu.entity.JsonEntity;
import com.sxu.util.HttpClientUtil;

import java.sql.Connection;
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
            //TODO 常规管控数据入库
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
