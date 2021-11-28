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
import org.apache.flink.util.Collector;


/**
 * @author ljy
 * @date 2021-10-15 15:28
 * @description 温度值在10s之内连续上升，则报警
 */
public class ProcessTest2_ApplicationCase {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<String> inputStream = env.socketTextStream("192.168.75.140", 7777);

        SingleOutputStreamOperator<SensorReading> dataStream = inputStream.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String s) throws Exception {
                String[] fields = s.split(",");
                return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
            }
        });

        dataStream.keyBy("id")
                .process(new TempConsIncreWarning(10))
                .print();



        env.execute();

    }

    /**
     * 实现功能
     * 1.当前温度大于之前温度（温度状态2》）
     *  1.1设定当前的10s定时器
     *      获取当前的process time + 10s
     *      将此时间注册时间触发器
     *          1.1.1得到当前的key
     *
     *      将时间计入时间状态1》
     * 2.当前温度小于之前温度
     *  删除时间触发器
     *  将时间状态清空
     *3.关闭
     *  清空温度状态
     */

    public static class TempConsIncreWarning extends KeyedProcessFunction<Tuple,SensorReading,String>{

        //时间段变量
        private   Integer interval;

        //设置温度状态、时间状态
        private ValueState<Double> lastTempState;
        private ValueState<Long> timerTsState;


        public TempConsIncreWarning(Integer interval) {
            this.interval = interval;
        }


        @Override
        public void open(Configuration parameters) throws Exception {
            lastTempState = getRuntimeContext().getState(new ValueStateDescriptor<Double>("last-temp",Double.class,Double.MIN_VALUE));
            timerTsState = getRuntimeContext().getState(new ValueStateDescriptor<Long>("timer-ts",Long.class));

        }



        @Override
        public void processElement(SensorReading sensorReading, Context context, Collector<String> collector) throws Exception {

            Double lastTemp = lastTempState.value();
            Long timers = timerTsState.value();

            if(sensorReading.getTemperature() > lastTemp && timers == null){
                Long ts= context.timerService().currentProcessingTime() + interval *1000L;
                context.timerService().registerProcessingTimeTimer(ts);
                timerTsState.update(ts);
            }else if(sensorReading.gettimestamp() < lastTemp && timers != null){
                context.timerService().deleteProcessingTimeTimer(timers);
                timerTsState.clear();
            }
        }

        @Override
        public void onTimer(long timestamp, OnTimerContext ctx, Collector<String> out) throws Exception {
            out.collect("传感器"+ctx.getCurrentKey()+"温度值连续"+interval+"s上升");
            timerTsState.clear();
        }

        @Override
        public void close() throws Exception {
            timerTsState.clear();
        }
    }
}
