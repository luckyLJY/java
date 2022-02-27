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

## 1、查询

先使用一个快速入门来引入，然后后面列出的各种查询都是用得比较多的（在我的工作环境是这样），其它没怎么用的这里就不列出了。

### 1.1 快速入门

#### 1.1.1 查询全部

```json
GET index/type/_search
{
    "query":{
        "match_all":{}
    }
}
```

或

```json
GET index/type/_search
```

#### 1.1.2 分页（以term为例）

```json
GET index/type/_search
{
    "from":0,
    "size":100,
    "query":{
        "term":{
            "area":"GuangZhou"
        }
    }
}
```

#### 1.1.3 包含指定字段（以term为例）

```json
GET index/type/_search
{
    "_source":["hobby", "name"],
    "query":{
        "term":{
            "area":"GuangZhou"
        }
    }
}
```

#### 1.1.4 排序（以term为例）

单个字段排序：

```json
GET index/type/_search
{
    "query":{
        "term":{
            "area":"GuangZhou"
        }
    },
    "sort":[
        {"user_id":{"order":"asc"}},
        {"salary":{"order":"desc"}}
    ]
}
```

### 1.2 全文查询

查询字段会被索引和分析，在执行之前将每个字段的分词器（或搜索分词器）应用于查询字符串。

#### 1.2.1 match query

```json
{
  "query": {
    "match": {
      "content": {
        "query": "里皮恒大",
        "operator": "and"
      }
    }
  }
}
```

> operator默认是or，也就是说，“里皮恒大”被分词为“里皮”和“恒大”，只要content中出现两个之一，都会搜索到；设置为and之后，只有同时出现都会被搜索到。

#### 1.2.2 match_phrase query

文档同时满足下面两个条件才会被搜索到：

- （1）分词后所有词项都要出现在该字段中
- （2）字段中的词项顺序要一致

```json
{
  "query": {
    "match_phrase": {
      "content": "里皮恒大"
    }
  }
}
```

### 1.3 词项查询

词项搜索时对倒排索引中存储的词项进行精确匹配，词项级别的查询通过用于结构化数据，如数字、日期和枚举类型。

#### 1.3.1 term query

```json
{
  "query": {
    "term": {
      "postdate": "2015-12-10 00:41:00"
    }
  }
}
```

#### 1.3.2 terms query

term的升级版，如上面查询的postdate字段，可以设置多个。

```json
{
  "query": {
    "terms": {
      "postdate": [
        "2015-12-10 00:41:00",
        "2016-02-01 01:39:00"
      ]
    }
  }
}
```

> 因为term是精确匹配，所以不要问，[]中的关系怎么设置and？这怎么可能，既然是精确匹配，一个字段也不可能有两个不同的值。

#### 1.3.3 range query

匹配某一范围内的数据型、日期类型或者字符串型字段的文档，注意只能查询一个字段，不能作用在多个字段上。

数值：

```json
{
  "query": {
    "range": {
      "reply": {
        "gte": 245,
        "lte": 250
      }
    }
  }
}
```

> 支持的操作符如下：
>
> gt：大于，gte：大于等于，lt：小于，lte：小于等于

日期：

```json
{
  "query": {
    "range": {
      "postdate": {
        "gte": "2016-09-01 00:00:00",
        "lte": "2016-09-30 23:59:59",
        "format": "yyyy-MM-dd HH:mm:ss"
      }
    }
  }
}
```

> format不加也行，如果写的时间格式正确。

#### 1.3.4 exists query

返回对应字段中至少有一个非空值的文档，也就是说，该字段有值（待会会说明这个概念）。

```json
{
  "query": {
    "exists": {
      "field": "user"
    }
  }
}
```

参考《从Lucene到Elasticsearch：全文检索实战》中的说明。

以下文档会匹配上面的查询：

| 文档                   | 说明                             |
| ---------------------- | -------------------------------- |
| {"user":"jane"}        | 有user字段，且不为空             |
| {"user":""}            | 有user字段，值为空字符串         |
| {"user":"-"}           | 有user字段，值不为空             |
| {"user":["jane"]}      | 有user字段，值不为空             |
| {"user":["jane",null]} | 有user字段，至少一个值不为空即可 |

下面的文档不会被匹配：

