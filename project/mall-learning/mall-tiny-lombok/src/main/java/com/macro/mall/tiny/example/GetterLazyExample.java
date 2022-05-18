package com.macro.mall.tiny.example;/**
 * Project: mall-tiny-lombok
 * Package: com.macro.mall.tiny.example
 * Version: 1.0
 * Created by ljy on 2022-5-7 20:57
 */

import lombok.Getter;

/**
 * @ClassName GetterLazyExample
 * @Author: ljy on 2022-5-7 20:57
 * @Version: 1.0
 * @Description:
 * 当我们获取某一个属性比较消耗资源时，可以给@Getter添加lazy=true属性实现懒加载，会生成Double Check Lock 样板代码对属性进行懒加载。
 */
public class GetterLazyExample {
    @Getter(lazy = true)
    private final double[] cached = expensive();

    private double[] expensive() {
        double[] result = new double[1000000];
        for (int i = 0; i < result.length; i++) {
            result[i] = Math.asin(i);
        }
        return result;
    }

    public static void main(String[] args) {
        //使用Double Check Lock 样板代码对属性进行懒加载
        GetterLazyExample example = new GetterLazyExample();
        System.out.println(example.getCached().length);
    }
}
