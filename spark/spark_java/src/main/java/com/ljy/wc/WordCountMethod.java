package com.ljy.wc;/**
 * Project: spark_java
 * Package: com.ljy.wc
 * Version: 1.0
 * Created by ljy on 2022-6-23 11:44
 */

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @ClassName
 * @Author: ljy on 2022-6-23 11:44
 * @Version: 1.0
 * @Description:
 */
public class WordCountMethod {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setAppName("loacl[*]");
        conf.setMaster("wordcount");

        JavaSparkContext sc = new JavaSparkContext(conf);
    }


}
