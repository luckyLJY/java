package com.itguigu.core.rdd.persist

import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.persist
 * Version: 1.0
 * Created by ljy on 2022-1-29 11:24
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-1-29 11:24
 * @Version: 1.0
 * @Description:
 */
object Spark03_RDD_Persist {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Persist")
    val sc = new SparkContext(sparkConf)

    val list = List("Hello Scala", "Hello Spark")

    val rdd: RDD[String] = sc.makeRDD(list)
    val flatRDD: RDD[String] = rdd.flatMap(_.split(" "))

    val mapRDD: RDD[(String, Int)] = flatRDD.map(word => {
      println("@@@@@@@@@")
      (word, 1)
    })
    //cache默认持久化的操作，只能将数据保存到内存中，如果想要保存到磁盘文件，需要更改存储级别
    //mapRDD.cache()

    //持久化操作必须在行动算子执行时完成
    mapRDD.persist(StorageLevel.DISK_ONLY)

    val reduceRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)
    reduceRDD.collect().foreach(println)
    println("*************************************")

    val groupRDD: RDD[(String, Iterable[Int])] = mapRDD.groupByKey()
    groupRDD.collect().foreach(println)

    sc.stop()
  }
}
