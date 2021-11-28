package com.guigu.apitest.window;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.OutputTag;

import javax.annotation.Nullable;

/**
 * @author ljy
 * @date 2021-9-30 15:35
 * @description 事件时间语义
 */
public class WindowTest3_EventTimeWindow {
    public static void main(String[] args) throws Exception{

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(4);

        //设置事件时间语义,默认200毫秒
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.getConfig().setAutoWatermarkInterval(100);

        //socket流
        DataStreamSource<String> inputStream = env.socketTextStream("192.168.75.140", 7777);

        //转换成Sensording类型
        SingleOutputStreamOperator<SensorReading> dataStream = inputStream.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String s) throws Exception {

                String[] fields = s.split(",");
                return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
            }
        })

        /*assignTimestampsAndWatermarks(WatermarkStrategy.forBoundedOutOfOrderness[SensorReading](Duration.ofSeconds(3))
                .withTimestampAssigner(new SerializableTimestampAssigner[SensorReading] {
            override def extractTimestamp(element: SensorReading, recordTimestamp: Long): Long = element.timeStamp * 1000L
        }))
        */
                //升序数据设置设置事件时间和watermark
                /*.assignTimestampsAndWatermarks(

                //
                new AscendingTimestampExtractor<SensorReading>() {
                    @Override
                    public long extractAscendingTimestamp(SensorReading sensorReading) {
                        return sensorReading.getTimestap()*1000L;
                    }
                })*/
                //乱序数据设置时间戳和watermark
                .assignTimestampsAndWatermarks(
                        //延迟2秒
                        new BoundedOutOfOrdernessTimestampExtractor<SensorReading>(Time.seconds(2)) {
                    @Override
                    public long extractTimestamp(SensorReading sensorReading) {
                        return sensorReading.gettimestamp() * 1000L;
                    }
                });

        /**
         * @// TODO: 2021-10-12
         * 添加侧输出流：
         *  时间：1.延迟(watermark)2s；2.窗口大小15s；3.允许迟到(allowedLateness)1分钟；4.另外捕获(sideOutputLateData)
         */
        OutputTag<SensorReading> outputTag = new OutputTag<SensorReading>("late"){};

        /**
         *
         * @kp 全窗口
         *
         */


        SingleOutputStreamOperator<SensorReading> minStream = dataStream.keyBy("id")
                .timeWindow(Time.seconds(15))
                .allowedLateness(Time.minutes(1))
                .sideOutputLateData(outputTag)
                .minBy("temperature");

        minStream.print("minTemp");
        minStream.getSideOutput(outputTag).print("late");

        env.execute();

    }


}
