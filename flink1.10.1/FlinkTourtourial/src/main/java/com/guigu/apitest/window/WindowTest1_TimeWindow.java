package com.guigu.apitest.window;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.commons.collections.IteratorUtils;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.evictors.CountEvictor;
import org.apache.flink.streaming.api.windowing.evictors.Evictor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.runtime.operators.windowing.TimestampedValue;
import org.apache.flink.util.Collector;

public class WindowTest1_TimeWindow {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //从文件读取数据
        //DataStream<String> inputStream = env.readTextFile("F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\sensor.txt");

        //socket文本流
        DataStream<String> inputStream = env.socketTextStream("192.168.75.140", 7777);

        //转换成Sensording类型
        DataStream<SensorReading> dataStream = inputStream.map(new MapFunction<String, SensorReading>() {

            @Override
            public SensorReading map(String s) throws Exception {
                String[] fields = s.split(",");
                return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
            }
        });



        //开窗测试
        /*DataStream<Integer> resultStream = dataStream.keyBy("id")
                //时间窗口
                //.window(TumblingProcessingTimeWindows.of(Time.seconds(15)))

                //.timeWindow(Time.seconds(10))
                //会话窗口
                //.window(EventTimeSessionWindows.withGap(Time.minutes(1)));
                //计数窗口
                //.countWindow(10,2); evictor去除器 trigger触发器
                //增量
                .window(TumblingProcessingTimeWindows.of(Time.seconds(15)))
                .aggregate(new AggregateFunction<SensorReading, Integer, Integer>() {
                    @Override
                    public Integer createAccumulator() {
                        return 0;
                    }

                    @Override
                    public Integer add(SensorReading value, Integer accumulator) {
                        return accumulator + 1;
                    }

                    @Override
                    public Integer getResult(Integer accumulator) {
                        return accumulator;
                    }

                    @Override
                    public Integer merge(Integer a, Integer b) {
                        return a + b;
                    }
                });*/

        //全量聚合
        //全窗口（WindowFunction和ProcessWindowFunction）
        DataStream<Tuple3<String, Long, Integer>> resultStream2 = dataStream.keyBy(SensorReading::getId)
                .timeWindow(Time.seconds(15))
                //.window(TumblingProcessingTimeWindows.of(Time.seconds(15)));
                /*.process(new ProcessWindowFunction<SensorReading, Object, Tuple, TimeWindow>() {
/               })*/
                .apply(new WindowFunction<SensorReading, Tuple3<String, Long, Integer>, String, TimeWindow>() {
                    @Override
                    public void apply(String s, TimeWindow window, Iterable<SensorReading> input, Collector<Tuple3<String, Long, Integer>> out) throws Exception {
                        String id = s;
                        long windowEnd = window.getEnd();
                        int count = IteratorUtils.toList(input.iterator()).size();
                        out.collect(new Tuple3<>(id, windowEnd, count));
                    }
                });


        // resultStream.print("result");
        resultStream2.print("result2");
        env.execute();

    }
}
