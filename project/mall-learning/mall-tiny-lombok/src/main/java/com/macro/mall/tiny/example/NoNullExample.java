package com.macro.mall.tiny.example;/**
 * Project: mall-tiny-lombok
 * Package: com.macro.mall.tiny.example
 * Version: 1.0
 * Created by ljy on 2022-5-7 8:53
 */

import lombok.NonNull;

/**
 * @ClassName NoNullExample
 * @Author: ljy on 2022-5-7 8:53
 * @Version: 1.0
 * @Description:
 */
public class NoNullExample {

    private String name;

    public NoNullExample(@NonNull String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        new NoNullExample("test");
        //或抛出NullPointerException
        new NoNullExample(null);
    }
}
