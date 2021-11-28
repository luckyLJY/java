package com.guigu.apitest.source;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import java.util.Arrays;

/**
 * @Date 20210913
 * @version 1.0
 * @author ljy
 * @deprecated 读取集合文件
 */
public class SourceTest1_Collection{
    public static void main(String[] args)throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //并行度
        env.setParallelism(1);

        //1.Source:从集合读取数据
        DataStream<SensorReading> sesorDataStream = env.fromCollection(
                Arrays.asList(
                        new SensorReading("sensor_1",1547718199L, 35.8),
                        new SensorReading("sensor_6",1547718201L, 15.4),
                        new SensorReading("sensor_7",1547718202L, 6.7),
                        new SensorReading("sensor_10",1547718205L,38.1)
                )
        );

        //并行度为1
        DataStreamSource<Integer> integerDataStreamSource = env.fromElements(1, 2, 4, 4, 5, 6, 7 );

        //打印
        sesorDataStream.print("data");
        integerDataStreamSource.print("int");

        //执行可以传参jobName
        env.execute();
    }
}
