package com.macro.mall.tiny.config;/**
 * Project: mall-tiny-redis
 * Package: com.macro.mall.tiny.config
 * Version: 1.0
 * Created by ljy on 2022-4-27 10:23
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * Created by macro on 2019/4/8.
 */
@Configuration
@MapperScan("com.macro.mall.tiny.mbg.mapper")
public class MyBatisConfig {
}
