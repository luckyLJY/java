
package com.mayiljysty.test01.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.mayiljysty.test01.dao.UserMapperTest01;
import com.mayiljysty.test02.dao.UserMapperTest02;



@Service
public class UserServiceTest01 {
	@Autowired
	private UserMapperTest01 userMapperTest01;
	@Autowired
	private UserMapperTest02 userMapperTest02;

	// private UserServiceTest02 userServiceTest02;
	@Transactional
	public String insertTest001(String name, Integer age) {
		userMapperTest01.insert(name, age);
		// userServiceTest02.insertTest002(name, age);
		userMapperTest02.insert(name, age);
		int i = 1 / 0;
		return "success";
	}



}
