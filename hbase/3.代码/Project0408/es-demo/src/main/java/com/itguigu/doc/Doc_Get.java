package com.itguigu.doc;/**
 * Project: Project0408
 * Package: com.itguigu.doc
 * Version: 1.0
 * Created by ljy on 2021-11-11 16:54
 */

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @ClassName Doc_Get
 * @Author: ljy on 2021-11-11 16:54
 * @Version: 1.0
 * @Description: 获取数据Get
 */
public class Doc_Get {
    public static void main(String[] args) throws Exception {

        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 查询数据
        GetRequest request = new GetRequest();
        request.index("user").id("1001");
        GetResponse response = esClient.get(request, RequestOptions.DEFAULT);

        System.out.println(response.getSourceAsString());

        esClient.close();
    }
}
