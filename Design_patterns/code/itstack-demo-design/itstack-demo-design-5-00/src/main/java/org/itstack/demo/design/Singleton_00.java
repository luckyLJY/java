package org.itstack.demo.design;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design
 * Version: 1.0
 * Created by ljy on 2022-2-9 15:01
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName Singleton_00
 * @Author: ljy on 2022-2-9 15:01
 * @Version: 1.0
 * @Description:0.静态类使用
 */
public class Singleton_00 {
    public static Map<String,String> cache = new ConcurrentHashMap<String, String>();
}
