package com.itguigu.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2022-1-5 10:08
 */

/**
 * @ClassName Spark07_RDD_Operator_Transform
 * @Author: ljy on 2022-1-5 10:08
 * @Version: 1.0
 * @Description:
 * 根据规则过滤
 */
object Spark07_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("filterRDD")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    val filterRDD: RDD[Int] = rdd.filter(num => num % 2 ==0)

    filterRDD.collect().foreach(println)

    sc.stop()
  }
}
