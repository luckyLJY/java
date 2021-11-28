# hbase操作
### base插入数据
```java
//列族
byte[] familyName = Bytes.toBytes("info");
//列限定符
byte[][] qualifiers = { Bytes.toBytes("name"), Bytes.toBytes("gender"),
        Bytes.toBytes("age"), Bytes.toBytes("address") };
// Instantiate an HTable object.
table = conn.getTable(tableName);
List<Put> puts = new ArrayList<Put>();
// Instantiate a Put object
Put put = new Put(Bytes.toBytes("012005000201"));
put.addColumn(familyName, qualifiers[0], Bytes.toBytes("Zhang San"));
put.addColumn(familyName, qualifiers[1], Bytes.toBytes("Male"));
put.addColumn(familyName, qualifiers[2], Bytes.toBytes("19"));
put.addColumn(familyName, qualifiers[3], Bytes.toBytes("Shenzhen, Guangdong"))
puts.put(put);
table.put(puts);
```
### Hbase查询数据
#### GET
Get get = new Get(rowkey);
get.addColumn();
Result result = table.get(get);
```java
    // Specify the column family name.
    byte[] familyName = Bytes.toBytes("info");
    // Specify the column name.
    byte[][] qualifier = { Bytes.toBytes("name"), Bytes.toBytes("address") };
    // Specify RowKey.
    byte[] rowKey = Bytes.toBytes("012005000201");
    // Create the Table instance
    table = conn.getTable(tableName);
    // Instantiate a Get object.
    Get get = new Get(rowKey);
    // Set the column family name and column name.
    get.addColumn(familyName, qualifier[0]);
    get.addColumn(familyName, qualifier[1]);
    // Submit a get request.
    Result result = table.get(get);
    for (Cell cell : result.rawCells()) {
            LOG.info(Bytes.toString(CellUtil.cloneRow(cell)) + ":"
            + Bytes.toString(CellUtil.cloneFamily(cell)) + ","
            + Bytes.toString(CellUtil.cloneQualifier(cell)) + ","
            + Bytes.toString(CellUtil.cloneValue(cell)));
    }
    tablename.close();
```
### es查询数据
selectEs(index, rowkey类型Map<String,Object>),返回List<Map<String,Object>>
```java
SearchRequest searchRequst = new SearchRequest(index);
SearchSourceBuilder searchSourceBilder = new SearchSourceBuilder();
searchSourceBuilder.query(QueryBuilders.termQuery("user","kimchy1"));

//指定排序
searchSourceBuilder.sort(new FieldSortBuilder("_doc").order(SortOrder.ASC));

//来源过滤
String[] includeFields = new String[] {"message","user","innerObject*"};
String[] excludeFields = new String[]{"postDate"};

searchSourceBuilder.fetchSource(includeFields, excludeFields);

//高亮
High1lightBuilder highlighBuilder = new HighlightBuilder();
//创建一个字段高亮区域
HighlightBuilder.Field highlightUser = new HighlightBuilder.Field("user");
//添加字段高亮区域
highlighBuilder.field(highlightUser)
//添加到serarchSourceBuilder中
searchSourceBuilder.highlighter(highlighBuilder)

//返回数据范围
searchSourceBuilder.from(0)
searchSoutceBuilder.size(2)

//超时时间
searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

//将searchSourceBuilder加入到searchRequest
searchRequest.source(searchSourceBuilder);

//使用客户端查询,返回SearchResponse
searchRespone searchRespone =highLevelClient.search(searchRequest,RequestOptions.DEFAULT);

//获取SearchRespone中的值
SearchHits hits = searchRespone.getHits();
Iterator<SearchHit> it =hits.iterator();
Map<String, Object> object = it.next.getSourceAsMap();
```