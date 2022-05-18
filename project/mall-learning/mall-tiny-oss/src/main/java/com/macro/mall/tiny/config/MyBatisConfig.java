package com.macro.mall.tiny.config;/**
 * Project: mall-tiny-oss
 * Package: com.macro.mall.tiny.config
 * Version: 1.0
 * Created by ljy on 2022-4-26 9:38
 */

/**
 * @ClassName MyBatisConfig
 * @Author: ljy on 2022-4-26 9:38
 * @Version: 1.0
 * @Description:
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

