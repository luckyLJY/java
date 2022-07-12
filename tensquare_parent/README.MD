## day02-MongoDB和评论管理
1. mongodb的安装及使用
    - mongo.exe为客户端
    - mongod.exe为服务端
    - docker使用  
      启动docker环境：systemctl start docker
      设置为开机自启动：systemctl enable docker  
      创建镜像应用：docker run -id --name=appname -p 端口:端口 imagename
      
    - mongodb常用使用命令
      ```mongo
      查看数据库：show dbs;
      查看集合：show collections;
      插入数据：db.集合.inset();
      查看： db.集合.find();db.集合.findOne();db.集合.find().limit(2);db.集合.find(条件)
      修改:db.集合.update(条件，修改后的数据)
      删除文档:db.集合名称.remove(条件)   
      将所有数据全部删除:db.comment.remove({})
      统计条数：db.comment.count()  
      模糊查询：
         查询评论内容包含"流量"的所有文档,db.comment.find({content:/流量/})
         查询评论内容以"加班"开头的db.comment.find({content:/^加班/})
         以"胜利"结束："胜利$"
      db.集合.find({"filed":{$gt:value}})
         gt:大于，lt:小于，gte:大于等于，lte:小于等于，ne：不等于
      条件连接：
         $and:[{条件},{条件},{条件}]
         db.comment.find({$and:[{thumbup:{$gte:1000},{thumbup:{$lte:2000}}}]})
         $or:[{条件}，{条件}]
      列值增长：$inc
         db.comment.update({_id:"2"},{$inc:{thumbup:1}})
      包含和不包含：$in,$nin
         db.comment.find({userid:{$in:["1013","1014"]}})
      
      ```
      
2. 点赞数递增
   i++在多线程中是不安全的
   
3. reids
   docker run -id --name=tensquare_redis -p 6379:6379 redis
   
## day03
1. websocket常用事件方法
   ```shell
   cur1 -X POST -H 'Content-Type:application/json'-H 'Accept:application/json'-H 'Authorization:Bear
   er YWNEP5n9zvoQEei7Kc1xPqJTkgAAAAAAAAAAAAAAAAAAAAGL4CTw6XgR6LaXXVmNX4QCAgMAAAFnXcBpfQBPGgDC09w5IdrfqG_
   H8_F53VLVTG0_82GXyEF8ZdMCt9-UpQ'-d '{"target_type":"users","target":["user2","user3"],"msg":{"typ
   e":"txt","msg":"testmessage"},"from":"user1"}''http://a1.easemob.com/easemob-demo/testapp/message
   ```
2. 环信云测试
   {
   "grant_type": "client_credentials",
   "client_id": "YXA6yP51o3wVR7G39fp2Wf1FEw",
   "client_secret": "YXA6QVQHRQ526L49_tkox7SPkXS4cFc"
   }
   
   orgname:1108220709100436
   appname:tensquare
   APPKEY:1108220709100436#tensquare
   curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{ \
   "grant_type": "client_credentials", \
   "client_id": "YXA6yP51o3wVR7G39fp2Wf1FEw", \
   "client_secret": "YXA6QVQHRQ526L49_tkox7SPkXS4cFc" \
   }' 'http://a1.easemob.com/1108220709100436/tensquare/token'
   
1.20