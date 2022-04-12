# orcal
## 基本操作

```sql
//修改数据
select r.*,rowid from tmp_invm r;

//删除表
drop table table_name;
1、su – oracle 不是必需，适合于没有DBA密码时使用，可以不用密码来进入sqlplus界面。
2、sqlplus /nolog 或sqlplus system/manager 或./sqlplus ;
3、SQL>connect / as sysdba ;（as sysoper）或
connect internal/oracle AS SYSDBA ;(scott/tiger)
conn sys/change_on_install as sysdba;
4、SQL>startup; 启动数据库实例
5、 查看当前的所有数据库: select * from v$database;
select name from v$database;
desc v$databases; 查看数据库结构字段
7、怎样查看哪些用户拥有SYSDBA、SYSOPER权限:
SQL>select * from V_$PWFILE_USERS;
Show user;查看当前数据库连接用户
8、进入test数据库：database test;
9、查看所有的数据库实例：select * from v$instance;
如：ora9i
10、查看当前库的所有数据表：
SQL> select TABLE_NAME from all_tables;
select * from all_tables;
SQL> select table_name from all_tables where table_name like ‘u’;
TABLE_NAME———————————————default_auditing_options
13、获得CQI.T_BBS_XUSER表中的记录：
select * from CQI.T_BBS_XUSER;
14、增加数据库用户：(test11/test)
create user test11 identified by test default tablespace users Temporary TABLESPACE Temp;
15、用户授权:
grant connect,resource,dba to test11;
grant sysdba to test11;
commit;
16、更改数据库用户的密码：(将sys与system的密码改为test.)
alter user sys indentified by test;
alter user system indentified by test;
17、给表名添加主键
alter table table_name add CONSTRAINT PK_table_name key(id);
```

## 查看表结构信息
```sql
===================================================================================== 
--获取当前用户表信息
select * from user_tables;
字段包含：
    table_name,tablespace_name,last_analyzed等
--所有用户的表
select * from all_tables;
字段包含：
    ower,table_name,tablespace_name,last_analyzed等
--包括系统表
select * from dba_tables;
字段包含：
    ower,table_name,tablespace_name,last_analyzed等
all_objects包含：    
    ower,object_name,subobject_name,object_id,created,last_ddl_time,timestamp,status等 


=====================================================================================    
--获取当前用户下表的字段
select * from user_tab_colums where table_name='用户表'
字段包含：
    table_name,column_name,data_type,data_length,data_precision,data_scale,nullable,column_id等
--获取所有用户下的字段
select * from all_tab_columns where table_name='用户表'
字段包含：
    ower,table_name,column_name,data_type,data_length,data_precision,data_scale,nullable,column_id等
--获取包含系统表的字段
select * from dba_tab_columns where table_name='用户表'
字段包含：
    ower,table_name,column_name,data_type,data_length,data_precision,data_scale,nullable,column_id等 

===================================================================================== 
--获取当前用户表注释
select * from user_tab_comments;
字段包含：
    user_tab_comments：table_name,table_type,comments
    相应的还有dba_tab_comments，all_tab_comments，这两个比user_tab_comments多了ower列。

===================================================================================== 
--获取当前用户表字段注释
select * from user_col_comments
字段包含：
    user_col_comments：table_name,column_name,comments
    相应的还有dba_col_comments，all_col_comments，这两个比user_col_comments多了ower列。 

```

## 查看字符集
```sql
--oracle服务端字符集
select userenv('language') from dual;
--oracle客户端字符集
echo $NLS_LANG
--连接oracle数据库
sqlplus / as sysdba
--oracle查询几条数据
select * from table_name rownum < 10;
--sqlplus设置显示效果
set linesize 300;
set pagesize 100;
```

## ogg问题
```sql
2019-06-24 10:34:00  ERROR   OGG-00446  Redo sequence 51868 no longer available in online logs for thread 1 without archiving enabled, SQL <SELECT MAX(sequence#)  FROM v$log WHERE thread# = :ora_thread>, error retrieving redo file name for sequence 51868, archived = 0, use_alternate = 0Not able to establish initial position for sequence 51868, rba 48308752.

查看最新日志序列号select * from v$Log;
alter extract E247,extseqno 51941,extrba 0
```

## 建表操作
```sql
-- 分区表创建
create table dbName.tableName(
    id varchar2(64),
    acct_no varchar2(32),
    post_date date
)
partition by range(post_date)(
    partition part_20230329 values less than (to_date('2023-03-30 00:00:00','SYYY-MM-DD HH24:MI:SS','nls_calendar=gregorian'))
    
    -- 以下可以不需要
    tablespace USERS --表空间
    pctfree 10  --为一个数据块保留的空间百分比，表示当数据块的可用空间地狱10%后，就不能被insert，只能被用于update
    initrans 2 --指的是一个BLOCK上初始预分区给并行交易控制的空间(ITLs)
    maxtrans 255 --指initrans空间不够，扩充到maxtrans大小
    storage
    (
        initial 80k
        next 1M
        minextents 1
        maxextents unlimited
    )
    --

    -- 最大分区
    partition part_maxvalue values less than (maxvalue)
);

--创建唯一索引
create unique index index_id on dbName.tableName (id,acct_no); 

--添加最大分区
alter table dbName.tableName add partition part_maxvalue values less than (maxvalue);

--删除分区
alter table dbName.tableName drop partition part_maxvalue;

--添加分区
alter table dbName.tableName add partition part_20230401 values less than (to_date('2023-03-30 00:00:00','SYYY-MM-DD HH24:MI:SS','nls_calendar=gregorian')) tablespace USERS;

--拆分最大分区
alter table dbName.tableName split partition part_maxvalue at(to_date('2023-04-12 00:00:00','SYYY-MM-DD HH24:MI:SS','nls_calendar=gregorian')) into (partition part_20230411,partition part_maxvalue);

--添加字段备注
comment on table dbName.tableName.columnName is 'columnsName的备注' 
```
## varchar和varchar2区别
1. varchar是标准sql里面的。varchar2是oracle提供的独有的数据类型。
2. varchar对于汉字占两个字节，对于英文是一个字节，占的内存小，varchar2都是占用两个字节。
3. varchar对空串不处理，varchar2将空串当做null来处理。
4. varchar存放固定长度的字符串，最大长度为2000，varchar2存放可变长度的字符串，最大长度为4000；

## exl造数
```sql
="update f_lender set income_amt = '"&B1&"' where protocol_no='"&A1&"';"
```

## 锁表解表操作
```sql
=========================================================================================
--锁表查询
select count(*) from v$locked_object;
select * from v$locked_object;

-- 查看那个表被锁
select b.owner,b.object_name,a.session_id,a.locked_mode
    from v$locked_object a, dba_objects b
    where b.object_id = a.object_id;

--查看是那个session引起的
select a.OS_USER_NAME,c.owner,c.object_name,b.sid,b.serial#,logon_time
    from v$locked_object a,v$session b,dba_object c
    where a.session_id = b.sid
    and   a.object_id = c.object_id
    order by b.logon_time;

--杀掉对应进程
alter system kill session '1025,41'
其中1025为sid,41为serial#.
```

