package com.guigu.apitest.source;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.HashMap;
import java.util.Random;

public class SourceTest4_UDF {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //从文件读取数据
        DataStream<SensorReading> dataStream = env.addSource(new MySensorSource());

        //打印
        dataStream.print();

        //执行
        env.execute();
    }


    //实现自定义的SourceFunction
    public static class MySensorSource implements SourceFunction<SensorReading>{

        //定义标志为用于控制数据的产生
        private boolean running = true;

        @Override
        public void run(SourceContext<SensorReading> sourceContext) throws Exception {
            //定义随机数触发器
            Random random = new Random();

            //设置10个触感器的初始温度
            HashMap<String,Double>  sensorTempMap = new HashMap<>();
            for(int i = 0 ;i<10 ;i++){
                sensorTempMap.put("sensor_"+(i+1),60 + random.nextGaussian()*20);
            }

            while (running){
                for (String sensorId: sensorTempMap.keySet()){
                    //当前温度基础上随机波动
                    Double newTemp = sensorTempMap.get(sensorId) + random.nextGaussian();
                    sensorTempMap.put(sensorId,newTemp);
                    sourceContext.collect(new SensorReading(sensorId,System.currentTimeMillis(),newTemp));
                }
                //控制输出评率
                Thread.sleep(1000);
            }
        }

        @Override
        public void cancel() {
            running = false;
        }
    }
}

