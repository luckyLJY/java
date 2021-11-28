package com.guigu.apitest.processfunction;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.operators.KeyedProcessOperator;
import org.apache.flink.util.Collector;

/**
 * @author ljy
 * @date 2021-10-14 18:03
 * @description keyedStream流使用ProcessFunction
 * 补充timeService()定时服务
 */
public class ProcessTest1_KeyedProcessFunction {
    public static void main(String[] args) throws  Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //socket文本流
        DataStreamSource<String> inputStream = env.socketTextStream("192.168.75.140", 7777);

        //转换成SensorReding类型
        SingleOutputStreamOperator<SensorReading> dataStream = inputStream.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String s) throws Exception {
                String[] fields = s.split(",");
                return new SensorReading(fields[0],new Long(fields[1]),new Double(fields[2]));
            }
        });

        //测试keyedProcessonReading类型
        dataStream.keyBy("id")
                .process(new MyProcess())
                .print();

        env.execute();
    }

    public static class MyProcess extends KeyedProcessFunction<Tuple,SensorReading,Integer>{

        private ValueState<Long> tsTimeState;

        @Override
        public void open(Configuration parameters) throws Exception {
            tsTimeState = getRuntimeContext().getState(new ValueStateDescriptor<Long>("ts-timer", Long.class));
        }

        @Override
        public void processElement(SensorReading sensorReading, Context context, Collector<Integer> collector) throws Exception {
            collector.collect(sensorReading.getId().length());

            System.out.println(tsTimeState);
            System.out.println("timeService"+context.timerService());
            System.out.println("当前进程时间"+context.timerService().currentProcessingTime());
            System.out.println("当前系统时间戳"+context.timestamp());
            System.out.println("当前key"+context.getCurrentKey());
            System.out.println("当前的水平线"+context.timerService().currentWatermark());

           //注册当前的key的processingtime的定时器，当processing time到达时间时，会触发key
            context.timerService().registerProcessingTimeTimer(context.timerService().currentProcessingTime()+5000L);
            tsTimeState.update(context.timerService().currentProcessingTime()+1000L);
            //context.timerService().registerEventTimeTimer((sensorReading.getTimestap()+10)*1000L);
            //context.timerService().deleteProcessingTimeTimer(tsTimeState.value());
        }

        @Override
        public void onTimer(long timestamp, OnTimerContext ctx, Collector<Integer> out) throws Exception {
            System.out.println(timestamp+ "定时器触发");
            System.out.println(ctx.getCurrentKey());
            //2021-10-17 事件类型
            System.out.println(ctx.timeDomain());
        }

        @Override
        public void close() throws Exception {
            tsTimeState.clear();
        }
    }

}
