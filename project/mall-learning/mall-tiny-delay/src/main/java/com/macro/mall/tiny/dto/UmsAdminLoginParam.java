package com.macro.mall.tiny.dto;/**
 * Project: mall-tiny-delay
 * Package: com.macro.mall.tiny.dto
 * Version: 1.0
 * Created by ljy on 2022-4-26 16:22
 */

/**
 * @ClassName UmsAdminLoginParam
 * @Author: ljy on 2022-4-26 16:22
 * @Version: 1.0
 * @Description:
 */

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

/**
 * 用户登录参数
 * Created by macro on 2018/4/26.
 */
public class UmsAdminLoginParam {
    @ApiModelProperty(value = "用户名", required = true)
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty(message = "密码不能为空")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
