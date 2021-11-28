package com.itguigu.index;/**
 * Project: Project0408
 * Package: com.itguigu.idex
 * Version: 1.0
 * Created by ljy on 2021-11-10 21:23
 */

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @ClassName Index_Delete
 * @Author: ljy on 2021-11-10 21:23
 * @Version: 1.0
 * @Description: 索引删除
 */
public class Index_Delete {
    public static void main(String[] args) throws Exception{

        //1.创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        //删除索引
        DeleteIndexRequest request = new DeleteIndexRequest("user");
        AcknowledgedResponse response = esClient.indices().delete(request, RequestOptions.DEFAULT);

        System.out.println("操作结果："+response.isAcknowledged());

        //关闭客户端你
        esClient.close();
    }
}
