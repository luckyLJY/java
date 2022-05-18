package com.macro.cloud.service.impl;/**
 * Project: mall-sclearning
 * Package: com.macro.cloud.service.impl
 * Version: 1.0
 * Created by ljy on 2022-5-10 14:49
 */

import com.macro.cloud.domain.User;
import com.macro.cloud.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName UserServiceImpl
 * @Author: ljy on 2022-5-10 14:49
 * @Version: 1.0
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {
    private List<User> userList;


    @Override
    public void create(User user) {
        userList.add(user);
    }

    @Override
    public User getUser(Long id) {
        List<User> findUserList = userList.stream().filter(userItem -> userItem.getId().equals(id)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(findUserList)){
            return findUserList.get(0);
        }
        return null;
    }

    @Override
    public void update(User user) {
        userList.stream().filter(userItem -> userItem.getId().equals(user.getId())).forEach(
                userItem ->{
                 userItem.setUsername(user.getUsername());
                 userItem.setPassword(user.getPassword());
                });
    }

    @Override
    public void delete(Long id) {
        User user = getUser(id);
        if (user != null){
            userList.remove(user);
        }
    }

    @Override
    public User getByUsername(String username) {
        List<User> findUserList = userList.stream().filter(userItem -> userItem.getUsername().equals(username)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(findUserList)){
            return findUserList.get(0);
        }
        return null;
    }

    @Override
    public List<User> getUserByIds(List<Long> ids) {
        return userList.stream().filter(userItem -> ids.contains(userItem.getId())).collect(Collectors.toList());
    }

    @PostConstruct
    public void initData(){
        userList = new ArrayList<>();
        userList.add(new User(1L, "macro", "123456"));
        userList.add(new User(2L, "andy", "123456"));
        userList.add(new User(3L, "mark", "123456"));
    }
}
