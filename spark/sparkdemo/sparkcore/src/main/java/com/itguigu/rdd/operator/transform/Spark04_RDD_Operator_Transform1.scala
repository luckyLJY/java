package com.itguigu.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2021-12-30 17:53
 */

/**
 * @ClassName Spark04_RDD_Operator_Transform1
 * @Author: ljy on 2021-12-30 17:53
 * @Version: 1.0
 * @Description：
 * flatmap
 * 切割多个字符串
 *"Hello Scala", "Hello Spark"
    Hello
    Scala
    Hello
    Spark
 */
object Spark04_RDD_Operator_Transform1 {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("flatmapMulString")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[String] = sc.makeRDD(List(
      "Hello Scala", "Hello Spark"
    ))

    val flatRDD: RDD[String] = rdd.flatMap(
      s => {
        s.split(" ")
      }
    )
    flatRDD.collect().foreach(println)

    sc.stop()
  }
}
