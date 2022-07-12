package com.macro.mall.security.config;/**
 * Project: mall-master1
 * Package: com.macro.mall.security.config
 * Version: 1.0
 * Created by ljy on 2022-6-18 16:34
 */

/**
 * @ClassName IgnoreUrlsConfig
 * @Author: ljy on 2022-6-18 16:34
 * @Version: 1.0
 * @Description:SpringSecurity白名单资源路径配置
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
/**
 * SpringSecurity白名单资源路径配置
 * Created by macro on 2018/11/5.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsConfig {

    private List<String> urls = new ArrayList<>();

}