| 文档            | 说明                       |
| --------------- | -------------------------- |
| {"user":null}   | 虽然有user字段，但是值为空 |
| {"user":[]}     | 虽然有user字段，但是值为空 |
| {"user":[null]} | 虽然有user字段，但是值为空 |
| {"foo":"bar"}   | 没有user字段               |

#### 1.3.5 ids query

查询具有指定id的文档。

```json
{
  "query": {
    "ids": {
      "type": "news",
      "values": "2101"
    }
  }
}
```

> 类型是可选的，也可以以数据的方式指定多个id。

```json
{
  "query": {
    "ids": {
      "values": [
        "2101",
        "2301"
      ]
    }
  }
}
```

### 1.4 复合查询

#### 1.4.1 bool query

因为工作中接触到关于es是做聚合、统计、分类的项目，经常要做各种复杂的多条件查询，所以实际上，bool query用得非常多，因为查询条件个数不定，所以处理的逻辑思路时，外层用一个大的bool query来进行承载。（当然，项目中是使用其Java API）

bool query可以组合任意多个简单查询，各个简单查询之间的逻辑表示如下：

| 属性     | 说明                                                         |
| -------- | ------------------------------------------------------------ |
| must     | 文档必须匹配must选项下的查询条件，相当于逻辑运算的AND        |
| should   | 文档可以匹配should选项下的查询条件，也可以不匹配，相当于逻辑运算的OR |
| must_not | 与must相反，匹配该选项下的查询条件的文档不会被返回           |
| filter   | 和must一样，匹配filter选项下的查询条件的文档才会被返回，但是filter不评分，只起到过滤功能 |

一个例子如下：

```json
{
  "query": {
    "bool": {
      "must": {
        "match": {
          "content": "里皮"
        }
      },
      "must_not": {
        "match": {
          "content": "中超"
        }
      }
    }
  }
}
```

> 需要注意的是，同一个bool下，只能有一个must、must_not、should和filter。

如果希望有多个must时，比如希望同时匹配"里皮"和"中超"，但是又故意分开这两个关键词（因为事实上，一个must，然后使用match，并且operator为and就可以达到目的），怎么操作？注意must下使用数组，然后里面多个match对象就可以了：

```json
{
  "size": 1,
  "query": {
    "bool": {
      "must": [
        {
          "match": {
            "content": "里皮"
          }
        },
        {
          "match": {
            "content": "恒大"
          }
        }
      ]
    }
  },
  "sort": [
    {
      "id": {
        "order": "desc"
      }
    }
  ]
}
```

> 当然must下的数组也可以是多个bool查询条件，以进行更加复杂的查询。

上面的查询等价于：

```json
{
  "query": {
    "bool": {
      "must": {
        "match": {
          "content": {
            "query": "里皮恒大",
            "operator": "and"
          }
        }
      }
    }
  },
  "sort": [
    {
      "id": {
        "order": "desc"
      }
    }
  ]
}
```

### 1.5 嵌套查询

先添加下面一个索引：

```json
PUT /my_index
{
  "mappings": {
    "my_type": {
      "properties": {
        "user":{
          "type": "nested",
          "properties": {
            "first":{"type":"keyword"},
            "last":{"type":"keyword"}
          }
        },
        "group":{
          "type": "keyword"
        }
      }
    }
  }
}
```

添加数据：

```json
PUT my_index/my_type/1
{
  "group":"GuangZhou",
  "user":[
    {
      "first":"John",
      "last":"Smith"
    },
    {
      "first":"Alice",
      "last":"White"
    }
  ]
}

PUT my_index/my_type/2
{
  "group":"QingYuan",
  "user":[
    {
      "first":"Li",
      "last":"Wang"
    },
    {
      "first":"Yonghao",
      "last":"Ye"
    }
  ]
}
```

查询：

较简单的查询：

```json
{
  "query": {
    "nested": {
      "path": "user",
      "query": {
        "term": {
          "user.first": "John"
        }
      }
    }
  }
}
```

较复杂的查询：

```json
{
  "query": {
    "bool": {
      "must": [
        {"nested": {
          "path": "user",
          "query": {
            "term": {
              "user.first": {
                "value": "Li"
              }
            }
          }
        }},
        {
          "nested": {
            "path": "user",
            "query": {
              "term": {
                "user.last": {
                  "value": "Wang"
                }
              }
            }
          }
        }
      ]
    }
  }
}
```

### 1.6 补充：数组查询与测试

添加一个索引：

