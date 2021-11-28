package com.mayiljysty.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.mayiljysty.controller","com.mayiljysty.service"})
@MapperScan(basePackages = {"com.mayiljysty.mapper"})

@EnableAutoConfiguration

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(App.class, args);
	}

}
