---
typora-root-url: img_hive
---

hive-site.xml

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
<property>
<name>javax.jdo.option.ConnectionUserName</name>
<value>root</value>
</property>
<property>
<name>javax.jdo.option.ConnectionPassword</name>
<value>123456</value>
</property>
<property>
<name>javax.jdo.option.ConnectionURL</name>
<value>jdbc:mysql://node03:3306/hive?createDatabaseIfNotExist=true&amp;useSSL=false</value>
</property>
<property>
<name>javax.jdo.option.ConnectionDriverName</name>
<value>com.mysql.jdbc.Driver</value>
</property>
<property>
<name>hive.metastore.schema.verification</name>
<value>false</value>
</property>
<property>
<name>datanucleus.schema.autoCreateAll</name>
<value>true</value>
</property>
<property>
<name>hive.server2.thrift.bind.host</name>
<value>node03</value>
</property>
<!--
     <property>
<name>hive.metastore.uris</name>
<value>thrift://node03:9083</value>
<description>JDBC connect string for a JDBC metastore</description>
</property>
<property>
<name>hive.metastore.local</name>
<value>false</value>
<description>this is local store</description>
</property>
-->
</configuration>

```

## 2.6Hive的交互

第一种交互

```
//登陆hive
cd /export/servers/apache-hive-3.1.0-bin/bin
bin/hive
```

- 创建一个数据库

```sql
create database if not exists myhive;
//查询库中所有表
show databases;
//使用某个库
use myhive;
//创建表
create table t_test(id int, name string);
//查表
show tables;
```

第二种交互方式：使用sql语句或者sql脚本进行交互

hive官方推荐使用hiveserver2的这种交互方式，需要我们启动hiveserver2这个服务端，然后通过客户 端去进行连接 启动服务端（前台启动命令如下） 

```shell
cd /export/servers/apache-hive-3.1.0-bin/ bin/hive --service hiveserver2 
```

重新开一个窗口启动我们的客户单进行连接 

```shell
cd /export/servers/apache-hive-3.1.0-bin bin/beeline
bin/beeline

!connect jdbc:hive2://node03:10000/myhive -n root -p
beeline -u jdbc:hive2://node03:10000/myhive -n root -p
```

!connect jdbc:hive2://node03:10000

hive-site.xml

```xml
<property>
<name>hive.server2.thrift.bind.host</name>
<value>node03</value>
</property>
<property>
<name>hive.server2.thrift.bind.port</name>
<value>10000</value>
</property>
```

hadoop中的core-site.xm

```
<property>
    <name>hadoop.proxyuser.node03.hosts</name>
    <value>*</value>
</property>
<property>
    <name>hadoop.proxyuser.node03.groups</name>
    <value>*</value>
</property>
```







不进入hive客户端直接执行hive的sql语句

```shell
cd /export/servers/apache-hive-3.1.0-bin/bin
bin/hive -e "create database if not exists myhive;"
```

或者我们将我们的hql语句写成sql脚本执行

```shell
cd /export/server
vim hive.sql
//hive.sql	
create database if not exists mytest;
use mytest;
create table stu(id int, name string);
```

执行脚本

```sql
hive -f /export/server/hive.sql
```



```sql
CREATE [EXTERNAL] TABLE [IF NOT EXISTS] table_name
[(col_name data_type [COMMENT col_comment], ...)]
[COMMENT table_comment]
[PARTITIONED BY (col_name data_type [COMMENT col_comment], ...)]
[CLUSTERED BY (col_name, col_name, ...)
[SORTED BY (col_name [ASC|DESC], ...)] INTO num_buckets BUCKETS]
[ROW FORMAT row_format]
[STORED AS file_format]
[LOCATION hdfs_path]
```

![](/2021-10-09_130221.png)

![](/2021-10-09_130510.png)

![](/2021-10-09_130628.png)

- 内部表

**创建表并指定字段之间的分隔符**

```sql
create table if not exists stu2(id int,name string) row format delimited fields terminated by '\t' stored as textfile location '/user/stu2';
```

**根据查询结果创建表**

```sql
create table stu3 as select * from stu2; # 通过复制表结构和表内容创建新表
```

 **根据已经存在的表结构创建表** 

```sql
create table stu4 like stu2;
```

**查询表的类型** 

```sql
desc formatted stu2;
```

- 外部表

**操作案例** 

分别创建老师与学生表外部表，并向表中加载数据

**创建老师表**

```sql
create external table teacher (t_id string,t_name string) row format delimited fields terminated by '\t';
```

**创建学生表**

```sqlite
create external table student (s_id string,s_name string,s_birth string , s_sex string ) row format delimited fields terminated by '\t';
```

**加载数据** 

```sql
load data local inpath '/export/servers/hivedatas/student.csv' into table student; 
```

**加载数据并覆盖已有数据** 

```sql
load data local inpath '/export/servers/hivedatas/student.csv' overwrite into table student;
```

从hdfs文件系统向表中加载数据（需要提前将数据上传到hdfs文件系统）

```sql
 cd /export/servers/hivedatas 
 hdfs dfs -mkdir -p /hivedatas
 hdfs dfs -put techer.csv /hivedatas/
 load data inpath '/hivedatas/techer.csv' into table teacher;
```

### 1.4. 分区表 

​		在大数据中，最常用的一种思想就是分治，我们可以把大的文件切割划分成一个个的小的文件，这样每次操作一个小的 文件就会很容易了，同样的道理，在hive当中也是支持这种思想的，就是我们可以把大的数据，按照每天，或者每小时 进行切分成一个个的小的文件，这样去操作小的文件就会容易得多了

 创建分区表语法 

```sql
create table score(s_id string,c_id string, s_score int) partitioned by (month string) row format delimited fields terminated by '\t';
```

创建一个表带多个分区

```sql
create table score2 (s_id string,c_id string, s_score int) partitioned by (year string,month string,day string) row format delimited fields terminated by '\t';
```

加载数据到分区表中

```sql
load data local inpath '/export/servers/hivedatas/score.csv' into table score partition (month='202110');
```

加载数据到多分区表中

```sql
load data local inpath '/export/servers/hivedatas/score.csv' into table score2 partition(year='2021',month='10',day='10');
```

多分区表联合查询(使用 union all )

```sql
select * from score where month = '201806' union all select * from score where month = '201806';
```

查看分区

```sql
show partitions score;
```

添加一个分区

```sql
alter table score add partition(month='201805');
```

删除分区

```sql
alter table score drop partition(month = '201806');
```

### 1.5分桶表

将数据按照指定的字段进行分成多个桶中去，说白了就是将数据按照字段进行划分，可以将数据按照字段划分到多个文 件当中去 

开启 Hive 的分桶功能

```sql
set hive.enforce.bucketing=true;
```

设置 Reduce 个数

```sql
set mapreduce.job.reduces=3;
```

创建桶表

```sql
create table course (c_id string,c_name string,t_id string) clustered by(c_id) into 3 buckets row format delimited fields terminated by '\t';
```

桶表的数据加载，由于通标的数据加载通过hdfs dfs -put文件或者通过load data均不好使，只能通过insert overwrite 

创建普通表，并通过insert overwrite的方式将普通表的数据通过查询的方式加载到桶表当中去

创建普通表

```sql
create table course_common (c_id string,c_name string,t_id string) row format delimited fields terminated by '\t'; 
```

普通表中加载数据 

```sql
load data local inpath '/export/servers/hivedatas/course.csv' into table course_common;
```

 

通过insert overwrite给桶表中加载数据

```sql
 insert overwrite table course select * from course_common cluster by(c_id);
```

