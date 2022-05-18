package com.macro.mall.tiny.example;/**
 * Project: mall-tiny-lombok
 * Package: com.macro.mall.tiny.example
 * Version: 1.0
 * Created by ljy on 2022-5-7 21:04
 */

import lombok.extern.java.Log;

/**
 * @ClassName LogExample
 * @Author: ljy on 2022-5-7 21:04
 * @Version: 1.0
 * @Description:
 * 使用@Log注解，可以直接生成日志对象log，通过log对象可以直接打印日志。
 */
@Log
public class LogExample {
    public static void main(String[] args) {
        log.info("level info ");
    }
}
