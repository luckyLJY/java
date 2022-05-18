package com.macro.mall.tiny.example;/**
 * Project: mall-tiny-lombok
 * Package: com.macro.mall.tiny.example
 * Version: 1.0
 * Created by ljy on 2022-5-7 10:05
 */

import lombok.SneakyThrows;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName SneakyThrows
 * @Author: ljy on 2022-5-7 10:05
 * @Version: 1.0
 * @Description:手动捕获并抛出异常
 */
public class SneakyThrowsExample {
    //自动抛出异常，无需处理
    @SneakyThrows(UnsupportedEncodingException.class)
    public static byte[] str2byte(String str){
        return str.getBytes("UTF-8");
    }

    public static void main(String[] args) {
        String str = "Hello World!";
        System.out.println(str2byte(str).length);
    }
}
