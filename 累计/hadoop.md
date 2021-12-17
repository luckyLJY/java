### orcal

#### 修改数据、及删除表

```sql
//修改数据
select r.*,rowid from tmp_invm r;
//删除表
drop table table_name;
```

#### case wher语法

```sql
select  /*+parallel(32)*/  //多线程
  sum(case when substr(key_1,0,4)='0036' then curr_bal end) as orgm
from 
    invm;
```



### idea

any rule插件使用快捷键alt+a

Tranlation:英文翻译插件：ctrl+shit+Y

### hive

#### 数值字段转化

```sql
case
  --处理非科学计数法表示的字符串
  when length(regexp_extract('字符串','([0-9]+\\.)([0-9]+)(E-*[0-9]+)',2))=0
then '字符串'
  --处理整数
  when length(regexp_extract('字符串','([0-9]+\\.)([0-9]+)(E[0-9]+)',2))<=cast(regexp_extract('字符串','(E)([0-9]+)',2) as int)
    then rpad(regexp_replace(regexp_extract('字符串','([^E]+)',1),'\\.',''),cast(regexp_extract('字符串','(E)([0-9]+)',2) as int)+1,'0')
  --处理小数
  when length(regexp_extract('字符串','([0-9]+\\.)([0-9]+)(E[0-9]+)',2))>cast(regexp_extract('字符串','(E)([0-9]+)',2) as int)
    then concat(substr(regexp_replace(regexp_extract('字符串','([^E]+)',1),'\\.',''),1,cast(regexp_extract('字符串','(E)([0-9]+)',2) as int)+1),'\.',
    substr(regexp_replace(regexp_extract('字符串','([^E]+)',1),'\\.',''),cast(regexp_extract('字符串','(E)([0-9]+)',2) as int)+2))
  --处理类似“3.4E-6”这种字符串
  when '字符串' regexp 'E-'
    then concat('0.',repeat('0',cast(regexp_extract('字符串','(E)(-)([0-9]+)',3) as int)-1),regexp_replace(regexp_extract('字符串','(.+)(E)',1),'\\.',''))
  else '字符串'
end
```

#### localtask超轻量的job在提交前session中关闭自动mapjoin

```powershell
解决：set hive.auto.convert.join=false
```

### yarn操作命令

#### 任务管理命令

```powershell
--查看任务
yarn application -list
--根据Application状态过滤：yarn application -list -appStates （所有状态：ALL、NEW、NEW_SAVING、SUBMITTED、ACCEPTED、RUNNING、FINISHED、FAILED、KILLED）\
yarn application -list -appStates FINISHED
--杀掉任务
yarn application -kill application_1612577921195_0001
--列出所有Application尝试的列表：
yarn applicationattempt -list <ApplicationId>
--打印ApplicationAttemp状态：
yarn applicationattempt -status <ApplicationAttemptId>
--列出所有Container：
yarn container -list <ApplicationAttemptId>
--打印Container状态：    
yarn container -status <ContainerId>
--列出所有节点：
yarn node -list -all
```

### flume与storm了解

- Apache Flume 是一种用于收集大量流数据，尤其是日志的服务。Flume 使用称为数据接收器的机制将数据推送给消费者。Flume 可以开箱即用地将数据推送到许多流行的接收器，包括 HDFS、HBase、Cassandra 和一些关系数据库。
- Apache Storm 涉及流数据。它是批处理和流处理之间的桥梁，Hadoop 本身并不是设计用来处理的。Storm 持续运行，处理传入的数据流并将其分成批次，因此 Hadoop 可以更轻松地摄取它。数据源称为 spout，每个处理节点都是一个 bolt。Bolt 对数据执行计算和处理，包括将输出推送到数据存储和其他服务。
- 如果您需要开箱即用的东西，请选择 Flume，一旦您决定是推还是拉更有意义。如果流数据现在只是您已经开发的 Hadoop 环境的一个小附加组件，Storm 是一个不错的选择。

- 可以使用风暴将日志数据摄取到 Hadoop 集群中
- 我们可以用风暴代替水槽

​        
​        
​        
​        
​        
​        
​        
​        
​        
​    
