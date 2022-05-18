package com.macro.mall.tiny.example;/**
 * Project: mall-tiny-lombok
 * Package: com.macro.mall.tiny.example
 * Version: 1.0
 * Created by ljy on 2022-5-7 10:01
 */

import lombok.Builder;
import lombok.ToString;

/**
 * @ClassName BuilderExample
 * @Author: ljy on 2022-5-7 10:01
 * @Version: 1.0
 * @Description:
 * 使用@Builder注解可以通过建造者模式来创建对象，建造者模式加链式调用，创建对象太方便了！
 */
@Builder
@ToString
public class BuilderExample {
    private Long id;
    private String name;
    private Integer age;

    public static void main(String[] args) {
        BuilderExample example = BuilderExample.builder()
                .id(1L)
                .name("test")
                .age(20)
                .build();

        System.out.println(example);
    }
}
