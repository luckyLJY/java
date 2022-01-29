package com.itguigu.rdd.persist

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.persist
 * Version: 1.0
 * Created by ljy on 2022-1-29 11:03
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-1-29 11:03
 * @Version: 1.0
 * @Description:
 */
object Spark01_RDD_Persist {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    val sc = new SparkContext(sparkConf)

    val list = List("Hello Scala", "Hello Spark")
    val rdd: RDD[String] = sc.makeRDD(list)

    val flatRDD: RDD[String] = rdd.flatMap(_.split(" "))
    val mapRDD: RDD[(String, Int)] = flatRDD.map((_, 1))
    val reduceRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)

    reduceRDD.collect().foreach(println)
    println("****************************")


    val rdd1 = sc.makeRDD(list)

    val flatRDD1 = rdd1.flatMap(_.split(" "))

    val mapRDD1 = flatRDD1.map((_,1))

    val groupRDD = mapRDD1.groupByKey()

    groupRDD.collect().foreach(println)

    sc.stop()
  }

}
