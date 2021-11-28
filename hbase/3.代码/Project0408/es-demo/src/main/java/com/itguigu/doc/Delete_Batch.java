package com.itguigu.doc;/**
 * Project: Project0408
 * Package: com.itguigu.doc
 * Version: 1.0
 * Created by ljy on 2021-11-12 14:41
 */

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @ClassName Delete_Batch
 * @Author: ljy on 2021-11-12 14:41
 * @Version: 1.0
 * @Description:文档批量删除
 */
public class Delete_Batch {
    public static void main(String[] args) throws Exception {

        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 批量删除数据
        BulkRequest request = new BulkRequest();

        request.add(new DeleteRequest().index("user").id("1001"));
        request.add(new DeleteRequest().index("user").id("1002"));
        request.add(new DeleteRequest().index("user").id("1003"));

        BulkResponse response = esClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response.getTook());
        System.out.println(response.getItems());

        esClient.close();
    }
}
