package com.itheima.user;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 启动类
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.itheima.user.mapper"})
@EnableEurekaClient //开启Eureke客户端发现功能
public class UserApplication {

    public static void main(String[] args){
        // 运行spring应用
        SpringApplication.run(UserApplication.class, args);
    }
}