package com.macro.cloud.config;/**
 * Project: mall-sclearning
 * Package: com.macro.cloud.config
 * Version: 1.0
 * Created by ljy on 2022-5-19 17:15
 */

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName RibbonConfig
 * @Author: ljy on 2022-5-19 17:15
 * @Version: 1.0
 * @Description:
 */
@Configuration
public class RibbonConfig {

    @Bean
    @SentinelRestTemplate
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
