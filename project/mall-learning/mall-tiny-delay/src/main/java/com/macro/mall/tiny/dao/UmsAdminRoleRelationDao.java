package com.macro.mall.tiny.dao;/**
 * Project: mall-tiny-delay
 * Package: com.macro.mall.tiny.dao
 * Version: 1.0
 * Created by ljy on 2022-4-26 16:05
 */

/**
 * @ClassName UmsAdminRoleRelationDao
 * @Author: ljy on 2022-4-26 16:05
 * @Version: 1.0
 * @Description:
 */

import com.macro.mall.tiny.mbg.model.UmsPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台用户与角色管理自定义Dao
 * Created by macro on 2018/10/8.
 */
public interface UmsAdminRoleRelationDao {

    /**
     * 获取用户所有权限(包括+-权限)
     */
    List<UmsPermission> getPermissionList(@Param("adminId") Long adminId);
}
