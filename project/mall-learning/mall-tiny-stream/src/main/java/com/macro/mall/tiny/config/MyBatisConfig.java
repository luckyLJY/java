package com.macro.mall.tiny.config;/**
 * Project: mall-tiny-stream
 * Package: com.macro.mall.tiny.config
 * Version: 1.0
 * Created by ljy on 2022-4-25 10:54
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MyBatisConfig
 * @Author: ljy on 2022-4-25 10:54
 * @Version: 1.0
 * @Description:MyBatisConfig配置类
 */
@Configuration
@MapperScan("com.macro.mall.tiny.mbg.mapper")
public class MyBatisConfig {
}
