package com.macro.mall.tiny.example;/**
 * Project: mall-tiny-lombok
 * Package: com.macro.mall.tiny.example
 * Version: 1.0
 * Created by ljy on 2022-5-7 9:41
 */

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * @ClassName DataExample
 * @Author: ljy on 2022-5-7 9:41
 * @Version: 1.0
 * @Description:
 */
@Data
public class DataExample {

    @NonNull
    private Long id;
    @EqualsAndHashCode.Exclude
    private String name;
    @EqualsAndHashCode.Exclude
    private Integer age;

    public static void main(String[] args) {
        DataExample example1= new DataExample(1L);
        example1.setName("test");
        example1.setAge(20);
        System.out.println(example1);
        DataExample example2 = new DataExample(1L);
        System.out.println(example1.equals(example2));
    }
}
