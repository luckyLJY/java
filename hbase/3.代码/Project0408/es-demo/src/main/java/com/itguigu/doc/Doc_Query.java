package com.itguigu.doc;/**
 * Project: Project0408
 * Package: com.itguigu.doc
 * Version: 1.0
 * Created by ljy on 2021-11-12 11:04
 */

import jdk.management.resource.internal.inst.FileOutputStreamRMHooks;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import javax.management.modelmbean.DescriptorSupport;
import java.util.Iterator;

/**
 * @ClassName Doc_Query
 * @Author: ljy on 2021-11-12 11:04
 * @Version: 1.0
 * @Description: 条件查询
 */
public class Doc_Query {
    public static void main(String[] args) throws  Exception{
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );

        //1. 查询索引中全部数据
        /*SearchRequest request = new SearchRequest();
        request.indices("user");
        request.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        //打印总条数
        System.out.println(hits.getTotalHits());
        //打印查询时间
        System.out.println(response.getTook());
        for (SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }*/

        //2. 条件查询
        /*SearchRequest request = new SearchRequest();
        request.indices("user");
        //查询条件年龄为30岁的
        request.source(new SearchSourceBuilder().query(QueryBuilders.termQuery("age",30)));
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();

        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());
        for (SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }*/

        //3. 分页查询
        /*SearchRequest request = new SearchRequest();
        request.indices("user");
        // 查询条件：第二页，每页两条数据
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        builder.from(2);
        builder.size(2);
        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();

        System.out.println(response.getTook());
        System.out.println(hits.getTotalHits());
        for (SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }*/

        //4. 查询排序
        /*SearchRequest request = new SearchRequest();
        request.indices("user");

        //查询条件:年龄排序倒序
        SearchSourceBuilder builder =new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        builder.sort("age", SortOrder.DESC);
        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println(response.getTook());
        System.out.println(hits.getTotalHits());

        for (SearchHit hit: hits){
            System.out.println(hit.getSourceAsString());
        }*/

        //5. 过滤字段
        /*SearchRequest request =new SearchRequest();
        request.indices("user");

        //查询条件：包含:无，不包含字段：age字段
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());

        String[] excludes={"age"};
        String[] includes={};

        builder.fetchSource(includes,excludes);

        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();

        System.out.println(response.getTook());
        System.out.println(hits.getTotalHits());

        for (SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }*/

        //6. 组合查询
        /*SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder  builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder =QueryBuilders.boolQuery();
        //且關係
       *//* boolQueryBuilder.must(QueryBuilders.matchQuery("age", 30));
        boolQueryBuilder.must(QueryBuilders.matchQuery("sex", "男"));
        //boolQueryBuilder.mustNot(QueryBuilders.matchQuery("sex", "男"));*//*
        //或關係
        boolQueryBuilder.should(QueryBuilders.matchQuery("age", 30));
        boolQueryBuilder.should(QueryBuilders.matchQuery("age", 40));

        builder.query(boolQueryBuilder);
        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();

        System.out.println(response.getTook());
        System.out.println(hits.getTotalHits());

        for (SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }*/

        //7.范围查询
        /*SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age");
        rangeQuery.gte(30);
        rangeQuery.lt(50);

        builder.query(rangeQuery);

        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();

        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());

        for (SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }*/

        //8.模糊查询
        /*SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.fuzzyQuery("name","wangwu").fuzziness(Fuzziness.ONE));

        request.source(builder);
        SearchResponse re = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = re.getHits();

        System.out.println(re.getTook());
        System.out.println(hits.getTotalHits());
        for (SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }*/

        //9.高亮查询
        /*SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "zhangsan");
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("name");

        builder.highlighter(highlightBuilder);
        builder.query(termQueryBuilder);
        request.source(builder);

        SearchResponse re = esClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = re.getHits();
        System.out.println(re.getTook());
        System.out.println(hits.getTotalHits());
        for (SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }*/

        //10.聚合查询
       /* SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        AggregationBuilder aggregationBuilder = AggregationBuilders.max("maxAge").field("age");
        builder.aggregation(aggregationBuilder);

        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();

        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());

        for ( SearchHit hit : hits ) {
            System.out.println(hit.getSourceAsString());
        }*/

        //11. 分组查询
        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("ageGroup").field("age");
        builder.aggregation(aggregationBuilder);

        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();

        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());

        for ( SearchHit hit : hits ) {
            System.out.println(hit.getSourceAsString());
        }
        esClient.close();
        //分组
        /*
        20211227  新增分组查询->排序
         */
       /* SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("post_date","20210203"));

        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("acct_nos").field("acct_no.keyword");
        termsAggregationBuilder.subAggregation(AggregationBuilders.sum("sum_amt_out").field("amt_in"))
                .order(BucketOrder.aggregation("sum_amt_out",false)).size(10);

        builder.aggregation(termsAggregationBuilder);

        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        Terms acct_nos = response.getAggregations().get("acct_nos");

        Iterator<? extends Terms.Bucket> iterator = acct_nos.getBuckets().iterator();
        while (iterator.hasNext()){
            Terms.Bucket next = iterator.next();
            Sum sum_amt_out = (Sum) next.getAggregations().getAsMap().get("sum_amt_out");
            System.out.println(next.getAggregations()+","+sum_amt_out.getValueAsString());
        }
        esClient.close();
*/

    }
}
