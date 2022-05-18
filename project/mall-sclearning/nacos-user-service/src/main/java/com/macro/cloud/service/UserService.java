package com.macro.cloud.service;/**
 * Project: mall-sclearning
 * Package: com.macro.cloud.service
 * Version: 1.0
 * Created by ljy on 2022-5-12 14:43
 */

import com.macro.cloud.domain.User;

import java.util.List;

/**
 * @ClassName UserService
 * @Author: ljy on 2022-5-12 14:43
 * @Version: 1.0
 * @Description:
 */
public interface UserService {
    void create(User user);

    User getUser(Long id);

    void update(User user);

    void delete(Long id);

    User getByUsername(String username);

    List<User> getUserByIds(List<Long> ids);
}
