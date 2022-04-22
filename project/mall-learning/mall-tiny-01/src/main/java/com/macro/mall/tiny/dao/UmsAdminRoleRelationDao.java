package com.macro.mall.tiny.dao;/**
 * Project: mall-tiny-01
 * Package: com.macro.mall.tiny.dao
 * Version: 1.0
 * Created by ljy on 2022-4-18 9:30
 */

import com.macro.mall.tiny.mbg.model.UmsPermission;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * @ClassName UmsAdminRoleRelationDao
 * @Author: ljy on 2022-4-18 9:30
 * @Version: 1.0
 * @Description:后台用户与角色管理自定义Dao
 */

public interface UmsAdminRoleRelationDao {
    /**
     * 获取用户所有权限(包括+-权限)
     */
    List<UmsPermission> getPermissionList(@Param("adminId") Long adminId);
}
