package com.ljy.wc;/**
 * Project: spark_java
 * Package: com.ljy.test
 * Version: 1.0
 * Created by ljy on 2021-12-23 11:00
 */

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.MapFunction;
import scala.Int;
import scala.Tuple2;


import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @ClassName Main
 * @Author: ljy on 2021-12-23 11:00
 * @Version: 1.0
 * @Description:测试java的spark开发环境
 */
public class WordCount {
    public static void main(String[] args) {

        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark_wordcount_java");

        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> lines = sc.textFile("./input/word.txt");
        JavaRDD<String> words = lines.flatMap(line -> Arrays.asList(line.split(",")).iterator());

        //使用group ->map
        /*JavaPairRDD<String, Iterable<String>> wordGroup = words.groupBy(new Function<String, String>() {

            @Override
            public String call(String s) throws Exception {
                return s;
            }
        });

        JavaRDD<Tuple2<String, Integer>> result = wordGroup.map(new Function<Tuple2<String, Iterable<String>>, Tuple2<String, Integer>>() {

            @Override
            public Tuple2<String, Integer> call(Tuple2<String, Iterable<String>> stringIterableTuple2) throws Exception {
                return new Tuple2<String, Integer>(stringIterableTuple2._1, StreamSupport.stream(stringIterableTuple2._2.spliterator(), false).collect(Collectors.toList()).size());
            }
        });*/

        //1. 使用reducekey
        JavaPairRDD<String, Integer> wordToCount = words.mapToPair(word -> new Tuple2<>(word, 1));

        //使用reduceByKey
        JavaPairRDD<String, Integer> result = wordToCount.reduceByKey((v1, v2) -> (v1 + v2));//


        result.foreach(tuple2 -> System.out.println(tuple2));

    }
}
