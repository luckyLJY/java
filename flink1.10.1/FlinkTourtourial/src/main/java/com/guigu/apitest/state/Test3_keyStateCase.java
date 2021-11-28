package com.guigu.apitest.state;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import scala.Tuple3;

/**
 * @author ljy
 * @date 2021-10-13 14:57
 * @description 温差超过10度的数据
 */
public class Test3_keyStateCase {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> inputStream = env.socketTextStream("192.168.75.140", 7777);


        SingleOutputStreamOperator<SensorReading> dataStream = inputStream.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String s) throws Exception {
                String[] fields = s.split(",");
                return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
            }
        });

        //定义flatmap操作，检测温度跳变，输出报警
        SingleOutputStreamOperator<Tuple3<String, Double, Double>> waringStream = dataStream.keyBy("id")
                .flatMap(new TempChangeWarning(10.0));

        waringStream.print();

        env.execute();
    }

    /**
     * @// TODO: 2021-10-13 定义温度报警函数类
     */
    public static class TempChangeWarning extends RichFlatMapFunction<SensorReading, Tuple3<String, Double, Double>>{

        private Double threshold;

        public TempChangeWarning (Double threshold){
            this.threshold = threshold;
        }

        //定义valueState状态，存储上次的温度值
        private ValueState<Double> tempState;

        //在open函数中注册状态，
        @Override
        public void open(Configuration parameters) throws Exception {
            tempState = getRuntimeContext().getState(new ValueStateDescriptor<Double>("last-temp", Double.class));
        }

        /**
         * @// TODO: 2021-10-13
         *  1.获取状态
         *  2.计算温差
         *  3，温差大于 等于10.0，存放到输出流中
         *  4.更新状态
         * @param sensorReading
         * @param collector
         * @throws Exception
         */
        @Override
        public void flatMap(SensorReading sensorReading, Collector<Tuple3<String, Double, Double>> collector) throws Exception {

            // 获取状态
            Double lastTemp = tempState.value();

            // 如果状态不为null，那么就判断两次温度差值
            if( lastTemp != null ){
                Double diff = Math.abs( sensorReading.getTemperature() - lastTemp );
                if( diff >= threshold )
                    collector.collect(new Tuple3<>(sensorReading.getId(), lastTemp, sensorReading.getTemperature()));
            }

            tempState.update(sensorReading.getTemperature());
        }

        /**
         * 清空状态
         * @throws Exception
         */
        @Override
        public void close() throws Exception {
            tempState.clear();
        }
    }
}
