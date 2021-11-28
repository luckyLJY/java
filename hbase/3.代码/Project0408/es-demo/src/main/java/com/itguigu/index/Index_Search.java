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
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;


/**
 * @ClassName Index_Search
 * @Author: ljy on 2021-11-10 20:31
 * @Version: 1.0
 * @Description: 查询索引
 */
public class Index_Search {

    public static void main(String[] args) throws  Exception{

        //1.创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        //查询索引
        GetIndexRequest request =  new GetIndexRequest("user");

        GetIndexResponse getIndexResponse = esClient.indices().get(request, RequestOptions.DEFAULT);

        //响应状态
        System.out.println(getIndexResponse.getAliases());
        System.out.println(getIndexResponse.getMappings());
        System.out.println(getIndexResponse.getSettings());

        //end:关闭ES客户端
        esClient.close();

    }

}
