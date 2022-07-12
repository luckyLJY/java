package com.macro.mall.common.domain;/**
 * Project: mall-master1
 * Package: com.macro.mall.common.domain
 * Version: 1.0
 * Created by ljy on 2022-6-18 15:22
 */

/**
 * @ClassName SwaggerProperties
 * @Author: ljy on 2022-6-18 15:22
 * @Version: 1.0
 * @Description:
 */

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Swagger自定义配置
 * Created by macro on 2020/7/16.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class SwaggerProperties {
    /**
     * API文档生成基础路径
     */
    private String apiBasePackage;
    /**
     * 是否要启用登录认证
     */
    private boolean enableSecurity;
    /**
     * 文档标题
     */
    private String title;
    /**
     * 文档描述
     */
    private String description;
    /**
     * 文档版本
     */
    private String version;
    /**
     * 文档联系人姓名
     */
    private String contactName;
    /**
     * 文档联系人网址
     */
    private String contactUrl;
    /**
     * 文档联系人邮箱
     */
    private String contactEmail;
}
