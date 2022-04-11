# GAUSS
## 建表语句
```sql
-- 建表
create table dbName.tableName(
    id varchar2(64),
    acct_no varchar2(32),
    post_date date
)
DISTRIBUTE BY HASH(id)  --分布键使得数据分布均匀
PARTITION BY RANGE(post_date)(
    PARTITION part_20220329 values less than(to_date('2022-03-29 00:00:00')),
    PARTITION part_maxvalue values less than (MAXVALUE)
);

--添加注释
comment on table dbName.tableName.columnName is 'columnsName的备注' 
--修改表结构
alter table table_name modify column_name varchar(10);

```