## hive基本操作
### 数据库操作

    # 新建数据库
    CREATE DATABASE IF NOT EXISTS db_name;

    # 查看schma
    show databases;
    
    # 使用数据库
    use db_name;

    # hive会为每一个数据库创建一个目录，可以在新建数据库时指定
    CREATE DATABASE TEST
    LOCATION '/my/test'
    COMMENT '数据库练习'；

    # 查看数据库信息
    DESCRIBE DATABASE TEST;

    # 增加一些和其相关的键-值对属性信息
    create database myhive
    with dbproperties('name'='lu','data'='2012-01-02');
    desc database extended myhive;

    # 删除数据库
    drop database if exists myhive;          //避免数据库不存在而抛出警告信息
    drop database if exists myhive cascade;//如果数据库下有表,使用

    # 修改数据库
    alter database myhive set dbproperties(edited-by'='Joe');
    //为数据库的dbproperties设置键值对属性值，来描述数据库属性信息，数据库其他元数据都是不可更改的，包括数据库名和数据库所在目录位置。
    
### 表操作

    # 建表
    CREATE TABLE IF NOT EXISTS mydb.employees(
        name STRING COMMENT 'Employee name',
        salary FLOAT COMMENT 'Employee salary',
        subordinates ARRY<STRING> COMMENT 'Names of subordinates',
        address STRUCT<street:STRING,city:STRING,state:STRING,zip:INT> COMMENT 'Home address'
    )
    COMMENT 'Description of the table'
    TBLPROPERTIES('creator'='me','created_at'='2012-01-02',...)
    LOCATION '/user/hive/warehouse/mydb.db/employees;

    # default数据库
    数据直接存储在/usr/hive/warehouse

    # 拷贝表结构
    CREATE TABLE IF NOT EXISTS mydb.employees2
    LINKE mydb.employees;
    注意：其他属性是否能够修改与版本有关系

    # 查看表
    use mydb;
    show tables;

    show tables IN mydb;

    show tables 'emplou.*';

    