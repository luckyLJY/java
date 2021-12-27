package com.tensquare.article.config;/**
 * Project: tensquare_parent
 * Package: com.tensquare.article.config
 * Version: 1.0
 * Created by ljy on 2021-12-26 14:12
 */

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MyBatisPlusConfig
 * @Author: ljy on 2021-12-26 14:12
 * @Version: 1.0
 * @Description:mybatisplus的分页插件
 */

@Configuration
public class MyBatisPlusConfig {
    @Bean
    public PaginationInterceptor ceatePaginationInterceptor(){
        return new PaginationInterceptor();
    }
}
