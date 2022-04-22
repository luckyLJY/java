package com.macro.mall.tiny.service;/**
 * Project: mall-tiny-01
 * Package: com.macro.mall.tiny.service
 * Version: 1.0
 * Created by ljy on 2022-4-15 16:26
 */

import com.macro.mall.tiny.mbg.model.UmsAdmin;
import com.macro.mall.tiny.mbg.model.UmsPermission;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UmsAdminService
 * @Author: ljy on 2022-4-15 16:26
 * @Version: 1.0
 * @Description:后台管理员Service
 */

public interface UmsAdminService {

    //根据用户名获取后台管理员
    UmsAdmin getAdminByUsername(String username);

    //注册功能
    UmsAdmin register(UmsAdmin umsAdminParam);

    /**
     * 登陆功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username,String password);

    //获取用户所有权限(包括角色权限+-权限)
    List<UmsPermission> getPermissionList(Long adminId);
}
