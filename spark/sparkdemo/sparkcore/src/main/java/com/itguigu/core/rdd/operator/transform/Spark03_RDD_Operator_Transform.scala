package com.itguigu.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2021-12-30 16:13
 */

/**
 * @ClassName Spark03_RDD_Operator_Transform
 * @Author: ljy on 2021-12-30 16:13
 * @Version: 1.0
 * @Description:
 * mapPartitionsWithIndex算子：
 * 将待处理的数据以分区为单位发送到计算节点进行处理，这里的处理是指可以进行任意的处
理，哪怕是过滤数据，在处理时同时可以获取当前分区索引。
 */
object Spark03_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("mapPartitionsWithIndex")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    val mpiRDD: RDD[Int] = rdd.mapPartitionsWithIndex(
      (index, iter) => {
        if (index == 1) {
          iter
        } else {
          Nil.iterator
        }
      }
    )
    mpiRDD.collect().foreach(println)

    sc.stop()
  }
}
