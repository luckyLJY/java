package com.guigu.apitest.state;

import akka.stream.impl.ReducerState;
import com.guigu.apitest.source.beans.SensorReading;
import com.guigu.apitest.transform.TransformTest5_RichFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.*;
import org.apache.flink.configuration.Configuration;
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
public class Test1KeyState {
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

        SingleOutputStreamOperator<Integer> resultStream = dataStream.keyBy("id")
                .map(new MyKeyCountMapper());
        resultStream.print();

        env.execute();

    }

    /**
     * @// TODO: 2021-10-13 自定义实现RichMapFunction
     */
   public static  class MyKeyCountMapper extends RichMapFunction<SensorReading, Integer> {
        //声明一个键控状态
        private ValueState<Integer> keyCountState;

        //其它类型状态的声明
        private ListState<String> myListState;
        private MapState<String,Double> myMapState;
        private ReducingState<SensorReading> myReducingState;



        @Override
        public void open(Configuration parameters) throws Exception {
            keyCountState = getRuntimeContext().getState(new ValueStateDescriptor<Integer>("key-count", Integer.class));
            myListState = getRuntimeContext().getListState(new ListStateDescriptor<String>("my-list",String.class));
            myMapState = getRuntimeContext().getMapState(new MapStateDescriptor<String, Double>("my-map", String.class,Double.class));

            //获取myReduce状态
           /* myReducingState =getRuntimeContext().getState(new ReducingStateDescriptor<SensorReading>("my-reducing", ,));

            ReducingStateDescriptor descriptor = new ReducingStateDescriptor("ReducingDescriptor", new ReduceFunction<SensorReading>() {
                @Override
                public SensorReading reduce(SensorReading sensorReading, SensorReading t1) throws Exception {
                    return null;
                }
            },SensorReading.class);
            myReducingState = getRuntimeContext().getReducingState(descriptor);*/

        }

        @Override
        public Integer map(SensorReading sensorReading) throws Exception {

            //所有状态API都有clear()清空操作

            //其它状态API调用
            //List
            for(String str: myListState.get()){
                System.out.println(str);
            }
            myListState.add("hello");

            if(keyCountState.value() == null){
                keyCountState.update(0);
            }

            //map state
            myMapState.get("key1");
            myMapState.put("key2",25.001);
            myMapState.remove("key2");

            //reducing state 聚合操作
            //myReducingState.add("sensording");

            //读取状态
            Integer count = keyCountState.value();
            count+=1;
            //对状态赋值
            keyCountState.update(count);
            return count;
        }
    }
}
