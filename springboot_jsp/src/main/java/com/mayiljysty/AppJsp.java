package com.mayiljysty;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


import com.mayiljysty.datasource.DBConfig1;
import com.mayiljysty.datasource.DBConfig2;

@SpringBootApplication
@EnableConfigurationProperties(value = { DBConfig1.class, DBConfig2.class })
//@ComponentScan(basePackages = {"com.mayiljysty.controller","com.mayiljysty.service"})
@MapperScan(basePackages = "com.mayiljysty.mapper")
//@EnableAutoConfiguration

public class AppJsp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(AppJsp.class, args);
	}

}
