#### scan读取一行数据

```powershell
--查询tablename的一条数据
scan 'tablename', LIMIT => 1
--查询user表中的所有信息
scan 'user'
--列族查询
scan 'user',{COLUMNS => 'info',RAW => true,VERSIONS => 5}
--多列族查询
scan 'user',{COLUMNS => ['info','data']}
scan 'user',{COLUMNS => ['info:name','data:pic']}
--指定列族和列名，以及限定的版本查询
scan 'user',{COLUMNS => 'info:name',VERSIONS => 5}
--按照数据值指定多个列族进行模糊查询
scan 'user',{COLUMNS => ['info','data'],FILTER => "(QualifierFilter(=,'substring:a'))"}
--rowkey的范围值查询
scan 'user',{COLUMNS => 'info',STARTROW => 'rk001',ENDROW => 'rk003'}
--rowkey模糊查询
scan 'user',{FILTER => "PrefixFilter('rk')"}
--指定数据范查询
scan 'user',{TIMERANGE => [1,2]}

```

#### get、put基本命令

```powershell
--查看帮助命令
help
--查看当前数据库中有哪些表
list

--创建一张表
--创建user表，包含info、data两个列族
create 'user','info','data'
create 'user', {NAME => 'info',VERSIONS => '3'},{NAME => 'data'}

--添加数据操作
--将信息表插入user表中，row key为rk001,在info列中添加name列标记符，值为zhangsn
put 'user','rk001','info:name','zhangsan'
--将信息插入到user表，row key为rk001,将pic列标识符添加到列族data，值为picture
put 'user','rk001','data:pic','picture'

--查询数据操作
--获取user表中row key为rk001的所有信息
get 'user','rk001'
--查看user表中rowk key为rk001,info列族的所有信息
get 'user','rk001','info'
--查看user表中row key为rk001,info列族的name、age列表符的信息
get 'user','rk001','info:name','info:age'
--在user表中获得row key为rk001，info、data列的信息
get 'user','rk001','info','data'
get 'user','rk001',{COLUMN => ['info','data']}
get 'user','rk001',{COLUMN => ['info:name','data:pic']}
--获得用户表中row key为rk001,而cell值为zhangsan的信息
get 'user','rk001',{FLTER => "(QualifierFilter(=,'substring:a'))"}
--指定row key和列值模糊查询
```

#### 更新数据值

```powershell
--更新版本号
alter 'user',NAME => 'info',VERSIONS => 5
```

#### 删除操作

```powershell
--指定rowkey以及列表进行删除
delete 'user','rk001','info:name'
--指定rowkey，列名以及字段值进行删除
delete 'user','rk001','info:name',123
--删除一个列族
alter 'user',NAME => 'info',METHOD =>'delete'
alter 'user','delete' => 'info'
--清空表数据
truncate 'user'
```

#### 移除表

```powershell
drop 'user'
```

#### Count操作

```powershell
count 'user'
```
