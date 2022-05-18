package com.macro.cloud.config;/**
 * Project: mall-sclearning
 * Package: com.macro.cloud
 * Version: 1.0
 * Created by ljy on 2022-5-12 9:28
 */

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName FeignConfig
 * @Author: ljy on 2022-5-12 9:28
 * @Version: 1.0
 * @Description:
 */

@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
