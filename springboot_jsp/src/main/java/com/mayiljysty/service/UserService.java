package com.mayiljysty.service;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mayiljysty.entity.User;
import com.mayiljysty.mapper.UserMapper;

@Service
public class UserService {
	
	@Autowired
	private UserMapper usermapper;
	
	public User findByName( String name) {
		return usermapper.findByName(name);
	}
	
	public void insert( String name,  Integer age) {
		usermapper.insert(name, age);
	}
}
