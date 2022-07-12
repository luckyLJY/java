## 操作
#### 表级操作

    # 创建模式
    CREATE SCHEMA  scm1;
    # 查看数据库
    SELECT datname FROM pg_database;
    # 创建表
    create table scm1.test(id int)
    #查看表
    select * from  scm1.test
    # 创建表空间
    CREATE TABLESPACE testday2 RELATIVE LOCATION 'tb_tablespace/testday2';
    # 查看表空间
    SELECT spcname FROM pg_tablespace;

    #创建列存储表
    create table testday2(date1 date,sex varchar,num int) WITH (ORIENTATION = COLUMN);
    # 创建分区表(按日期分区，动态分区)
    create table part1(
    partid int ,
    col2 date,
    col3 int
    ) 
    partition by range(col2) 
    interval('1 day') 
    (
    partition part1 values less than ('20210101'),
    partition part2 values less than ('20210102')
    );

    # 分区 [MINVALUE,0),[0,2018),[2018,2019),[2019,2020)  无法插入2021的数据，只能往后追加分区
    create table test(date1 date,sex varchar,num int) WITH (ORIENTATION = COLUMN)
    PARTITION BY RANGE (num) (PARTITION P0 VALUES LESS THAN(0))ENABLE ROW MOVEMENT;

    ALTER TABLE test ADD PARTITION P2018 VALUES LESS THAN (2018);
    ALTER TABLE test ADD PARTITION P2019 VALUES LESS THAN (2019);
    ALTER TABLE test ADD PARTITION P2020 VALUES LESS THAN (2020);

    select * from test partition (P2018)
    select * from test partition (P2019)
    select * from test partition (P2020)

    ##############################################################################################

    ### (2000-2100) 
    create table test(date1 date,sex varchar,num int) WITH (ORIENTATION = COLUMN)
    PARTITION BY RANGE (num)
    (PARTITION P0 START (0) END (1) EVERY (1)
    )ENABLE ROW MOVEMENT;

    ALTER TABLE test ADD PARTITION P2017 START (1) END (2020) EVERY (1);
    ##############################################################################################
    # 查看分区
    select relname,parentid from pg_partition

    select * from test partition(p2);



    select * from part1
    insert into part1 values(2,'2021-01-01',1),(3,'2021-01-01',1)

    # 按分区查询
    select * from part1 partition for ('2022-01-04')



    ## day表  ###
    create table day(statsday date,
        statsmonth varchar,
        statsyear INTEGER
        ) WITH (ORIENTATION = COLUMN)
    PARTITION BY RANGE (statsday)
    (PARTITION P0 START ('2000-01-01') END ('2100-01-01') EVERY ('1 year')
    )ENABLE ROW MOVEMENT;


    ##  month 表 #######

    create table month(
        statsmonth varchar,
        statsyear INTEGER
        ) WITH (ORIENTATION = COLUMN)
    PARTITION BY RANGE (statsmonth)
    (PARTITION P2000 less than ('2001-01'),
    PARTITION P2001 less than ('2002-01'),
    ........
    PARTITION P2099 less than ('2100-01'),
    PARTITION P2100 less than ('2101-01')
    )ENABLE ROW MOVEMENT;


    ### year表 ####
    create table year(
        statsyear INTEGER
        ) WITH (ORIENTATION = COLUMN)
    PARTITION BY RANGE (statsyear)
    (PARTITION P0 START (2000) END (2100) EVERY (1)
    )ENABLE ROW MOVEMENT; 
    
    ####################
    select relname,parentid from pg_partition where parentid = 

    delete from pg_partition where 1=1

    select * from testday1 partition(p0_0);
    select * from testday partition(p0_22);
