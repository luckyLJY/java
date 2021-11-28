package com.guigu.wc;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

//批量处理
public class WordCount {
    public static void main(String[] args) throws   Exception{
        //执行环境
        ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
        //从文件读取数据
        String inputPath ="F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\hello.txt";
        DataSource<String> stringDataSource = executionEnvironment.readTextFile(inputPath);
        //对数据进行处理：按空格分组，转换成(word,1)二元组
            DataSet<Tuple2<String, Integer>> resultSet = stringDataSource.flatMap(new MyFlatMapper())
                .groupBy(0)//按照第一个位置的word分组;
                .sum(1);//将第二个位置上的数据求和

        resultSet.print();
    }
    public static class  MyFlatMapper implements FlatMapFunction<String, Tuple2<String,Integer>> {


        @Override
        public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
            //按空格分组
            String[] words = s.split(" ");
            //遍历有word，包成二元组
            for(String word: words){
                collector.collect(new Tuple2<>(word,1));
            }
        }
    }
}
