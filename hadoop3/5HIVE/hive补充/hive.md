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

## HIVE的非等值连接

locate()函数进行功能转换。 


locate(string substr, string str[, int pos]) 
查找字符串str中的pos位置后字符串substr第一次出现的位置，若未找到，则返回0。

```sql
hive> select locate('a','abcd'), locate('b', 'abcd'), locate('f', 'abcd')
结果:  1  2  0
1
2
```



```sql
join模糊匹配 
left join , right join , full join

hive> select * from a left join b on 1=1 where locate(a.col,b.col)>0

hive> select * from a   right join b on 1=1 where locate(a.col,b.col)>0

hive> select * from a full join b  where locate(a.col,b.col)>0
```

除了将locate()直接写在where条件里，也可以使用row_number()来搭配使用。

```sql
select col
from(
    select 
        if(locate(a.col, b.col)>0, b.col, a.col) as col, 
        row_number() over(partition by a.col order by locate(a.col, b.col) desc) as rn
    from a 
    left join b on 1=1
) as a
where rn=1
```

## HIVE开窗

distribute by是控制在map端如何拆分数据给reduce端的。hive会根据distribute by后面列，对应reduce的个数进行分发，默认是采用hash算法。sort by为每个reduce产生一个排序文件。在有些情况下，你需要控制某个特定行应该到哪个reducer，这通常是为了进行后续的聚集操作。distribute by刚好可以做这件事。因此，distribute by经常和sort by配合使用。
注：Distribute by和sort by的使用场景

1. Map输出的文件大小不均。
2.Reduce输出文件大小不均。
3.小文件过多。
4.文件超大。
两种开窗方式区别
patition by是按照一个一个reduce去处理数据的，所以要使用全局排序order by
distribute by是按照多个reduce去处理数据的，所以对应的排序是局部排序sort by
窗口大小: hive 的窗口大小默认是从起始行到当前行.



MAP-SIDE JOIN和REDUCE-SIDE JOIN的区别
map-side join
hive在那些普通的join操作中, 每个on子句都用到了a.id=b.id作为join的连接键.
当多个表进行join链接时, 如果每个on子句使用相同的连接键的话, 那么只会产生一个mapreduce job.
如果所有表中, 有一张表是小表, 那么可以在最大的表通过mapper的时候将小表完全放到内存中, hive可以在map端执行链接过程, 也就是map-side join. 因为hive可以和内存中的小表进行逐一匹配, 省略了reduce过程.
设置属性:
hive.auto.convert.join=true
hive.majoin.smalltable.filesize=25000000(25M)
局限: 要有一份小到足够加载到内存里的表
优化:
使用内存服务器, 扩大节点的内存空间. 针对map join, 可以报一份数据到专门的内存服务器, 在map()方法中, 对每一个输入, 根据key到内存服务器中获取数据进行连接.
reduce-side join
map端按照链接字段进行hash, reduce端完成连接操作. 在map阶段, 把关键字作为key输出, 并在value中标记出数据是来自表1还是表2. 经过shuffle阶段将两表数据按照key自然分组, 然后在reduce阶段, 判断每个value是来自表1还是表2, 再合并数据就可以了
适合两个大表的连接操作
问题:
map阶段没有数据瘦身, shuffle的网络传输和排序性能会很低.
reduce端对两个集合做乘积计算, 消耗内存很大, 容易造成oom.
优化:
使用BloomFilter过滤空连接的数据
对其中一份数据在内存中建立BloomFilter, 另外一份数据在连接之前, 使用BloomFilter判断它的key是否存在, 如果不存在, 那么这个记录是空连接, 可以忽略.
10. MAP-SIDE JOIN加载到内存中的表在内存中以什么形式存在
被加载到内存中的表估计是以jvm对象存储的, 因为在一本讲spark内核的书中在讲spark sql 的优越性能的时候, 讲到spark sql性能的提高, 有一个很大的优化是进行了内存列存储, 而不是传统的jvm对象存储. 因为使用原生的jvm对象存储, 每个对象都要增加十几个字节的额外开销. 另外使用这种方式, 每个数据记录产生一个jvm对象, 如果是大小200b的数据记录,32 GB的对战讲产生1.6亿个对象, 对gc来说也是很大的压力.
而使用了内存列存储, 就是将所有元神数据欸行的列采用原生数组来存储, 将hive
支持的复杂数据类型先序列化后并接成一个字节数组来存储. 这样每个列创建一个jvm对象, 可以快速的gc和紧凑的数据存储, 还可以使用低cpu开销的高效压缩方法来降低内存开销.
对于分析查询中频繁使用的聚合特定列, 性能会得到很大的提高, 因为这些列数据放在一起, 更容易读入内存进行计算
