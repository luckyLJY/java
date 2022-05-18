package com.macro.cloud.domain;/**
 * Project: mall-sclearning
 * Package: com.macro.cloud.domain
 * Version: 1.0
 * Created by ljy on 2022-5-12 16:09
 */

/**
 * @ClassName User
 * @Author: ljy on 2022-5-12 16:09
 * @Version: 1.0
 * @Description:
 */
public class User {

    private Long id;
    private String username;
    private String password;

    public User(){}

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
