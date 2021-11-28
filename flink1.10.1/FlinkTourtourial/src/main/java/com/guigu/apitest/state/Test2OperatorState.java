package com.guigu.apitest.state;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.checkpoint.ListCheckpointed;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Collections;
import java.util.List;

/**
 * @author ljy
 * @date 2021-10-12 16:03
 * @description 算子状态
 */
public class Test2OperatorState {
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

        SingleOutputStreamOperator<Integer> countStream = dataStream.map(new MyOperator());
        countStream.print();

        env.execute();

    }
    public  static class MyOperator implements MapFunction<SensorReading,Integer>, ListCheckpointed<Integer> {

        //定义一个本地变量作为，算子状态
        private Integer count =0;


        @Override
        public Integer map(SensorReading sensorReading) throws Exception {
            count++;
            return count;
        }

        @Override
        public List<Integer> snapshotState(long l, long l1) throws Exception {
            return Collections.singletonList(count);
        }

        @Override
        public void restoreState(List<Integer> list) throws Exception {
            for (Integer num: list){
                count += num;
            }
        }
    }
}
