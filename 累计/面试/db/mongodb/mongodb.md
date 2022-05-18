## mongodb
#### 在linux和docker环境上安装
- 下载MongoDB的docker镜像
    ```sh
        docker pull mongo:4.2.5
    ```
- 使用Docker命令启动那个Mongo服务
    ```sh
        docker run -p 27017:27017 --name mongo \
        -v /mydata/mongo/db:/data/db \
        -d mongo:4.2.5
    ```
- 需要为MongoDB设置账号，可以使用如下命令启动
    ```sh
        docker run -p 27017:27017 --name mongo \
        -v /mydata/mongo/db:/data/db \
        -d mongo:4.2.5 --auth
    ```
- 进入MongoDB客户端
    ```sh
        docker exec -it mongo mongo
    ```
- 之后在admin集合中创建一个账号用于连接，这里创建的是基于root角色的超级管理账号；
    ```sh
        use admin
        db.createUser({ 
            user: 'mongoadmin', 
            pwd: 'secret', 
            roles: [ { role: "root", db: "admin" } ] });
    ```
- 创建完成后验证是否可以登录
    ```sh
        db.auth("mongoadmin","secret")
    ```
#### 相关概念
![](./img_mongodb/基本概念.png)
#### 数据库操作
- 创建数据库，使用use命令区创建数据库，当插入第一条数据时会创建数据库，例如创建一个test数据库；
    ```sql
    > use test
    switched to db test
    > db.article.insert({name:"MongoDB 教程"})
    WriteResult({ "nInserted" : 1 })
    > show dbs
    admin   0.000GB
    config  0.000GB
    local   0.000GB
    test    0.000GB
    ```
- 删除数据库，使用db对象中的dropDatabase()方法删除；
    ```sh
        > db.dropDatabase()
        { "dropped" : "test", "ok" : 1 }
        > show dbs
        admin   0.000GB
        config  0.000GB
        local   0.000GB
    ```

#### 集合操作
- 创建集合，使用db对象中的createCollection()方法来创建集合，例如创建一个article集合；
    ```sh
        > use test
        switched to db test
        > db.createCollection("article")
        { "ok" : 1 }
        > show collections
        article
    ```
- 删除集合，使用collection对象的drop()方法来删除集合，例如删除一个article集合；
    ```sh
        > db.article.drop()
        true
        > show collections
    ```


#### 文档操作
插入文档  
- MongoDB通过collection对象的insert()方法向集合插入文档，
    ```sh
    db.collection.insert(document)
    ```
- 使用collection对象的insert()方法插入文档，例如插入一个article文档；  
    ```sh
    db.article.insert({title:;MongoDB 教程，
        description: 'MongoDB 是一个 Nosql 数据库',
        by: 'Andy',
        url: 'https://www.mongodb.com/',
        tags: ['mongodb', 'database', 'NoSQL'],
        likes: 100
    })
    ```
- 使用collection对象的find()方法获取文档，例如获取所有article文档；
    ```sh
    db.article.find({})
    ```
更新文档    
- MongoDB通过collection对象的update()来更新集合中的文档，
    ```sh
    db.collection.update(
    <query>,
    <update>,
    {
        multi: <boolean>
    }
    )
    # query：修改的查询条件，类似于SQL中的WHERE部分
    # update：更新属性的操作符，类似与SQL中的SET部分
    # multi：设置为true时会更新所有符合条件的文档，默认为false只更新找到的第一条
    ```
- 将title为MongoDB教程的所有文档的title修改为MongoDB;
    ```sh
    db.article.update({'title':'MongoDB 教程'},{$set:{'title':'MongoDB'}},{multi:true})
    ```
- 除了update()方法外，save()方法可以用来替换已有文档
    ```sql
    db.collection.save(document)
    ```
- 这次我们将ObjectId为5e9943661379a112845e4056的文档的title改为MongoDB 教程；
    ```sql
    db.article.save({
        "_id" : ObjectId("5e9943661379a112845e4056"),
        "title" : "MongoDB 教程",
        "description" : "MongoDB 是一个 Nosql 数据库",
        "by" : "Andy",
        "url" : "https://www.mongodb.com/",
        "tags" : [ 
            "mongodb", 
            "database", 
            "NoSQL"
        ],
        "likes" : 100.0
    })
    ```
