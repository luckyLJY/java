package com.guigu.wc;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

//流处理
public class StreamWordCount_socket {
    public static void main(String[] args) throws Exception {

        //创建流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();



        String host = args[0];
        Integer port = Integer.parseInt(args[1]);
        
        //从socket读取数据
        DataStreamSource<String> inputDataStream = env.socketTextStream(host, port);


        //并行度
        env.setParallelism(1);

        //基于数据流转换
        DataStream<Tuple2<String, Integer>> resultStream = inputDataStream.flatMap(new WordCount.MyFlatMapper())
                                            .keyBy(0)
                                            .sum(1);



        resultStream.writeAsText(args[2], FileSystem.WriteMode.OVERWRITE);

        //数据流
        resultStream.print();

        //执行
        env.execute();


    }
}
