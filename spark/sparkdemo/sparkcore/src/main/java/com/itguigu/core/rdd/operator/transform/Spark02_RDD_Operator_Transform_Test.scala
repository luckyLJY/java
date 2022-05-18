package com.itguigu.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2021-12-30 15:59
 */

/**
 * @ClassName Spark02_RDD_Operator_Transform_Test
 * @Author: ljy on 2021-12-30 15:59
 * @Version: 1.0
 * @Description:
 * mapPartitions算子
 * 以分区的为单位发送到计算节点进行处理
 */
object Spark02_RDD_Operator_Transform_Test {
  def main(args: Array[String]): Unit = {
    //TODO 准备环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    //[1,2],[3,4]
    //[2],[4]
    val mapRDD: RDD[Int] = rdd.mapPartitions(
      iter => {
        List(iter.max).iterator
      }
    )

    mapRDD.collect().foreach(println)
  }
}
