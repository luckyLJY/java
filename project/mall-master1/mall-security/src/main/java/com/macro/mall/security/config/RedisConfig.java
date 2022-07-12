package com.macro.mall.security.config;/**
 * Project: mall-master1
 * Package: com.macro.mall.security.config
 * Version: 1.0
 * Created by ljy on 2022-6-18 16:33
 */

import com.macro.mall.common.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RedisConfig
 * @Author: ljy on 2022-6-18 16:33
 * @Version: 1.0
 * @Description:Redis配置类
 */
@EnableCaching
@Configuration
public class RedisConfig extends BaseRedisConfig {

}
