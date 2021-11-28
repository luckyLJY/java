package com.itguigu.doc;/**
 * Project: Project0408
 * Package: com.itguigu.doc
 * Version: 1.0
 * Created by ljy on 2021-11-12 14:38
 */

import org.apache.http.HttpHost;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * @ClassName Doc_Update
 * @Author: ljy on 2021-11-12 14:38
 * @Version: 1.0
 * @Description: 更新文档
 */
public class Doc_Update {
    public static void main(String[] args) throws Exception {

        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 修改数据
        UpdateRequest request = new UpdateRequest();
        request.index("user").id("1001");
        request.doc(XContentType.JSON, "sex", "女");

        UpdateResponse response = esClient.update(request, RequestOptions.DEFAULT);

        System.out.println(response.getResult());

        esClient.close();
    }
}
