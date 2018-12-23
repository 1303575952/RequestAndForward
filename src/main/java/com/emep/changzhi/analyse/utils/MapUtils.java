package com.emep.changzhi.analyse.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author wz
 * @version V1.0
 * @Description: 将一个 Map 对象转化为一个 JavaBean
 * @date 2018/11/23 19:20
 */
public class MapUtils {

    /**
     * 将一个 Map 对象转化为一个 JavaBean
     * @param type 要转化的类型
     * @param map 包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InstantiationException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings("rawtypes")
    public static Object convertMap(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        // 获取类属性
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        // 创建 JavaBean 对象
        Object obj = type.newInstance();
        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String a  = descriptor.getPropertyType().getName();
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                try {
                    Object value = map.get(propertyName);
                    Object[] args = new Object[1];
                    if (a.indexOf("Double") != -1) {
                        args[0] = Double.parseDouble(value.toString());
                    }else if(a.indexOf("Integer") != -1){
                        args[0] =Integer.parseInt(value.toString());
                    }
                    else{
                        args[0] = value;
                    }
                    descriptor.getWriteMethod().invoke(obj, args);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }


    /**
     * 判断map是否为空
     * @param map
     * @return
     */
    public static Boolean isEmptyMap(Map<String,Object> map) {
        Boolean reslut = false;
        if(map != null && !map.isEmpty()){
            reslut = true;
        }
        return reslut;
    }



}
