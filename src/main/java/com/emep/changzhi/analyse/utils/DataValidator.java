package com.emep.changzhi.analyse.utils;

import com.emep.changzhi.analyse.model.ResultBean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

public class DataValidator {

    public static ResultBean validateMapData(Map<String, Object> map, String... args){
        ResultBean resultBean = new ResultBean();
        if (null != args) {
            for (String arg : args) {
                if (arg.isEmpty()){ break; }
                if (map.containsKey(arg)){
                    if (StringUtils.isEmpty(map.get(arg).toString())){
                        resultBean = resultBean.nulls(arg);
                        break;
                    }else {
                        continue;
                    }
                }else {
                    resultBean =  resultBean.Lack(arg);
                    break;
                }
            }
        }
        return resultBean;
    }


    /**
     * 为对象set属性
     * */
    public static Object setObjectAttr(Object o,Map<String, Object> payload){
        Field[] fields=o.getClass().getFields();
        for(int i=0;i<fields.length;i++){
            try {
                if(payload.get(fields[i].getName())!=null){
                    String firstLetter = fields[i].getName().substring(0, 1).toUpperCase();
                    String setter = "set" + firstLetter + fields[i].getName().substring(1);
                    Method method=null;
                    System.out.println(fields[i].getName().toString());
                    if("saveTime".equals(fields[i].getName().toString())){
                        method = o.getClass().getMethod(setter, Date.class);
                    }else{
                        method = o.getClass().getMethod(setter, String.class);

                    }
                    method.invoke(o,payload.get(fields[i].getName()));

                }

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return o;
    }
}
