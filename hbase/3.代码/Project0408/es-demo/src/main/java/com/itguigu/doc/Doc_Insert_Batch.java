package com.itguigu.doc;/**
 * Project: Project0408
 * Package: com.itguigu.doc
 * Version: 1.0
 * Created by ljy on 2021-11-11 16:45
 */

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * @ClassName Doc_Insert_Batch
 * @Author: ljy on 2021-11-11 16:45
 * @Version: 1.0
 * @Description: 批量插入
 */
public class Doc_Insert_Batch {
    public static void main(String[] args) throws Exception{

        RestHighLevelClient esclient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );
        // 批量插入数据
        BulkRequest request = new BulkRequest();

       request.add(new IndexRequest().index("user").id("1001").source(XContentType.JSON, "name", "zhangsan", "age",30,"sex","男"));
       request.add(new IndexRequest().index("user").id("1002").source(XContentType.JSON, "name", "lisi", "age",30,"sex","女"));
       request.add(new IndexRequest().index("user").id("1003").source(XContentType.JSON, "name", "wangwu", "age",40,"sex","男"));
       request.add(new IndexRequest().index("user").id("1004").source(XContentType.JSON, "name", "wangwu1", "age",40,"sex","女"));
       request.add(new IndexRequest().index("user").id("1005").source(XContentType.JSON, "name", "wangwu2", "age",50,"sex","男"));
       request.add(new IndexRequest().index("user").id("1006").source(XContentType.JSON, "name", "wangwu3", "age",50,"sex","男"));
        request.add(new IndexRequest().index("user").id("1007").source(XContentType.JSON, "name", "wangwu44", "age",60,"sex","男"));
        request.add(new IndexRequest().index("user").id("1008").source(XContentType.JSON, "name", "wangwu555", "age",60,"sex","男"));
        request.add(new IndexRequest().index("user").id("1009").source(XContentType.JSON, "name", "wangwu66666", "age",60,"sex","男"));

        BulkResponse response = esclient.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response.getTook());
        System.out.println(response.getItems());

        esclient.close();
    }
}
