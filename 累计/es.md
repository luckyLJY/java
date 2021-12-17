## 笔记

### 集群

```powershell
# 查询集群健康状态
GET _cluster/health

# 查询所有节点
GET _cat/nodes

# 查询指定索引分片的分布
GET _cat/shards/order_stpprdinf_2019-12?v

# 查询所有插件
GET _cat/plugins
```

### 索引相关查询

```powershell
# 查询所有索引及容量
GET _cat/indices

# 查询索引映射结构
GET my_index/_mapping

# 查询所有的相同前缀索引
GET my-*/_search

# 查询所有索引模板
GET _template

# 查询具体索引模板
GET _template/my_template
```

### 索引相关操作

1. 创建索引模板
   ```json
   # 创建索引模板
   PUT _template/test_hot_cold_template
   {
       "index_patterns": "test_*",
       "settings": {
           "number_of_shards" : 3,
           "index.number_of_replicas": 1
        },
       "mappings": {
         "order": {
             "dynamic": false, 
             "properties": {
                 "id": {"type": "long"},
                 "name": {"type": "keyword"}
             }
         }    
       },
       "aliases": {
         "test": {}
       }      
   }
   
   # 根据模板创建索引并写入数据
   POST test_hot_cold-2019-12-01/order
   {
     "id":1,
     "name":"cwx"
   }
   
   # 删除模板
   DELETE _template/order_stpprdinf_template
   ```

2.  直接创建索引
   ```json
   PUT my_index
   {
     "mappings": {
       "doc": {
         "properties": {
           "name": {
             "type": "text"
           },
           "blob": {
             "type": "binary"
           }
         }
       }
     }
   }
   
   
   -- 增加字段
   put my_index/_mapping/doc
   {
       "properties": {
         "cwxtest": {"type": "keyword"}
       }
   }
   
   -- 数据冷备份
   PUT _snapshot/my_backup  # my_backup 备份的名称
   {
       "type": "order", 
       "settings": {
           "location": "/mount/backups/my_backup" 
       }
   }
   
   ```

3. 写入索引
   ```json
   PUT my_index/doc/1
   {
     "name": "Some binary blob",
     "blob": "U29tZSBiaW5hcnkgYmxvYg==" 
   }
   
   ```
4. 删除索引
   ```json
   DELETE my-index
   
   ```
5. 修改索引setting
   ```json
   PUT /test_hot_cold-2019-12-01/_settings 
   { 
     "settings": { 
       "index.routing.allocation.require.hotwarm_type": "cold"
     } 
   }
   
   ```

### DSL query查询

```json

--1. 查询所有
GET _search
{
  "query":{
    "match_all":{}
  }
}

--2. 查询单个索引的固定属性
--精确匹配
GET _search
{
  "query":{
    "term":{"name":"you"}
  }
}

--模糊匹配
GET _search
{
  "query":{
    "match":{"name":"you"}
  }
}

--范围查找
GET _search
{
  "query":{
    "range":{
      "age":{
        "gte":15,
        "lte":25
      }
    }
  }
}

GET indexName/_search
{
  "query":{
    "wildcard":{"relatedId":"*672499460503*"}
  }
}

--3.功能性查询
--过滤
GET my_index/_search
{
  "query":{
    "bool":{
      "filter":{
        "term":{
          "age":1095
        }
      }
    }
  }
}

--或 or
GET my_index/_search
{
  "query":{
    "bool":{
      "should":[
          {
            "term":{ "name":"you"}
          },
          {
            "match":{"age":20}
          }
        ]
    }
  }
}

--与 AND
GET my_index/_search
{
  "query":{
    "bool":{
      "must":[
          {
            "match":{"name":"you"}
          },
          {
            "range":{ "age":{"from":10,"to":20}}
          }
        ]
    }
  }
}

--必须 =
GET my_index/_search
{
  "query":{
    "bool":{
      "must":{
        "range":{
          "age":{"from":10,"to":20}
        }
      }
    }
  }
}

--必须不 not
GET my_index/_search
{
  "query":{
    "bool":{
      "must_not":{
        "term":{
          "name":"you"
        }
      }
    }
  }
}

--复合查询
GET my_index/_search
{
  "query":{
    "bool":{
      "should":
        [
          {"match":{"age":40}},
          {"match":{"age":20}}
        ],
        "filter":
        {
          "match":{"name":"you"}
        }
    }
  }
}

--4.索引迁移
--场景 从A索引 复制到B索引
POST _reindex
{
  "source":
  {
    "index":"my_index"  
  },
  "dest":
  {
    "index":"new_my_index"
  }
}

--5.基于查询删除
POST my_index/_delete_by_query
{
  "query":
  {
    "term":
    {
      "cameraId":"00000002"
    }
  }
}

--查询
GET my_index/_search
{
  "query":
  {
    "term":
    {
      "cameraId":"00000002"
    }
  }
}
```

