package com.itheima.user.service;

import com.itheima.user.mapper.UserMapper;
import com.itheima.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Value("${test.name}")
    private  String name;
    /**
     * 根据主键查询用户
     * @param id 用户id
     * @return 用户
     */
    public User queryById(Long id){

        System.out.println("配置文件中的 test name"+name);
        return userMapper.selectByPrimaryKey(id);
    }
}
