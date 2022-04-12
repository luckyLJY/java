## 基本操作
```sql
# 查看表结构
!desc database_name.table_name;

# 新建表
create table table_name(
    val varchar(10),
    id INT,
    CONSTRAINT pk PRIMARY kEY(id)
)
COMPESSION='SNAPPY';

# 查看主键
!priamarykeys database_name.table_name;

# 插入数据
upsert into database_name.table_name values('张三'，1);

#删除表
drop table database_name.table_name;

# 修改表结构
alter table database_name.table_name add date varchar(10);

# 删除数据
delete from database_name.table_name where val='张三';


```