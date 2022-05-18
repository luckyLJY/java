package com.macro.cloud.config;/**
 * Project: mall-sclearning
 * Package: com.macro.cloud.config
 * Version: 1.0
 * Created by ljy on 2022-5-12 16:08
 */

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName RibbonConfig
 * @Author: ljy on 2022-5-12 16:08
 * @Version: 1.0
 * @Description:
 */
@Configuration
public class RibbonConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
