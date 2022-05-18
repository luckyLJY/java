package com.macro.mall.tiny.example;/**
 * Project: mall-tiny-lombok
 * Package: com.macro.mall.tiny.example
 * Version: 1.0
 * Created by ljy on 2022-5-7 9:14
 */

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName EqualsAndHashCodeExample
 * @Author: ljy on 2022-5-7 9:14
 * @Version: 1.0
 * @Description:
 */
@Getter
@Setter
@EqualsAndHashCode
public class EqualsAndHashCodeExample {
    private Long id;
    @EqualsAndHashCode.Exclude
    private String name;
    @EqualsAndHashCode.Exclude
    private Integer age;

    public static void main(String[] args) {
        EqualsAndHashCodeExample example1 = new EqualsAndHashCodeExample();
        example1.setId(1L);
        example1.setName("test");
        example1.setAge(20);
        EqualsAndHashCodeExample example2 = new EqualsAndHashCodeExample();
        example2.setId(1L);
        System.out.println(example1.equals(example2));
    }
}
