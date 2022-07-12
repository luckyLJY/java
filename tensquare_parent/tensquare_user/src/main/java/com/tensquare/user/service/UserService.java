package com.tensquare.user.service;/**
 * Project: tensquare_parent
 * Package: com.tensquare.user.service
 * Version: 1.0
 * Created by ljy on 2022-7-9 16:42
 */

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName
 * @Author: ljy on 2022-7-9 16:42
 * @Version: 1.0
 * @Description:
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    //根据id查询用户
    public User selectById(String userId){

        return userDao.selectById(userId);
    }
}
