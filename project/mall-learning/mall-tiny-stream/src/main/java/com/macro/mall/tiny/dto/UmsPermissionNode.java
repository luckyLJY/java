package com.macro.mall.tiny.dto;/**
 * Project: mall-tiny-stream
 * Package: com.macro.mall.tiny.dto
 * Version: 1.0
 * Created by ljy on 2022-4-25 14:29
 */

import com.macro.mall.tiny.mbg.model.UmsPermission;

import java.util.List;

/**
 * @ClassName UmsPermissionNode
 * @Author: ljy on 2022-4-25 14:29
 * @Version: 1.0
 * @Description:
 */
public class UmsPermissionNode extends UmsPermission {

    private List<UmsPermissionNode> children;

    public List<UmsPermissionNode> getChildren() {
        return children;
    }

    public void setChildren(List<UmsPermissionNode> children) {
        this.children = children;
    }
}
