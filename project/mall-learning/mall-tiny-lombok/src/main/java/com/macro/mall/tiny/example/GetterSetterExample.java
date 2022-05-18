package com.macro.mall.tiny.example;/**
 * Project: mall-tiny-lombok
 * Package: com.macro.mall.tiny.example
 * Version: 1.0
 * Created by ljy on 2022-5-7 9:05
 */

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName bytes
 * @Author: ljy on 2022-5-7 9:05
 * @Version: 1.0
 * @Description:
 */
public class GetterSetterExample {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Integer age;

    public static void main(String[] args) {
        GetterSetterExample example = new GetterSetterExample();
        example.setName("test");
        example.setAge(20);
        System.out.printf("Name:%s age:%d",example.getName(),example.getAge());
    }
}
