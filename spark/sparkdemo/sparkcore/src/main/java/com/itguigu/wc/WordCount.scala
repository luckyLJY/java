package com.itguigu.wc

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.wc
 * Version: 1.0
 * Created by ljy on 2021-11-27 21:34
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2021-11-27 21:34
 * @Version: 1.0
 * @Description:字符统计
 */
object WordCount {
  def main(args: Array[String]): Unit = {
    //TODO: 创建Spark运行配置对象
    val conf = new SparkConf().setAppName("WordCount")
      .setMaster("local[*]")
      .set("spark.executor.memory","2g")


    //TODO: 创建Spark上下文环境对象（连接对象）
    val sc:SparkContext = new SparkContext(conf)

    //TODO: 读取文件数据
    val fileRDD: RDD[String] = sc.textFile("input/word.txt")

    //TODO： 将文件中的数据进行分词
    val wordRDD:RDD[String] = fileRDD.flatMap(_.split(","))

    //TODO: 转换数据结构word => （word,1）
    val word2OneRDD:RDD[(String,Int)] = wordRDD.map((_, 1))

    //TODO: 将转换结构后的数据安装相同的单词进行分组聚合
    val word2CountRDD:RDD[(String,Int)] = word2OneRDD.reduceByKey(_ + _)

    //TODO: 将数据聚合结果采集到内存中
    val word2Count:Array[(String,Int)] = word2CountRDD.collect()

    //TODO: 打印结果
    word2Count.foreach(println)

    //TODO: 关闭Spark连接
    sc.stop()
  }
}
