package com.itguigu.pro;/**
 * Project: Project0408
 * Package: com.itguigu.pro
 * Version: 1.0
 * Created by ljy on 2021-12-6 15:02
 */

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * @ClassName QueryByScorll
 * @Author: ljy on 2021-12-6 15:02
 * @Version: 1.0
 * @Description:使用游标查询
 */
public class QueryByScorll {
    public static void main(String[] args) {
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );

        String scrollId = null;
        try {
            //设置游标等待时间
            Scroll scroll = new Scroll(TimeValue.timeValueSeconds(10l));

            //创建查询请求
            SearchRequest request = new SearchRequest("user");
            //将游标添加到请求上
            request.scroll(scroll);

            /**
             * TODO:设置查询参数
             * 1.创建SearchSourceBuilder
             * 2.使用query方法添加查询参数，参数由QueryBuilders带入
             * 3.使用source方法添加到查询请求上
             */
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            request.source(searchSourceBuilder);

            SearchResponse searchResponse = esClient.search(request, RequestOptions.DEFAULT);
            //从response中获取游标id
            scrollId = searchResponse.getScrollId();
            SearchHit[] searchHits = searchResponse.getHits().getHits();

            while (searchHits!=null && searchHits.length>0){
                /**
                 * 1.创建SearchScorllRequest构造使用scollid
                 * 2.将游标添加到该对象
                 * 3.使用高级客户端的searchScorll添加该对象
                 */
                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                scrollRequest.scroll(scroll);
                searchResponse = esClient.searchScroll(scrollRequest, RequestOptions.DEFAULT);
                scrollId = searchResponse.getScrollId();
                searchHits = searchResponse.getHits().getHits();
                System.out.println(searchResponse.toString());


            }
            /**
             * TODO:关闭游标
             * 1.创建ClearScrollRequest的对象
             * 2.使用该对象添加游标
             * 3.高级客户端关闭游标返回
             */
            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
            clearScrollRequest.addScrollId(scrollId);
            ClearScrollResponse clearScrollResponse = esClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
            if (clearScrollResponse.isSucceeded()){
                System.out.println("ClearRespone is successful");
            }else{
                System.out.println("ClearScroll is filed");
            }

        }catch (Exception e){

        }

    }
}
