package com.itguigu.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2022-1-5 10:39
 */

/**
 * @ClassName Spark12_RDD_Operator_Transform
 * @Author: ljy on 2022-1-5 10:39
 * @Version: 1.0
 * @Description：
 * 排序
 */
object Spark12_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(4, 3, 5, 2, 1), 2)
    val newRDD: RDD[Int] = rdd.sortBy(num => num)

    newRDD.saveAsTextFile("output")

    sc.stop()
  }
}
