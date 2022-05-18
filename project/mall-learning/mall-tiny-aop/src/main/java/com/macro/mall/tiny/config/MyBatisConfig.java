package com.macro.mall.tiny.config;/**
 * Project: mall-learning
 * Package: com.macro.mall.tiny.config
 * Version: 1.0
 * Created by ljy on 2022-4-24 10:28
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MyBatisConfig
 * @Author: ljy on 2022-4-24 10:28
 * @Version: 1.0
 * @Description:MyBatisConfig配置类
 */
@Configuration
@MapperScan("com.macro.mall.tiny.mbg.mapper")
public class MyBatisConfig {
}
