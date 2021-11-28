package com.guigu.apitest.transform;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.operators.translation.KeyExtractingMapper;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class TransformTest1_Base {
    public static void main(String[] args)throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);


        //从文件读取数据
        DataStream<String> inputStream = env.readTextFile("F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\sensor.txt");

        //1.map,把String转换为长度输出
        DataStream<Integer> mapSteam = inputStream.map(new MapFunction<String, Integer>() {
            @Override
            public Integer map(String value) throws Exception {

                return value.length();
            }
        });
        //2.flatmap,按逗号切分字段
        DataStream<String> flatMapStream = inputStream.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String value, Collector<String> collector) throws Exception {
                String[] fields = value.split(",");
                for (String field : fields){
                    collector.collect(field );
                }
            }
        });

        //Fliter，帅选sensor_1的数据
        DataStream<String> filterStream = inputStream.filter((FilterFunction<String>) s -> s.startsWith("sensor_1"));

        //打印输出
        mapSteam.print("map");
        flatMapStream.print("flatMap");
        filterStream.print("filter");

        env.execute();
    }
}
