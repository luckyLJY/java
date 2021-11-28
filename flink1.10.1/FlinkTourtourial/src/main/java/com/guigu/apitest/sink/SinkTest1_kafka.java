package com.guigu.apitest.sink;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;

import java.util.Properties;

public class SinkTest1_kafka {
    public static void main(String[] args)throws Exception {


        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //kafka配置项
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","localhost:9092");
        properties.setProperty("group.id","consumer-group");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("auto.offset.rest","latest");

        //从Kafka读取数据
        DataStream<String> inputStream =
                env.addSource(new FlinkKafkaConsumer010<String>("sensor",new org.apache.flink.api.common.serialization.SimpleStringSchema(),properties));

        //转换成SensorReading类型
        DataStream<String > dataStream = inputStream.map(new MapFunction<String, String>() {
            @Override
            public String map(String value) throws Exception {
                String[] fields = value.split(",");
                return new SensorReading(fields[0],new Long(fields[1]),new Double(fields[2])).toString();
            }
        });


        dataStream.addSink(new FlinkKafkaProducer011<String>("localhost:9092","sinktest",new SimpleStringSchema()));

        env.execute();
    }
}
