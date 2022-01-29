package com.itguigu.rdd.persist

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.persist
 * Version: 1.0
 * Created by ljy on 2022-1-29 11:08
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-1-29 11:08
 * @Version: 1.0
 * @Description:
 */
object Spark02_RDD_Persist {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Persist")
    val sc = new SparkContext(sparkConf)

    val list = List("Hello Scala", "Hello Spark")
    val rdd: RDD[String] = sc.makeRDD(list)

    val flatMap: RDD[String] = rdd.flatMap(_.split(" "))
    val mapRDD: RDD[(String, Int)] = flatMap.map(word => {
      println("@@@@@@@@@@@@@@@@@")
      (word, 1)
    })

    val reduceRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)
    reduceRDD.collect().foreach(println)
    println("***********************")
    val groupRDD: RDD[(String, Iterable[Int])] = mapRDD.groupByKey()
    groupRDD.collect().foreach(println)

    sc.stop()
  }
}
