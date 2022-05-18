package com.itguigu.core.rdd.dep

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.dep
 * Version: 1.0
 * Created by ljy on 2022-1-29 10:44
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-1-29 10:44
 * @Version: 1.0
 * @Description:
 */
object Spark02_RDD_Dep {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Dep")
    val sc = new SparkContext(sparkConf)

    val lines: RDD[String] = sc.textFile("data/word.txt")
    println(lines.dependencies)
    println("************************")

    val words: RDD[String] = lines.flatMap(_.split(" "))
    println(words.dependencies)
    println("*************************")

    val wordTone: RDD[(String, Int)] = words.map(word => (word, 1))
    println(wordTone.dependencies)
    println("************************")

    val wordToSum: RDD[(String, Int)] = wordTone.reduceByKey(_ + _)
    println(wordToSum.dependencies)
    println("**************************")

    wordToSum.collect().foreach(println)


    sc.stop()
  }
}