### 按时间范围查询：

```json
GET order_stpprdinf_2019-12/_search
{
  "size":10,
  "query":{
    "range":{
      "order_time":{
        "gte":"2019-12-11T00:00:00+08:00",
        "lte":"2019-12-11T23:59:59+08:00"
      } 
    }
  }
}
```

### 按条件删除

```json
GET order_stpprdinf_2019-12/_delete_by_query?conflicts=proceed
{
  "query":{
    "range":{
      "order_time":{
        "gte":"2019-12-11T00:00:00+08:00",
        "lte":"2019-12-11T23:59:59+08:00"
      } 
    }
  }
}

```

### 模糊匹配和范围查询

```json
GET /_search
{
  "size":0,
  "query": {
    "bool": {
      "must": [
        {"wildcard": {
          "path": "/data/log/idcard.log*"
        }},
        {"range": {
            "@timestamp": {
            "gte": "2018-05-09",
            "lte": "2018-05-09"
        }}
      }
    ]
  }
}
}

```

### es修改字mapping

```json
POST 索引/_mapping
{
  "properties": {
    "city": { 
      "type":     "text",
      "fielddata": true
    }
  }
}

//curl命令
curl -H 'Content-Type: application/json' -XPUT "http://192.168.31.203:9200/dcs_new"
curl  -H 'Content-Type: application/json' -XPOST "http://192.168.31.203:9200/dcs_new/qzwl_dcs/_mapping?pretty" -d ' 
{
    "qzwl_dcs": {
            "properties": {
                "value": {
                    "type": "double",
                    "store": "true",
                    "ignore_malformed": "true"
                }
            }
        }
  }

```

### curl操作es命令

####     集群常用命令

- 查看命令

```powershell
curl  -XGET 'http://hadoop137:9200'
```

- 查看集群状态

```powershell
curl -XGET 'http://hadoop137:9200/_cluster/state?pretty'

#这里在url后面添加了pretty是为了让其在控制台上输出的结果是一个优美的json格式
```

#### 索引库常用命令

- 语法

```json
curl ‐X<VERB> '<PROTOCOL>://<HOST>:<PORT>/<PATH>?<QUERY_STRING>' ‐d '<BODY>'
```

- 查看所有索引信息

```powershell
curl -XGET 'http://hadoop137:9200/_cat/indices?pretty&v'
```

- 创建索引

```powershell
curl -XPUT 'http://hadoop137:9200/upuptop?pretty'
```

- 删除索引

```powershell
curl -XDELETE 'http://hadoop137:9200/upuptop?pretty'
```

#### 文档常用命令

- 创建文档

```powershell
# 9200/索引库名/文档类型/id/  -d 文档内容
# id可以忽略，ES会自动生成id，如果id存在，那么就是更新数据，字段可以增加

curl -XPOST 'http://hadoop137:9200/upuptop/stu/1?pretty' -H 'Content-Type: application/json' -d '
{
"name":"shaofei",
"age":22,
"sex":1
}'

```

- 修改文档

```powershell
# 给id为1的学生修改姓名为upuptop，年龄修改为25，添加学号字段
curl -XPOST 'http://hadoop137:9200/upuptop/stu/1?pretty' -H 'Content-Type: application/json' -d '
{
"name":"upuptop",
"age":25,
"sex":0,
"number":"1501260222"
}'
```

- 查看所有文档

```powershell
curl -XGET 'http://hadoop137:9200/upuptop/stu/_search?pretty'
```

- 根据id查看文档

```powershell
curl -XGET 'http://hadoop137:9200/upuptop/stu/1?pretty'
```

- 删除文档

```powershell
curl -XDELETE 'http://hadoop137:9200/upuptop/stu/1?pretty'
```

### POSTMAN操作

#### es对多个字段求和

```sql
select sum(value_name),sum(value1_name) from index
```

```json
POST index/_search
{
    "aggs":{
        "value_name":{
            "sum":{
                "field":"OTHER_CHANNEL_COUNT"
            }
        },
        "value1_name":{
            "sum":{
                "field":"OTHER_CHANNEL_AMOUNT"
            }
        }
    }
}
```

#### es查询多个字段

```sql
select value1,value2 from index    where datetime >=now
```

```
POST index/_search?
{
    "_source":[_value1,value2],
    "query":{
        "range":{
            "datatime":{
                "gte":now,
                "format":"ddMMyyyy"
            }
        }
    }
}
```

#### es排序求第行数据

```sql
select * from index order by _id limit 1
```

