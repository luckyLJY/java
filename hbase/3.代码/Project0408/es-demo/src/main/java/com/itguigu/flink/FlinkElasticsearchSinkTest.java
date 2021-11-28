package com.itguigu.flink;/**
 * Project: Project0408
 * Package: com.itguigu.flink
 * Version: 1.0
 * Created by ljy on 2021-11-12 14:43
 */

import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch7.ElasticsearchSink;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName FlinkElasticsearchSinkTest
 * @Author: ljy on 2021-11-12 14:43
 * @Version: 1.0
 * @Description: es与flink结合
 */
public class FlinkElasticsearchSinkTest {
    public static void main(String[] args) throws Exception {
        //构建Flink环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //Source:数据输入
        DataStreamSource<String> source = env.socketTextStream("localhost", 999);

        //使用ESBuilder构建输出
        List<HttpHost> hosts = new ArrayList<>();
        hosts.add(new HttpHost("localhost",9200,"http"));;
        ElasticsearchSink.Builder<String> esBuilder = new ElasticsearchSink.Builder<>(hosts, new ElasticsearchSinkFunction<String>() {
            @Override
            public void process(String s, RuntimeContext runtimeContext, RequestIndexer requestIndexer) {
                Map<String, String> jsonMap = new HashMap<>();
                jsonMap.put("data", s);

                IndexRequest indexRequest = new IndexRequest();
                indexRequest.index("flink-index");
                indexRequest.id("9001");
                indexRequest.source(jsonMap);
                ;

                requestIndexer.add(indexRequest);
            }
        });

        //sink:数据输出
        esBuilder.setBulkFlushMaxActions(1);
        source.addSink(esBuilder.build());;

        //执行
        env.execute();
    }
}
