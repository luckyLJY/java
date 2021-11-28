package com.itguigu.bean;/**
 * Project: Project0408
 * Package: com.itguigu.idex.com.itguigu.bean
 * Version: 1.0
 * Created by ljy on 2021-11-10 20:37
 */

/**
 * @ClassName
 * @Author: ljy on 2021-11-10 20:37
 * @Version: 1.0
 * @Description: User实体类
 */
public class User {

    private String name;
    private String sex;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
