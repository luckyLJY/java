package com.guigu.wc;

import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;

//流处理
public class StreamWordCount {
    public static void main(String[] args) throws Exception {

        //创建流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //F:\新建文件夹\java\flink\FlinkTourtourial\src\main\resources\hello.txt
        //从文件读取数据
        String inputPath = args[0];
        DataStream<String> inputDataStream = env.readTextFile(inputPath);
        
        //从socket读取数据
        //DataStreamSource<String> inputDataStream = env.socketTextStream("192.168.75.100", 7777);


        //并行度
        env.setParallelism(1);

        //基于数据流转换
        DataStream<Tuple2<String, Integer>> resultStream = inputDataStream.flatMap(new WordCount.MyFlatMapper())
                                            .keyBy(0)
                                            .sum(1);

        //新版的sink到文件
        /*resultStream.addSink(StreamingFileSink.forRowFormat(
                new Path(args[1]),
                new SimpleStringEncoder())
                .build()
        );*/

        resultStream.writeAsText(args[1], FileSystem.WriteMode.OVERWRITE);

        //数据流
        //resultStream.print();

        //执行
        env.execute();


    }
}
