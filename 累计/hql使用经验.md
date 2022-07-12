## sql语句执行顺序
在 hive 和 mysql 中都可以通过 explain+sql 语句，来查看执行顺序。对于一条标准 sql 语句，它的书写顺序是这样的：  
select … from … where … group by … having … order by … limit …  
(1)mysql 语句执行顺序：  
from... where...group by... having.... select ... order by... limit …  
(2)hive 语句执行顺序：  
from … where … select … group by … having … order by … limit

--知识点
select from_unixtime(unix_timestamp(),"yyyy-MM-dd HH:mm:ss") -- 获取当前系统时间
-- 使用炸裂函数，创建年份、月份、日期从20220101到今天的时间维度表
SELECT
      substr(date_add(start_date, pos), 1, 4) nf,
      substr(date_add(start_date, pos), 1, 7) yd,
      date_add(start_date, pos) rd
    FROM
      (
        SELECT
          trunc(
            from_unixtime(unix_timestamp(), 'yyyy-MM-dd'),
            'YYYY'
          ) AS start_date,
          date_add(from_unixtime(unix_timestamp(), 'yyyy-MM-dd'), -1) AS end_date
      ) temp lateral VIEW posexplode (
        split(space(datediff(end_date, start_date)), '')
      ) t AS pos,
      val
-- 向分区表插入数据
INSERT OVERWRITE
        TABLE dwd_zwfw_ztk.TC_D_M_ZWFW_SS_HCPBJXX  partition(YYYYMMDD='${flow_startDate}') 

-- over函数使用
1. rank() over()是跳跃排序，有两个第二名时接下来就是第四名(同样是在各个分组内)
     

2. dense_rank() over()是连续排序，有两个第二名时仍然跟着第三名。相比之下row_number是没有重复值的 
    dense_rank() over (
                PARTITION by zzjglx
                order by xzxksxclsxysb desc
            ) as xzxksxclsxysbqspm 
3. row_number() 会根据顺序计算，仅仅是加了序号
    select *,row_number() over(partition by substr(orderdate,1,7) order by cost desc) as numfrom business;

-- with语句使用
with temp1 as(select), temp2 as(select)

-- 左补0
lpad(cast(h as string),2,'0') #向左补2位，如果没有值，就按0补。

-- 常用函数
1. 日期
unix_timestamp:返回当前或指定时间的时间戳	        select unix_timestamp();  select unix_timestamp('2008-08-08 08:08:08'); 
from_unixtime：将时间戳转为日期格式                 select from_unixtime(1218182888);
current_date：当前日期                  select current_date();
current_timestamp：当前的日期加时间     select current_timestamp();
to_date：抽取日期部分                   select to_date('2008-08-08 08:08:08');   select to_date(current_timestamp());
year：获取年                            select year(current_timestamp());
month：获取月                           select month(current_timestamp());
day：获取日                             select DAY(current_timestamp());
hour：获取时                            select HOUR(current_timestamp());
minute：获取分                          select minute(current_timestamp());
second：获取秒                          select SECOND(current_timestamp());
weekofyear：当前时间是一年中的第几周    select weekofyear(current_timestamp());  select weekofyear('2020-01-08');
dayofmonth：当前时间是一个月中的第几天  select dayofmonth(current_timestamp());  select dayofmonth('2020-01-08');
months_between： 两个日期间的月份       select months_between('2020-07-29','2020-06-28');
add_months：日期加减月                  select add_months('2020-06-28',1);
datediff：两个日期相差的天数            select datediff('2019-03-01','2019-02-01');   select datediff('2020-03-01','2020-02-01');
date_add：日期加天数                    select date_add('2019-02-28',1);   select date_add('2020-02-28',1);
date_sub：日期减天数                    select date_sub('2019-03-01',1);   select date_sub('2020-03-01',1);
last_day：日期的当月的最后一天          select last_day('2020-02-28');   select last_day('2019-02-28');
date_format() ：格式化日期   日期格式：'yyyy-MM-dd hh:mm:ss'   select date_format('2008-08-08 08:08:08','yyyy-MM-dd hh:mm:ss');  

2. 取整
round： 四舍五入     select round(4.5);     
ceil：  向上取整     select ceil(4.5);
floor： 向下取整     select floor(4.5);

3. 字符串操作
upper： 转大写         select upper('abcDEFg');
lower： 转小写         select lower('abcDEFg');
length： 长度          select length('abcDEFg');
trim：  前后去空格     select length('   abcDEFg    ');  select length(trim('   abcDEFg    '));
lpad： 向左补齐，到指定长度   select lpad('abc',11,'*');
rpad：  向右补齐，到指定长度  select rpad('abc',11,'*');  
substring: 剪切字符串         select substring('abcdefg',1,3);     select rpad(substring('13843838438',1,3),11,'*');
regexp_replace： SELECT regexp_replace('100-200', '(\\d+)', 'num');   select regexp_replace('abc d e f',' ','');
	使用正则表达式匹配目标字符串，匹配成功后替换！

4. 集合
size： 集合中元素的个数
map_keys： 返回map中的key
map_values: 返回map中的value         select size(friends),map_keys(children),map_values(children) from person;
array_contains: 判断array中是否包含某个元素     select array_contains(friends,'lili') from person;
sort_array： 将array中的元素排序         select sort_array(split('1,3,4,5,2,6,9',','));   
                                         select sort_array(split('a,d,g,b,c,f,e',','));
-- 日志查看经验
https://www.modb.pro/db/326530
https://www.cnblogs.com/barneywill/p/10083731.html

-- 查看命令
1）查看系统自带的函数
hive> show functions;
2）显示自带的函数的用法
hive> desc function upper;
3）详细显示自带的函数的用法
hive> desc function extended upper;