删除文档  
- MongoDB通过collection对象的remove()方法来删除集合中的文档
    ```sql
    db.collection.remove(
    <query>,
    {
        justOne: <boolean>
    }
    )
    # query：删除的查询条件，类似于SQL中的WHERE部分
    # justOne：设置为true只删除一条记录，默认为false删除所有记录
    ```
- 删除title为MongDB 教程的所有文档
    ```sql
    db.article.remove({'title':'MongDB 教程'})
    ```
查询文档   
- MongoDB通过collection对象的find()方法来查询文档，语法如下
    ```sql
    db.collection.find(query,projection)
    # query:查询条件，类似于SQL中的WHERE部分
    # projection:可选，使用投影操作符指定返回的键
    ```
- 查询article集合中的所有文档
    ```sql
    db.article.find()
    ```
- MongoDB中的条件操作符，通过与SQL语句的对比
    ![](./img_mongodb/条件操作符.png)
- 条件查询，查询title为MongoDB教程的所有文档；
    ```sql
    db.article.find({'title':'MongoDB 教程'})
    ```
- 条件查询，查询likes大于50的所有文档
    ```sql
    db.article.find({'likes':{$gt:50}})
    ```
- AND条件可以通过在find()方法传入多个键，以逗号隔开来实现，例如查询title为MOngoDB 教程并且by为Andy的所有文档；
    ```sql
    db.article.find({$or:[{"title":"Redis 教程"},{"title":"MongoDB 教程"}]})
    ```
- AND 和 OR条件的联合使用，例如查询likes大于50，并且title为Redis 教程或者"MongoDB 教程的所有文档。
    ```sql
    db.article.find({"likes": {$gt:50}, $or: [{"title": "Redis 教程"},{"title": "MongoDB 教程"}]})
    ```
#### 其他操作
Limit与skip操作   
- 读取指定数量的文档，可以使用limit()方法
    ```sql
    db.collection.find().limit(NUMBER)
    ```
- 只查询article集合中的2条数据
    ```sql
    db.article.find().limit(2)
    ```
- 跳过指定数量的文档来读取,可以使用skip()方法
    ```sql
    db.collection.find().limit(NUMBER).skip(NUMBER)
    ```
- 从第二条开始，查询article集合中的2条数据
    ```sql
    db.article.find().limit(2).skip(1)
    ```
排序  
- 在MongoDB中使用sort()方法对数据进行排序，sort()方法通过参数来指定排序的字段，并使用1和-1来指定排序方式，1为升序，-1为降序；  
    ```sql
    db.collection.find().sort({KEY:1})
    ```
- 按article集合中文档的likes字段降序排列；
    ```sql
    db.article.find().sort({likes:-1})
    ```
索引   
- 索引通过能够极大的提高查询的效率，如果没有索引，MongDB在读取数据必须扫描集合中的每个文件并选取那些符合查询条件的记录。
- MongoDB使用createIndex()方法来创建索引，语法如下：
    ```sql
    db.collection.createIndex(keys,options)
    # background：建索引过程会阻塞其它数据库操作，设置为true表示后台创建，默认为false
    # unique：设置为true表示创建唯一索引
    # name：指定索引名称，如果没有指定会自动生成
    ```
- 给title和description字段创建索引，1表示升序索引，-1表示降序索引，指定以后台方式创建
    ```sql
    db.article.createIndx({"title":1,"description":-1},{background:true})
    ```
- 查看article集合中已经创建的索引
    ```sql
    db.article.getIndexes()
    ```
聚合   
- MongoDB中的聚合使用aggregate()方法，类似于SQL中的group by语句，语法如下：
    ```sql
    db.collection.aggregate(AGGREGATE_OPERATION)
    ```
- 聚合中常用的操作符如下：
    ![](./img_mongodb/聚合操作符.png)
- 根据by字符聚合文档并计算文档数量，类似与SQL中的count()函数
    ```sql
    db.article.aggregate([{$group:{_id:"$by",sum_count:{$sum:1}}}])
    ```
- 根据by字段聚合文档并计算likes字段的平局值，类似与SQL中的avg()语句
    ```sql
    db.article.aggregate([{$group : {_id : "$by", avg_likes : {$avg : "$likes"}}}])
    ```