package com.guigu.apitest.transform;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.*;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;

import java.util.Collections;

public class TransformTest3_MtipleStream {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //从文件中读取数据
        DataStream<String> inputStream = env.readTextFile("F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\sensor.txt");

        //转换成SensorReading类型
        DataStream<SensorReading> dataStream = inputStream.map(line -> {
            String[] fields = line.split(",");
            return new SensorReading(fields[0],new Long(fields[1]),new Double(fields[2]));
        });


        //1.分流，大于30度为界，分成两个流
        SplitStream<SensorReading> splitStream = dataStream.split(new OutputSelector<SensorReading>() {
            @Override
            public Iterable<String> select(SensorReading sensorReading) {
                return (sensorReading.getTemperature() > 30 ? Collections.singleton("high") : Collections.singleton("low"));
            }
        });

        DataStream<SensorReading> hightTempStream = splitStream.select("high");
        DataStream<SensorReading> lowTempStream = splitStream.select("low");
        DataStream<SensorReading> allStream = splitStream.select("high", "low");

        hightTempStream.print("high");
        lowTempStream.print("low");
        allStream.print("all");

        //2.connected将高温流转换为二元组，与低温流连接合并
        SingleOutputStreamOperator<Tuple2<String, Double>> waringStream = hightTempStream.map(new MapFunction<SensorReading, Tuple2<String, Double>>() {

            @Override
            public Tuple2<String, Double> map(SensorReading sensorReading) throws Exception {
                return new Tuple2<>(sensorReading.getId(), sensorReading.getTemperature());
            }
        });

        ConnectedStreams<Tuple2<String, Double>, SensorReading> connectedStreams = waringStream.connect(lowTempStream);

        SingleOutputStreamOperator<Object> resultStream = connectedStreams.map(new CoMapFunction<Tuple2<String, Double>, SensorReading, Object>() {
            @Override
            public Object map1(Tuple2<String, Double> stringDoubleTuple2) throws Exception {
                return new Tuple3<>(stringDoubleTuple2.f0, stringDoubleTuple2.f1, "height temp warning");
            }

            @Override
            public Object map2(SensorReading sensorReading) throws Exception {
                return new Tuple2<>(sensorReading.getId(), "normal");
            }
        });

        //3.union多条数据
        resultStream.print();
        hightTempStream.union(lowTempStream,allStream);
        env.execute();
    }
}