```json
PUT my_index2
{
  "mappings": {
    "my_type2":{
      "properties": {
        "message":{
          "type": "text"
        },
        "keywords":{
          "type": "keyword"
        }
      }
    }
  }
}
```

添加数据：

```json
PUT /my_index2/my_type/1
{
  "message":"keywords test1",
  "keywords":["美女","动漫","电影"]
}

PUT /my_index2/my_type/2
{
  "message":"keywords test2",
  "keywords":["电影","美妆","广告"]
}
```

搜索：

```json
{
  "query": {
    "term": {
      "keywords": "广告"
    }
  }
}
```

> Note1：注意设置字段类型时，keywords设置为keyword，所以使用term查询可以精确匹配，但设置为text，则不一定——如果有添加分词器，则可以搜索到；如果没有，而是使用默认的分词器，只是将其分为一个一个的字，就不会被搜索到。这点尤其需要注意到。
>
> Note2：对于数组字段，也是可以做桶聚合的，做桶聚合的时候，其每一个值都会作为一个值去进行分组，而不是整个数组进行分组，可以使用上面的进行测试，不过需要注意的是，其字段类型不能为text，否则聚合会失败。
>
> Note3：所以根据上面的提示，一般纯数组比较适合存放标签类的数据，就像上面的案例一样，同时字段类型设置为keyword，而不是text，搜索时进行精确匹配就好了。

### 1.7 滚动查询scroll

如果一次性要查出来比如10万条数据，那么性能会很差，此时一般会采取用scoll滚动查询，一批一批的查，直到所有数据都查询完处理完（es返回的scrollId，可以理解为是es进行此次查询的操作句柄标识，每发送一次该scrollId，es都会操作一次，或者说循环一次，直到时间窗口到期）。

使用scoll滚动搜索，可以先搜索一批数据，然后下次再搜索一批数据，以此类推，直到搜索出全部的数据来，scoll搜索会在第一次搜索的时候，保存一个当时的视图快照，之后只会基于该旧的视图快照提供数据搜索，如果这个期间数据变更，是不会让用户看到的，每次发送scroll请求，我们还需要指定一个scoll参数，指定一个时间窗口，每次搜索请求只要在这个时间窗口内能完成就可以了（也就是说，该scrollId只在这个时间窗口内有效，视图快照也是）。

```
GET spnews/news/_search?scroll=1m
{
  "query": {
    "match_all": {}
  },
  "size": 10,
  "_source": ["id"]
}

GET _search/scroll
{
  "scroll":"1m",
  "scroll_id":"DnF1ZXJ5VGhlbkZldGNoAwAAAAAAADShFmpBMjJJY2F2U242RFU5UlAzUzA4MWcAAAAAAAA0oBZqQTIySWNhdlNuNkRVOVJQM1MwODFnAAAAAAAANJ8WakEyMkljYXZTbjZEVTlSUDNTMDgxZw=="
}
```

## 2、聚合

### 2.1 指标聚合

相当于MySQL的聚合函数。

#### max

```json
{
  "size": 0,
  "aggs": {
    "max_id": {
      "max": {
        "field": "id"
      }
    }
  }
}
```

> size不设置为0，除了返回聚合结果外，还会返回其它所有的数据。

#### min

```json
{
  "size": 0,
  "aggs": {
    "min_id": {
      "min": {
        "field": "id"
      }
    }
  }
}
```

#### avg

```json
{
  "size": 0,
  "aggs": {
    "avg_id": {
      "avg": {
        "field": "id"
      }
    }
  }
}
```

#### sum

```json
{
  "size": 0,
  "aggs": {
    "sum_id": {
      "sum": {
        "field": "id"
      }
    }
  }
}
```

#### stats

```json
{
  "size": 0,
  "aggs": {
    "stats_id": {
      "stats": {
        "field": "id"
      }
    }
  }
}
```

### 2.2 桶聚合

相当于MySQL的group by操作，所以不要尝试对es中text的字段进行桶聚合，否则会失败。

#### Terms

相当于分组查询，根据字段做聚合。

```json
{
  "size": 0,
  "aggs": {
    "per_count": {
      "terms": {
        "size":100,
        "field": "vtype",
        "min_doc_count":1
      }
    }
  }
}
```

在桶聚合的过程中还可以进行指标聚合，相当于mysql做group by之后，再做各种max、min、avg、sum、stats之类的：

