package com.macro.mall.tiny.example;/**
 * Project: mall-tiny-lombok
 * Package: com.macro.mall.tiny.example
 * Version: 1.0
 * Created by ljy on 2022-5-7 9:09
 */

import lombok.ToString;

/**
 * @ClassName ToStringExample
 * @Author: ljy on 2022-5-7 9:09
 * @Version: 1.0
 * @Description:
 */
@ToString
public class ToStringExample {
    @ToString.Exclude
    private Long id;
    private String name;
    private Integer age;


    public ToStringExample(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public static void main(String[] args) {
        ToStringExample ex = new ToStringExample(1L, "test", 20);
        System.out.println(ex);
    }
}
