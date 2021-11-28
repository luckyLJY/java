package com.guigu.apitest.transform;

import com.guigu.apitest.source.beans.Sensor;
import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class TransformTest3_Reduce {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //从文件中读取数据
        DataStream<String> inputStream = env.readTextFile("F:\\新建文件夹\\java\\flink1.10.1\\FlinkTourtourial\\src\\main\\resources\\sensor.txt");

        //转换成SensorReading类型
        /*DataStream<SensorReading> dataStream = inputStream.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String value) throws Exception {
                String[] fields = value.split(",");
                return new SensorReading(fields[0],new Long(fields[1]),new Double(fields[2]));
            }
        });*/
        DataStream<SensorReading> dataStream = inputStream.map(line -> {
            String[] fields = line.split(",");
            return new SensorReading(fields[0],new Long(fields[1]),new Double(fields[2]));
        });

        //分组
        KeyedStream<SensorReading, Tuple> keyedStream = dataStream.keyBy("id");
        //KeyedStream<SensorReading, String> keyedStream1 = dataStream.keyBy(SensorReading::getId);

        //滚动聚合，取当前最大的温度值，以及当前最新的时间戳
        keyedStream.reduce(new ReduceFunction<SensorReading>() {

            @Override
            public SensorReading reduce(SensorReading sensorReading, SensorReading t1) throws Exception {
                return new SensorReading(sensorReading.getId(),t1.getTemperature(),Math.max(sensorReading.getTemperature(),t1.getTemperature()));
            }

        });
        DataStream<SensorReading> resultStream = keyedStream.reduce((curState, newDate) -> {
            return new SensorReading(curState.getId(), newDate.getTemperature(), Math.max(curState.getTemperature(), newDate.getTemperature()));
        });



        resultStream.print();

        env.execute();
    }
}
