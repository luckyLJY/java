package com.macro.mall.tiny.example;/**
 * Project: mall-tiny-lombok
 * Package: com.macro.mall.tiny.example
 * Version: 1.0
 * Created by ljy on 2022-5-7 20:52
 */

import lombok.AllArgsConstructor;
import lombok.With;

/**
 * @ClassName WithExample
 * @Author: ljy on 2022-5-7 20:52
 * @Version: 1.0
 * @Description:
 * 使用@With注解可以实现对原对象进行克隆，并改变其一个属性，使用时需要指定全参构造方法
 */
@With
@AllArgsConstructor
public class WithExample {

    private Long id;
    private String name;
    private Integer age;

    public static void main(String[] args) {
        WithExample ex = new WithExample(1L, "test", 20);
        WithExample example2 = ex.withAge(22);
        System.out.println(ex.equals(example2));
    }
}
