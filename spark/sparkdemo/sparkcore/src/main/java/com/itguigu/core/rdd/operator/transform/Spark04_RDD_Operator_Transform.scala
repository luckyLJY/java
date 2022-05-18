package com.itguigu.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2021-12-30 17:49
 */

/**
 * @ClassName Spark04_RDD_Operator_Transform
 * @Author: ljy on 2021-12-30 17:49
 * @Version: 1.0
 * @Description:
 * flatmap
 * 将处理的数据进行扁平化后再进行映射处理，所以算子也称之为扁平映射
 * 切割多个数组
 */
object Spark04_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("flatmap")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[List[Int]] = sc.makeRDD(List(
      List(1, 2), List(3, 4)
    ))

    val flatRDD: RDD[Int] = rdd.flatMap(
      list => {
        list
      }
    )

    flatRDD.collect().foreach(println)

    sc.stop()
  }
}
