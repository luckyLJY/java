package com.guigu.apitest.processfunction;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

/**
 * @author ljy
 * @date 2021-10-15 16:40
 * @description 侧输出流：
 * 功能：
 *  大于30度高温输出流
 *  小于30度低温输出流
 */
public class ProcessTest3_SideOutput {
    public static void main(String[] args) throws  Exception{
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

        //定义一个高温输出流
        OutputTag<SensorReading> outputTag = new OutputTag<SensorReading>("highTemp"){};

        SingleOutputStreamOperator<SensorReading> lowTempStream = dataStream.process(new ProcessFunction<SensorReading, SensorReading>() {
            @Override
            public void processElement(SensorReading sensorReading, Context context, Collector<SensorReading> collector) throws Exception {
                if (sensorReading.getTemperature() > 30) {
                    context.output(outputTag, sensorReading);
                } else {
                    collector.collect(sensorReading);
                }
            }
        });

        lowTempStream.print("lowtemp");
        lowTempStream.getSideOutput(outputTag).print("highTemp");
        env.execute();
    }
}