```json
{
  "size": 0,
  "aggs": {
    "per_count": {
      "terms": {
        "field": "vtype"
      },
      "aggs": {
        "stats_follower": {
          "stats": {
            "field": "realFollowerCount"
          }
        }
      }
    }
  }
}
```

#### Filter

相当于是MySQL根据where条件过滤出结果，然后再做各种max、min、avg、sum、stats操作。

```json
{
  "size": 0,
  "aggs": {
    "gender_1_follower": {
      "filter": {
        "term": {
          "gender": 1
        }
      },
      "aggs": {
        "stats_follower": {
          "stats": {
            "field": "realFollowerCount"
          }
        }
      }
    }
  }
}
```

> 上面的聚合操作相当于是：查询gender为1的各个指标。

#### Filters

在Filter的基础上，可以查询多个字段各自独立的各个指标，即对每个查询结果分别做指标聚合。

```json
{
  "size": 0,
  "aggs": {
    "gender_1_2_follower": {
      "filters": {
        "filters": [
          {
            "term": {
              "gender": 1
            }
          },
          {
            "term": {
              "gender": 2
            }
          }
        ]
      },
      "aggs": {
        "stats_follower": {
          "stats": {
            "field": "realFollowerCount"
          }
        }
      }
    }
  }
}
```

#### Range

```json
{
  "size": 0,
  "aggs": {
    "follower_ranges": {
      "range": {
        "field": "realFollowerCount",
        "ranges": [
          {
            "to": 500
          },
          {
            "from": 500,
            "to": 1000
          },
          {
            "from": 1000,
            "to": 1500
          },
          {
            "from": "1500",
            "to": 2000
          },
          {
            "from": 2000
          }
        ]
      }
    }
  }
}
```

> to：小于，from：大于等于

#### Date Range

跟上面一个类似的，其实只是字段为日期类型的，然后范围值也是日期。

#### Date Histogram Aggregation

这个功能十分有用，可以根据年月日来对数据进行分类。
索引下面的文档：

```sql
DELETE my_blog

PUT my_blog
{
  "mappings": {
    "article":{
      "properties": {
        "title":{"type": "text"},
        "postdate":{
          "type": "date"
          , "format": "yyyy-MM-dd HH:mm:ss"
        }
      }
    }
  }
}

PUT my_blog/article/1
{
  "title":"Elasticsearch in Action",
  "postdate":"2014-09-23 23:34:12"
}

PUT my_blog/article/2
{
  "title":"Spark in Action",
  "postdate":"2015-09-13 14:12:22"
}

PUT my_blog/article/3
{
  "title":"Hadoop in Action",
  "postdate":"2016-08-23 23:12:22"
}
```

按年对数据进行聚合：

```
GET my_blog/article/_search
{
  "size": 0, 
  "aggs": {
    "agg_year": {
      "date_histogram": {
        "field": "postdate",
        "interval": "year",
        "order": {
          "_key": "asc"
        }
      }
    }
  }
}

{
  "took": 18,
  "timed_out": false,
  "_shards": {
    "total": 5,
    "successful": 5,
    "failed": 0
  },
  "hits": {
    "total": 3,
    "max_score": 0,
    "hits": []
  },
  "aggregations": {
    "agg_year": {
      "buckets": [
        {
          "key_as_string": "2014-01-01 00:00:00",
          "key": 1388534400000,
          "doc_count": 1
        },
        {
          "key_as_string": "2015-01-01 00:00:00",
          "key": 1420070400000,
          "doc_count": 1
        },
        {
          "key_as_string": "2016-01-01 00:00:00",
          "key": 1451606400000,
          "doc_count": 1
        }
      ]
    }
  }
}
```

按月对数据进行聚合：

```
GET my_blog/article/_search
{
  "size": 0, 
  "aggs": {
    "agg_year": {
      "date_histogram": {
        "field": "postdate",
        "interval": "month",
        "order": {
          "_key": "asc"
        }
      }
    }
  }
}
```

> 这样聚合的话，包含的年份的每一个月的数据都会被分类，不管其是否包含文档。

按日对数据进行聚合：

```
GET my_blog/article/_search
{
  "size": 0, 
  "aggs": {
    "agg_year": {
      "date_histogram": {
        "field": "postdate",
        "interval": "day",
        "order": {
          "_key": "asc"
        }
      }
    }
  }
} 复制
```

> 这样聚合的话，包含的年份的每一个月的每一天的数据都会被分类，不管其是否包含文档。
