package com.sxu.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sxu.dao.JDBCDao;
import com.sxu.entity.JsonEntity;
import com.sxu.util.HttpClientUtil;

import java.sql.Connection;
import java.sql.ResultSet;

public class TaskControlInfoData {
    public void getTaskControlInfoJson(String url, String json) throws Exception {
        HttpClientUtil.httpPostWithJSON(url, json);
        JSONObject jo = JsonEntity.jsonEntity;
        System.out.println("jo" + jo);
        JSONArray results = jo.getJSONArray("results");
        if (results.size() > 0) {
            ResultSet rs = null;
            Connection conn = JDBCDao.getConn();
            //TODO 任务管控数据入库
            conn.close();
            System.out.println("连接关闭");
        }
    }

    public static void main(String[] args) throws Exception {
        String url = "http://119.90.57.34:9680/channel/do";
        String jsonStr = "{\n" +
                "\"async\": 0,\n" +
                "\"callback\": \"\",\n" +
                "\"method\": \"任务管控\",\n" +
                "\"param\": {\"企业名称\" : \"沁源县兴茂煤化有限公司\",\n" +
                "\t\"监控点名称\" : \"焦炉烟囱排口\",\n" +
                "\t\"起始日期\" : \"2018年12月15日\",\n" +
                "\t\"天数\" : \"1\"}\n" +
                "}";
        new TaskControlInfoData().getTaskControlInfoJson(url, jsonStr);
    }
}
