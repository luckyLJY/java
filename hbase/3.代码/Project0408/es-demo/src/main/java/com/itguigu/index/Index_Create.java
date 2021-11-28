package com.itguigu.index;/**
 * Project: Project0408
 * Package: com.itguigu.idex
 * Version: 1.0
 * Created by ljy on 2021-11-10 20:31
 */


import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

/**
 * @ClassName Index_Create
 * @Author: ljy on 2021-11-10 20:31
 * @Version: 1.0
 * @Description: 创建索引
 */
public class Index_Create {

    public static void main(String[] args) throws  Exception{

        //1.创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        //2.创建索引
        CreateIndexRequest request = new CreateIndexRequest("user");
        CreateIndexResponse createIndexResponse =
                esClient.indices().create(request, RequestOptions.DEFAULT);

        // 3.响应状态
        boolean acknowledged = createIndexResponse.isAcknowledged();
        System.out.println("索引操作 ：" + acknowledged);

        //end:关闭ES客户端
        esClient.close();

    }

}
