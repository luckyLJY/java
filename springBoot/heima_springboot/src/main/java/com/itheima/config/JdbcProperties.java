package com.itheima.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ConfigurationProperties从application配置文件中读取配置项
 * prefix表示配置项的前缀
 * 配置项类中的类变量名必须要与 前缀之后的配置项名称保持 松散绑定(相同)
 */
//@ConfigurationProperties(prefix = "jdbc")
public class JdbcProperties {
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    public String getDriverName() {
        return driverClassName;
    }

    public void setDriverName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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
