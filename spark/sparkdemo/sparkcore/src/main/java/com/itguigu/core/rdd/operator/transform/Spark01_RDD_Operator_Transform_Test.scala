package com.itguigu.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2021-12-30 15:23
 */

/**
 * @ClassName Spark01_RDD_Operator_Transform_Test
 * @Author: ljy on 2021-12-30 15:23
 * @Version: 1.0
 * @Description:
 * map算子处理apache.log
 */
object Spark01_RDD_Operator_Transform_Test {
  def main(args: Array[String]): Unit = {
    //TODO 准备环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[String] = sc.textFile("data/apache.log")

    //按空格分开，取第6个空格后的数据
    val mapRDD: RDD[String] = rdd.map(
      line => {
        val datas: Array[String] = line.split(" ")
        datas(6)
      }
    )
    mapRDD.collect().foreach(println)

    sc.stop()
  }
}
