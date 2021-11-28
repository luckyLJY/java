package com.itheima.pojo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.soap.Name;
import java.util.Date;

//在编译阶段会根据注释自动生成对应的方法；data包含get/set/hashcode/equals/toString等方法
@Data
@Table(name = "tb_user")
public class User {

    @Id
    //主键回填
    @KeySql(useGeneratedKeys = true)
    private Long id;

    @Column(name = "user_name")
    //符合驼峰规则无需添加
    private String userName;

    private  String password;

    private String name;

    private Integer age;

    private Integer sex;

    private String note;

    private Date birthday;

    private Date created;

    private Date updated;
}
