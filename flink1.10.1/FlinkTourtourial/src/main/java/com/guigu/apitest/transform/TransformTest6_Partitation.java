package com.guigu.apitest.transform;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class TransformTest6_Partitation {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(4);

        //从文件中读取数据
        DataStream<String> inputStream = env.readTextFile("F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\sensor.txt");

        //1.shuffle随机打乱
        DataStream<String> shuffleDataStream = inputStream.shuffle();

        //keyBy
        SingleOutputStreamOperator<SensorReading> keyStream = inputStream.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String s) throws Exception {
                String[] values = s.split(",");
                return new SensorReading(values[0], new Long(values[1]), new Double(values[2]));
            }
        });


        //shuffleDataStream.print("shuffle");
        //keyStream.keyBy("id").print("keybay");
        keyStream.global().print("global");
        env.execute();
    }
}
