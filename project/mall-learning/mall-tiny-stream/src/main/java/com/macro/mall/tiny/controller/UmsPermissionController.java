package com.macro.mall.tiny.controller;/**
 * Project: mall-tiny-stream
 * Package: com.macro.mall.tiny.controller
 * Version: 1.0
 * Created by ljy on 2022-4-25 17:32
 */


import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.dto.UmsPermissionNode;
import com.macro.mall.tiny.service.UmsPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName UmsPermissionController
 * @Author: ljy on 2022-4-25 17:32
 * @Version: 1.0
 * @Description:后台用户权限管理
 */
@Controller
@Api(tags = "UmsPermissionController",description = "后台用户权限管理")
@RequestMapping("/permission")
public class UmsPermissionController {
    @Autowired
    private UmsPermissionService permissionService;

    @ApiOperation("以层级结构返回所有权限")
    @RequestMapping(value = "/treeList",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<UmsPermissionNode>> treeList(){
        List<UmsPermissionNode> permissionNodeList = permissionService.treeList();
        return CommonResult.success(permissionNodeList);
    }

    @ApiOperation("Stream API测试")
    @RequestMapping(value = "/stream",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult streamTest(){
        permissionService.streamTest();
        return CommonResult.success(null,"操作成功");
    }
}
