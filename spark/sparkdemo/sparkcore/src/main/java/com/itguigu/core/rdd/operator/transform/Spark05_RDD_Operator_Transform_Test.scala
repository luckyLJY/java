package com.itguigu.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2022-1-5 9:28
 */

/**
 * @ClassName Spark05_RDD_Operator_Transform_Test
 * @Author: ljy on 2022-1-5 9:28
 * @Version: 1.0
 * @Description:
 * 将同一个分区的数据直接转化为相同类型的内存数组进行处理，分区不变
 */
object Spark05_RDD_Operator_Transform_Test {
  def main(args: Array[String]): Unit = {

    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("GlomRDDTest")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    val glomRDD: RDD[Array[Int]] = rdd.glom()

    val maxRDD: RDD[Int] = glomRDD.map(
      array => {
        array.max
      }
    )
    println(maxRDD.collect().sum)

    sc.stop()
  }
}
