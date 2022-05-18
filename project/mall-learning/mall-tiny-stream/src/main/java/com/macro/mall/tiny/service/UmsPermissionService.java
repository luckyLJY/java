package com.macro.mall.tiny.service;/**
 * Project: mall-tiny-stream
 * Package: com.macro.mall.tiny.service
 * Version: 1.0
 * Created by ljy on 2022-4-25 14:33
 */

import com.macro.mall.tiny.dto.UmsPermissionNode;

import java.util.List;

/**
 * @ClassName UmsPermissionService
 * @Author: ljy on 2022-4-25 14:33
 * @Version: 1.0
 * @Description:后台用户权限管理Service
 */
public interface UmsPermissionService {
    //以层级结构返回所有权限
    List<UmsPermissionNode> treeList();

    void streamTest();
}
