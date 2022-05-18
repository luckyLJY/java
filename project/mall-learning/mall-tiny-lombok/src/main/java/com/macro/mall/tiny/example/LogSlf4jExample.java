package com.macro.mall.tiny.example;/**
 * Project: mall-tiny-lombok
 * Package: com.macro.mall.tiny.example
 * Version: 1.0
 * Created by ljy on 2022-5-7 21:05
 */

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName LogSlf4jExample
 * @Author: ljy on 2022-5-7 21:05
 * @Version: 1.0
 * @Description:
 */
@Slf4j
public class LogSlf4jExample {
    public static void main(String[] args) {
        log.info("level:{}","info");
        log.warn("level:{}","warn");
        log.error("level:{}", "error");
    }
}
