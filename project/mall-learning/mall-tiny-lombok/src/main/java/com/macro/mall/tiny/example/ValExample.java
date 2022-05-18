package com.macro.mall.tiny.example;/**
 * Project: mall-tiny-lombok
 * Package: com.macro.mall.tiny.example
 * Version: 1.0
 * Created by ljy on 2022-5-7 8:43
 */

import lombok.val;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ValExample
 * @Author: ljy on 2022-5-7 8:43
 * @Version: 1.0
 * @Description:
 */
public class ValExample {

    public static void example1(){
        //使用val 代替String类型和ArrayList<String>
       val example =  new ArrayList<String>();
       example.add("Hello Word");
        val foo = example.get(0);
        System.out.println(foo.toLowerCase());
    }

    public static void examle2(){
        //用val代替Map.Entry<Integer,String>类型
        val map = new HashMap<>();
        map.put(0,"zero");
        map.put(5,"five");
        for (val entry : map.entrySet()) {
            System.out.printf("%d:%s\n",entry.getKey(),entry.getValue());
        }
    }

    public static void main(String[] args) {
        example1();
        examle2();
    }
}