```json
GET index/_search
{
    "sort":[
        {
            "_id":{
                "order":desc
            }
        }
    ],"size":1
}
```

#### 排序

```sql
select * from index order by hours
```

```json
GET index/_search
{
    "sort:[
        {
            "hours.keyword":{
                "order":desc
            }
        }
    ]"
}

--这里的排序字段需要使用：字段.keyword
```

## 运维

### 设置es的de默认分区数和副本数

#### 修改现有副本数为0

```json
curl -XPUT https://192.168.x.x:9200/_settings -d '
 {
    "index":{
      "number_of_replicas":0
    }
}'
```

#### 设置es默认模板

```json
#这样后面生成log-*的索引都会按照这个模板来了。分区10，副本0了。
curl -XPUT https://192.168.x.x:9200/_template/log -d '{
  "template": "log-*",
  "settings": {
    "number_of_shards": 10,
    "number_of_replicas": "0"
  }
}'

```

#### es删除历史日志

```json
#操作
首先，贴出我从kafka采集日志到es的相关logstash的配置
input{
  kafka{
  topics => ["logs-normal","logs-error","logs-point"]
     bootstrap_servers => "192.168.x.x:9092,192.168.x.x:9092:9092,192.168.x.x:9092"
     codec => json
     group_id=> "logstash"
     codec => multiline {
        pattern => "\s"
        negate=>true
        what => "previous"
    }
  }
}
filter{
   grok{
        match => {"message" => "\[(?<datetime>\d{4}-\d{2}-\d{2}\s\d{2}:\d{2}:\d{2},\d{3})"}
   }
   date{
        match => ["datetime", "yyyy-MM-dd HH:mm:ss,SSS"]
        target => "@timestamp"
   }
   mutate {
    remove_field => ["datetime"]
  }
}
output{
    elasticsearch {
            action => "index"
            hosts  => ["192.168.x.x:9200","192.168.x.x:9200","192.168.x.x:9200"]
            index  => "applog-%{+YYYY.MM.dd}"
    }
}




#大家注意 index => "applog-%{+YYYY.MM.dd}",这会根据timestamp的时间来生成每天的日志块，而我删除日志，也是根据索引+日期
来删除的。这样清楚多少天以前的就很简单了。
#删除代码
然后根据索引+日期删除日志
curl -XDELETE  "192.168.101.123:9200/applog-2016.12.26"
```

#### es定时删除数据

有的时候我们在使用ES时，由于资源有限或业务需求，我们只想保存最近一段时间的数据，所以有如下脚本可以定时删除数据

新建 delete_es_by_day.sh 脚本：

```powershell
#!/bin/sh  
# example: sh  delete_es_by_day.sh logstash-kettle-log logsdate 30  

index_name=$1  
daycolumn=$2  
savedays=$3  
format_day=$4  

if [ ! -n "$savedays" ]; then  
  echo "the args is not right,please input again...."  
  exit 1  
fi  

if [ ! -n "$format_day" ]; then  
   format_day='%Y%m%d'  
fi  

sevendayago=`date -d "-${savedays} day " +${format_day}`  

curl -XDELETE "10.130.3.102:9200/${index_name}/_query?pretty" -d "  
{  
        "query": {  
                "filtered": {  
                        "filter": {  
                                "bool": {  
                                        "must": {  
                                                "range": {  
                                                        "${daycolumn}": {  
                                                                "from": null,  
                                                                "to": ${sevendayago},  
                                                                "include_lower": true,  
                                                                "include_upper": true  
                                                        }  
                                                }  
                                        }  
                                }  
                        }  
                }  
        }  
}"  

echo "ok"

```

注解：脚本传入参数说明：

1.索引名；
2.日期字段名；
3.保留最近几天数据，单位天；
4.日期格式，可不输（默认形式20160101）

#### es根据查询结果删除数据(delete by query)

1.安装。打开命令行，切到Elasticsearch文件夹中，运行如下命令：

```powershell
sudo bin/plugin install delete-by-query
```

每个节点都要安装，并重启服务

2.使用delete by query

3.执行删除命令

```powershell
curl -XDELETE 'https://10.0.21.xx:9200/applog/logs/_query?pretty' -d ' 
{
  "query": {
    "filtered": {
      "filter": {
        "range": {
          "@timestamp": {
            "lte": "2016-11-21"
          }
        }
      }
    }
  }
}'

```

4.合并

```powershell
curl -XPOST 'https://10.0.21.xx:9200/applog/_optimize?max_num_segments=1'
```

## 问题

#### kibana关闭

```powershell
ps -ef | grep '.*node/bin/node.*src/cli'
username   5989  2989  1 11:33 pts/1    00:00:05 ./../node/bin/node ./../src/cli
kill -9 5989
```
