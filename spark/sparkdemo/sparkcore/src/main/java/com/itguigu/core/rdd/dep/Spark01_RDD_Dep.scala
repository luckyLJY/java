package com.itguigu.core.rdd.dep

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.dep
 * Version: 1.0
 * Created by ljy on 2022-1-29 10:35
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-1-29 10:35
 * @Version: 1.0
 * @Description:
 */
object Spark01_RDD_Dep {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    val sc = new SparkContext(sparkConf)

    val lines: RDD[String] = sc.textFile("data/word.txt")
    println(lines.toDebugString)
    println("*****************")
    val words: RDD[String] = lines.flatMap(_.split(" "))
    println(words.toDebugString)
    println("*****************")
    val wordToOne: RDD[(String, Int)] = words.map(word => (word, 1))
    println(wordToOne.toDebugString)
    println("*****************")
    val wordSum: RDD[(String, Int)] = wordToOne.reduceByKey(_ + _)
    println(wordSum.toDebugString)
    println("*****************")
    val array: Array[(String, Int)] = wordSum.collect()
    array.foreach(println)

    sc.stop()
  }
}
