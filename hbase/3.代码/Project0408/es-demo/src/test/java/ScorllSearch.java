/**
 * Project: Project0408
 * Package: PACKAGE_NAME
 * Version: 1.0
 * Created by ljy on 2021-11-18 12:28
 */

import org.apache.http.HttpHost;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import java.io.IOException;

/**
 * @ClassName ScorllSearch
 * @Author: ljy on 2021-11-18 12:28
 * @Version: 1.0
 * @Description: es 滚动查询
 */
public class ScorllSearch {
    @Test
    public void scrollQueryTest() throws IOException {
        //        1. 创建查询对象
        String index = "sms-logs-index";
        String type = "sms-logs-type";
        SearchRequest searchRequest = new SearchRequest(index);//指定索引
        searchRequest.types(type);//指定类型
        searchRequest.scroll(TimeValue.timeValueMinutes(1l));//指定存在内存的时长为1分钟
//    2. 封装查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.sort("fee", SortOrder.DESC);
        searchSourceBuilder.size(2);
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);


        //        3.执行查询
        // client执行
        HttpHost httpHost = new HttpHost("192.168.43.30", 9200);
        RestClientBuilder restClientBuilder = RestClient.builder(httpHost);
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        String scrollId = searchResponse.getScrollId();
        System.out.println(scrollId);//获取scorllId


//        4.获取数据
        SearchHit[] hits = searchResponse.getHits().getHits();
        for(SearchHit searchHit : hits){
            System.out.println(searchHit);
        }

        //获取全部的下一页, 不过我不知道这种有什么用?????
        while (true){
            //创建SearchScrollRequest对象
            SearchScrollRequest searchScrollRequest = new SearchScrollRequest(scrollId);
            searchScrollRequest.scroll(TimeValue.timeValueMinutes(1l));//设置1分钟
            SearchResponse scroll = restHighLevelClient.scroll(searchScrollRequest, RequestOptions.DEFAULT);
            SearchHit[] hits1 = scroll.getHits().getHits();
            if(hits1 != null && hits1.length > 0){
                System.out.println("------------下一页--------------");
                for(SearchHit searchHit : hits1){
                    System.out.println(searchHit);
                }

            }else {
                System.out.println("------------结束--------------");
                break;
            }
        }

        //删除ScrollId
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = restHighLevelClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        System.out.println("删除scroll"  + clearScrollResponse);
    }

    /*

     // 滚动查询

    @Test
    public void scroll() {
        SearchResponse response = client.prepareSearch("twitter").setTypes("tweet")
                .addSort(SortBuilders.fieldSort("_doc"))
                .setSize(10).setScroll(new TimeValue(2000)).execute()
                .actionGet();
        //获取总数量
        long totalCount = response.getHits().getTotalHits();
        int page = (int) totalCount / 10;//计算总页数,每次搜索数量为分片数*设置的size大小
        System.out.println("totalCount:" + totalCount);
        scrollOutput(response);
        for (int i = 0; i < page; i++) {
            //再次发送请求,并使用上次搜索结果的ScrollId
            response = client.prepareSearchScroll(response.getScrollId())
                    .setScroll(new TimeValue(20000)).execute()
                    .actionGet();
            scrollOutput(response);
        }
    }

    public static void scrollOutput(SearchResponse response){
        SearchHits hits = response.getHits();
        System.out.println("-----------begin------------");
        for (int j=0;j<hits.getHits().length;j++) {
            try {
                String id = hits.getHits()[j].getId();
                System.out.println("第" + k + "条数据:" + id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            k++;
        }
        System.out.println("-----------end------------");
    }

     */
}
