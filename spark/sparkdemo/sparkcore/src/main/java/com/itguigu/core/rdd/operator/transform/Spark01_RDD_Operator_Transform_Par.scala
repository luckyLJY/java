package com.itguigu.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2021-12-30 14:51
 */

/**
 * @ClassName Spark01_RDD_Operator_Transform_Par
 * @Author: ljy on 2021-12-30 14:51
 * @Version: 1.0
 * @Description:
 * TODO:map算子
 * 1. rdd的计算一个分区的数据是一个一个执行逻辑
 *    只有前面一个数据全部的逻辑执行完毕后，才会执行下一个数据
 *    分区内数据的执行时有序的
 * 2. 不同分区数据计算是无序的
 */
object Spark01_RDD_Operator_Transform_Par {
  def main(args: Array[String]): Unit = {
    //TODO 准备环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    val mapRDD: RDD[Int] = rdd.map(
      num => {
        print(">>>>>" + num)
        num
      }
    )

    val mapRDD1: RDD[Int] = mapRDD.map(
      num => {
        print("###" + num)
        num
      }
    )

    mapRDD1.collect()

    sc.stop()
  }
}
