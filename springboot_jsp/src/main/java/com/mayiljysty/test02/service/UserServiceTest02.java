package com.mayiljysty.test02.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mayiljysty.test02.dao.UserMapperTest02;

@Service
public class UserServiceTest02 {
	
	@Autowired
	private UserMapperTest02 userMapperTest02;

	@Transactional
	public String insertTest002(String name, Integer age) {
		userMapperTest02.insert(name, age);
		
		return "success";
	}
}
