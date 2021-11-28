package com.guigu.apitest.transform;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class TransformTest5_RichFunction {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(4);

        //从文件中读取数据
        DataStream<String> inputStream = env.readTextFile("F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\sensor.txt");

        //转换成SensorReading类型
        DataStream<SensorReading> dataStream = inputStream.map(line -> {
            String[] fields = line.split(",");
            return new SensorReading(fields[0],new Long(fields[1]),new Double(fields[2]));
        });

        DataStream<Tuple2<String,Integer>> resultStream = dataStream.map(new MyMapper());

        resultStream.print();


        env.execute();
    }
    public static  class MyMpaper0 implements MapFunction<SensorReading,Tuple2<String,Integer>>{

        @Override
        public Tuple2<String, Integer> map(SensorReading sensorReading) throws Exception {
            return new Tuple2<>(sensorReading.getId(),sensorReading.getId().length());
        }
    }

        //实现自定义的富函数类
        public static class  MyMapper extends RichMapFunction<SensorReading, Tuple2<String, Integer>> {

            @Override
            public Tuple2<String, Integer> map(SensorReading sensorReading) throws Exception {
                return new Tuple2<>(sensorReading.getId(),getRuntimeContext().getIndexOfThisSubtask());
            }

            @Override
            public void open(Configuration parameters) throws Exception {
                //初始化工作，一般定义状态，或者建立数据库连接
                System.out.println("open");
            }

            @Override
            public void close() throws Exception {
                //关闭连接，清空状态
                System.out.println("close");
            }
        }



    }
