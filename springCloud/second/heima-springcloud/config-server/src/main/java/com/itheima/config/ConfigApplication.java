package com.itheima.config;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 启动类
 */
@SpringBootApplication
@EnableConfigServer  //配置服务
public class ConfigApplication {

    public static void main(String[] args){
        // 运行spring应用
        SpringApplication.run(ConfigApplication.class, args);
    }
}