package com.itguigu.wc

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.wc
 * Version: 1.0
 * Created by ljy on 2021-12-24 11:33
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2021-12-24 11:33
 * @Version: 1.0
 * @Description:单词统计
 *  1.建立spark连接
 *  2.读取文件
 *  3.使用flatMap分词
 *  4.使用map数据转化
 *  5.使用reduceByKey统计单词
 *  6.采集打印控制台
 *  7.关闭连接
 */
object Spark02_WordCount {
  def main(args: Array[String]): Unit = {
    
    val sparkConf = new SparkConf().setAppName("wordcount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val lines:RDD[String] = sc.textFile("input/word.txt")

    val words:RDD[String] = lines.flatMap(_.split(","))

    val wordToOne:RDD[(String,Int)] = words.map(word => (word, 1))

    val wordSum:RDD[(String,Int)] = wordToOne.reduceByKey(_ + _)

    val array:Array[(String,Int)] = wordSum.collect()
    array.foreach(println)

    sc.stop()
  }
}
