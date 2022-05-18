package com.itguigu.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2022-1-5 9:38
 */

/**
 * @ClassName Spark05_RDD_Operator_Transform
 * @Author: ljy on 2022-1-5 9:38
 * @Version: 1.0
 * @Description:
 * 分区转换数据类型
 * Int=>Array
 */
object Spark05_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {

    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("GlomRDD")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    val glomRDD: RDD[Array[Int]] = rdd.glom()

    glomRDD.collect().foreach(data=> println(data.mkString(",")))

    sc.stop()
  }
}
