package org.itstack.demo.design.cuisine;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design
 * Version: 1.0
 * Created by ljy on 2022-2-9 15:40
 */

import com.alibaba.fastjson.JSON;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @ClassName MQAdapter
 * @Author: ljy on 2022-2-9 15:40
 * @Version: 1.0
 * @Description:MQ消息体适配器
 */
public class MQAdapter {

    public static RebateInfo filter(String strJson, Map<String,String> link)throws NoSuchMethodException, InvocationTargetException,IllegalAccessException{
       // Map map = JSON.parseObject(strJson, Map.class);
        /*for (Object o : map.keySet()) {
            System.out.println(o);
        }*/

        //RebateInfo filter = filter(JSON.parseObject(strJson, Map.class), link);
        //System.out.println("第一层"+filter);
        return filter(JSON.parseObject(strJson, Map.class), link);
    }

    public static RebateInfo filter(Map obj,Map<String,String> link) throws NoSuchMethodException, InvocationTargetException,IllegalAccessException{
        RebateInfo rebateInfo = new RebateInfo();
        for (String key : link.keySet()) {
            Object val = obj.get(link.get(key));
            //System.out.println("看看"+val.toString());
            RebateInfo.class.getMethod("set"+key.substring(0,1).toUpperCase()+key.substring(1),String.class).invoke(rebateInfo,val.toString());
        }

        //System.out.println("第二层"+rebateInfo);
        return rebateInfo;
    }
}





















