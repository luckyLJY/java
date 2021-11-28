package com.mayiljysty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mayiljysty.entity.User;
import com.mayiljysty.service.UserService;

import com.mayiljysty.test01.service.UserServiceTest01;



@Controller
public class IndexController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserServiceTest01 userServiceTest01;
	

	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	@ResponseBody
	@RequestMapping("/getuser")
	public User getUserByname(String name) {
		return userService.findByName(name);
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public String insert( String name,  Integer age) {
		userService.insert(name, age);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping("/insertTest001")
	public String insertTest001(String name, Integer age) {
		userServiceTest01.insertTest001(name, age);
		return "success";
	}
}

