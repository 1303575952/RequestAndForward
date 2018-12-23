package com.emep.changzhi.analyse.model;


import com.alibaba.fastjson.JSONObject;

/**
* @author wz
* @Description: 封装返回结果
* @date 2018/11/23 19:20
* @version V1.0
*/
public class Result {

    Integer code;
    String msg;
    JSONObject json = new JSONObject();

    public Result() {
        code = -1;
        msg = "unknown";
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public int getCode(){return code;}

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setJson(JSONObject json){this.json= json;}

    public void addField(String name, Object content ){
        json.put(name, content);
    }

    @Override
    public String toString() {
        json.put("result", code);
        json.put("msg", msg);
        return json.toString();
    }
}
