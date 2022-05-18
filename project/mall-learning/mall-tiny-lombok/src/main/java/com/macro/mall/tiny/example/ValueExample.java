package com.macro.mall.tiny.example;/**
 * Project: mall-tiny-lombok
 * Package: com.macro.mall.tiny.example
 * Version: 1.0
 * Created by ljy on 2022-5-7 9:53
 */

import lombok.ToString;
import lombok.Value;

/**
 * @ClassName ValueExample
 * @Author: ljy on 2022-5-7 9:53
 * @Version: 1.0
 * @Description:
 * 使用@Value注解可以把类声明为不可变的，声明后此类相当于final类，无法被继承，其属性也会变成final属性。
 */
@Value
@ToString
public class ValueExample {


    private Long id;
    private String name;
    private Integer age;

    public static void main(String[] args) {
        ValueExample example = new ValueExample(1L, "test", 20);
        System.out.println(example);
    }
}
