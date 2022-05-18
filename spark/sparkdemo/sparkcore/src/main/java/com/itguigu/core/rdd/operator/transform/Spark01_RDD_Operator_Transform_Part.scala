package com.itguigu.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2021-12-30 15:13
 */

/**
 * @ClassName Spark01_RDD_Operator_Transform_Part
 * @Author: ljy on 2021-12-30 15:13
 * @Version: 1.0
 * @Description:
 * map分区计算输出
 */
object Spark01_RDD_Operator_Transform_Part {
  def main(args: Array[String]): Unit = {

    //TODO 准备环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    //一个并行度直接输出
    rdd.saveAsTextFile("output")
    //一个并行度计算输出
    val mapRDD: RDD[Int] = rdd.map(_ * 2)
    mapRDD.saveAsTextFile("output1")

    sc.stop()
  }
}
