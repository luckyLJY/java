package com.guigu.apitest.window;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.OutputTag;
import scala.Tuple3;

public class WindowTest2_CountWindow {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<String> inputStream = env.socketTextStream("192.168.75.100", 7777);

        SingleOutputStreamOperator<SensorReading> dataStream = inputStream.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String s) throws Exception {
                String[] fileds = s.split(",");
                return new SensorReading(fileds[0], new Long(fileds[1]), new Double(fileds[2]));
            }
        });

        //计数窗口：温度求平均值
        SingleOutputStreamOperator<Double> avgResultStream = dataStream.keyBy("id")
                .countWindow(3, 1)
                .aggregate(new MyAvgTemp());
        avgResultStream.print();

       /* //温度求和使用sum，采用侧输出流
        OutputTag<SensorReading> outputTag = new OutputTag<SensorReading>("late");

        SingleOutputStreamOperator<SensorReading> sumStream = dataStream.keyBy("id")
                .timeWindow(Time.seconds(15))
                // 允许1分钟内的迟到数据<=比如数据产生时间在窗口范围内，但是要处理的时候已经超过窗口时间了
                .allowedLateness(Time.minutes(1))
                //侧输出流，超过1分钟，收集于此
                .sideOutputLateData(outputTag)
                //侧输出流对温度求和
                .sum("temperature");
        // 之后可以再用别的程序，把侧输出流的信息和前面窗口的信息聚合。（可以把侧输出流理解为用来批处理来补救处理超时数据）
        sumStream.getSideOutput(outputTag).print();*/

        env.execute();
    }
    public static class MyAvgTemp implements AggregateFunction<SensorReading, Tuple2<Double,Integer>,Double>{

        //创建累加器
        @Override
        public Tuple2<Double, Integer> createAccumulator() {
            return new Tuple2<>(0.0,0);
        }

        //累加器+输入数据->累加器
        @Override
        public Tuple2<Double, Integer> add(SensorReading sensorReading, Tuple2<Double, Integer> accumulator) {
            return new Tuple2<>(accumulator.f0+ sensorReading.gettimestamp(),accumulator.f1+1);
        }

        //返回结果：累加器->输出数据
        @Override
        public Double getResult(Tuple2<Double, Integer> accumulator) {
            return accumulator.f0 / accumulator.f1;
        }

        //累加器累加
        @Override
        public Tuple2<Double, Integer> merge(Tuple2<Double, Integer> a, Tuple2<Double, Integer> b) {
            return new Tuple2<>(a.f0+b.f0, a.f1+b.f1);
        }
    }
}
