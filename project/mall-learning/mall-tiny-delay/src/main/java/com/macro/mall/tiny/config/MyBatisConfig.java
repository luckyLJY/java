package com.macro.mall.tiny.config;/**
 * Project: mall-tiny-delay
 * Package: com.macro.mall.tiny.config
 * Version: 1.0
 * Created by ljy on 2022-4-26 15:26
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MyBatisConfig
 * @Author: ljy on 2022-4-26 15:26
 * @Version: 1.0
 * @Description:
 */
@Configuration
@MapperScan({"com.macro.mall.tiny.mbg.mapper","com.macro.mall.tiny.dao"})
public class MyBatisConfig {
}
