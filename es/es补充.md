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
select value1,value2 from index	where datetime >=now
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

