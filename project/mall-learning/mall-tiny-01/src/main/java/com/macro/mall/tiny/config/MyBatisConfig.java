package com.macro.mall.tiny.config;/**
 * Project: mall-tiny-01
 * Package: com.macro.mall.tiny.config
 * Version: 1.0
 * Created by ljy on 2022-4-14 9:19
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MyBatisConfig
 * @Author: ljy on 2022-4-14 9:19
 * @Version: 1.0
 * @Description:
 * MyBatis配置类
 */
@Configuration
@MapperScan({"com.macro.mall.tiny.mbg.mapper","com.macro.mall.tiny.dao"})
public class MyBatisConfig {
}
