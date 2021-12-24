package com.ljy.wc;/**
 * Project: spark_java
 * Package: com.ljy.test
 * Version: 1.0
 * Created by ljy on 2021-12-23 11:00
 */

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.MapFunction;
import scala.Tuple2;


import java.util.Arrays;
import java.util.Iterator;

/**
 * @ClassName Main
 * @Author: ljy on 2021-12-23 11:00
 * @Version: 1.0
 * @Description:测试java的spark开发环境
 */
public class WordCount {
    public static void main(String[] args) {
        SparkConf javaEvent = new SparkConf().setAppName("JavaEvent").setMaster("local[*]").set("spark.executor.memory", "2g");
        JavaSparkContext javaSparkContext = new JavaSparkContext(javaEvent);
        //读取文件
        JavaRDD<String> stringJavaRDD = javaSparkContext.textFile("input/word.txt");
        //文件切分
        JavaRDD<String> wordJavaRDD = stringJavaRDD.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                Iterator<String> it = Arrays.asList(s.split(",")).iterator();
                return it;
            }
        });
        //转换数据结构
        JavaRDD<Tuple2<String, Integer>> word2OneRDD = wordJavaRDD.map(new Function<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<>(s, 1);
            }
        });

        //分组组合
        Tuple2<String, Integer> word2Count = word2OneRDD.reduce(new Function2<Tuple2<String, Integer>, Tuple2<String, Integer>, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> call(Tuple2<String, Integer> stringIntegerTuple2, Tuple2<String, Integer> stringIntegerTuple22) throws Exception {
                return new Tuple2<>(stringIntegerTuple22._1, stringIntegerTuple2._2 + stringIntegerTuple22._2);
            }
        });

        //集合采集到内存
        System.out.printf("1", word2Count);

        javaSparkContext.stop();
    }
}