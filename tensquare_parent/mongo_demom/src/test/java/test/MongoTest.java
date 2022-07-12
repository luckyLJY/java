package test;/**
 * Project: tensquare_parent
 * Package: test
 * Version: 1.0
 * Created by ljy on 2022-6-25 15:35
 */

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;

/**
 * @ClassName
 * @Author: ljy on 2022-6-25 15:35
 * @Version: 1.0
 * @Description:
 */
public class MongoTest {

    private MongoCollection<Document> collection;//集合
    private MongoClient mongoClient;//客户端

    @Before
    public  void init(){
        //1. 创建操作mongodb的客户端
         mongoClient = new MongoClient("192.168.75.200",27017);

        //2. 选择数据库 use commentdb
        MongoDatabase commentdb = mongoClient.getDatabase("commentdb");
        //3. 获取集合db.comment
         collection = commentdb.getCollection("comment");

    }

    //查询所有数据:db.comment.find()
    @Test
    public void test1(){

        //4. 使用集合进行查询，查询所有数据db.comment.find()
        FindIterable<Document> documents = collection.find();
        //5. 解析结果集
        for (Document document : documents) {
            System.out.println("----------------------------");
            System.out.println("_id"+document.get("_id"));
            System.out.println("content"+document.get("content"));
            System.out.println("userid"+document.get("userid"));
            System.out.println("thumbup"+document.get("thumbup"));
        }
    }

    @After
    public void after(){
        //释放资源：
        mongoClient.close();
    }

    //根据条件查询
    @Test
    public void test2(){
        //封装查询条件
        BasicDBObject bjson = new BasicDBObject("_id", "1");

        //执行查询
        FindIterable<Document> documents = collection.find(bjson);

        for (Document document : documents) {
            System.out.println("----------------------------");
            System.out.println("_id"+document.get("_id"));
            System.out.println("content"+document.get("content"));
            System.out.println("userid"+document.get("userid"));
            System.out.println("thumbup"+document.get("thumbup"));
        }
    }

    //新增
    @Test
    public void test3(){
        //分组新增数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("_id","6");
        map.put("content","新增测试");
        map.put("userid","1019");
        map.put("thumbup","666");
        //封装新增文档对象
        Document document = new Document(map);
        //执行新增
        collection.insertOne(document);
    }

    //修改
    @Test
    public void test4(){
        //创建修改的条件
        BasicDBObject filter = new BasicDBObject("_id","6");
        //创建修改的值
        BasicDBObject update = new BasicDBObject("$set", new Document("userid", "999"));
        //执行修改
        collection.updateOne(filter,update);
    }

    //删除
    @Test
    public void test5(){
        //创建删除的条件
        BasicDBObject bson = new BasicDBObject("id", "6");

        //执行删除
        collection.deleteOne(bson);
    }
}
