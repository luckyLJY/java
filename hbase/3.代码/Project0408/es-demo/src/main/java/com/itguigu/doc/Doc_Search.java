package com.itguigu.doc;/**
 * Project: Project0408
 * Package: com.itguigu.doc
 * Version: 1.0
 * Created by ljy on 2021-11-12 10:53
 */

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.Iterator;

/**
 * @ClassName Doc_Search
 * @Author: ljy on 2021-11-12 10:53
 * @Version: 1.0
 * @Description: 查询一个Index下的所有内容
 */
public class Doc_Search {
    public static void main(String[] args) throws Exception{

        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );

        //查询索引的所有数据
        SearchRequest request = new SearchRequest();
        request.indices("user");

        //构造查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());

        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println(response.getTook());
        System.out.println(hits.getTotalHits());

        Iterator<SearchHit> iterator = hits.iterator();
        while(iterator.hasNext()){
            SearchHit hit = iterator.next();
            System.out.println(hit.getSourceAsString());
        }

        esClient.close();

    }
}
