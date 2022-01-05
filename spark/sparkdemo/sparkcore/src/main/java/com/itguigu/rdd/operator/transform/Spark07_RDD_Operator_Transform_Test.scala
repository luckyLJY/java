package com.itguigu.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2022-1-5 10:12
 */

/**
 * @ClassName Spark07_RDD_Operator_Transform_Test
 * @Author: ljy on 2022-1-5 10:12
 * @Version: 1.0
 * @Description:
 * 从服务器日志数据 apache.log 中获取 2015 年 5 月 17 日的请求路径
 */
object Spark07_RDD_Operator_Transform_Test {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("logFilter")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[String] = sc.textFile("data/apache.log")
    rdd.filter(
      line =>{
        val datas: Array[String] = line.split(" ")
        val time: String = datas(3)
        time.startsWith("17/05/2015")
      }
    ).collect().foreach(println)

    sc.stop()
  }
}
