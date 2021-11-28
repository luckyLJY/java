package com.javapro.map;/**
 * Project: JAVA_PRO
 * Package: com.javapro
 * Version: 1.0
 * Created by ljy on 2021-11-23 14:55
 */

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * @ClassName MapTrav
 * @Author: ljy on 2021-11-23 14:55
 * @Version: 1.0
 * @Description:map的几种遍历方法
 */
public class MapTrav {
    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 2);
        //useEntrySet(map);
        //useKeySet(map);
        //useIter(map);

        //输出系统名称
        //System.out.println(System.getProperty("os.name").toUpperCase());

        /**
         * BigDecimal转化为Double
         */
        /*BigDecimal A =new BigDecimal("10.0");
        System.out.println(A.doubleValue()==10.0);*/
        System.out.println("202111250841".substring(0,8));
    }

    /**
     * 使用Iterator遍历
     * @param map
     */
    private static void useIter(Map<Integer, Integer> map) {

        Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<Integer,Integer> entry = it.next();
            System.out.println("key = "+ entry.getKey() +", value = "+entry.getValue());
        }

        // 4. java8 Lambda
        // java8提供了Lambda表达式支持，语法看起来更简洁，可以同时拿到key和value，
        // 不过，经测试，性能低于entrySet,所以更推荐用entrySet的方式
        map.forEach((key, value) -> {
            System.out.println(key + ":" + value);
        });
    }

    /**
     *使用keySet或values来实现遍历
     * @param map
     */
    public static void useKeySet(Map<Integer, Integer> map) {
        //是引用keyset遍历
        for(Integer key:map.keySet()){
            System.out.println("key = "  + key);
        }
        //使用map中的值遍历
        for (Integer value:map.values()){
            System.out.println("key = " + value);
        }
    }

    /**
     * 使用entrySet进行遍历
     * @param map
     */
    public static void useEntrySet(Map<Integer, Integer> map){
        // 1. entrySet遍历，在键和值都需要时使用（最常用）
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
        }
    }


}
