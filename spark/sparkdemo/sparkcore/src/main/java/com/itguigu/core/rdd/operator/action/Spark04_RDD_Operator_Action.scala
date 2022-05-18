package com.itguigu.core.rdd.operator.action

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.action
 * Version: 1.0
 * Created by ljy on 2022-1-29 9:24
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-1-29 9:24
 * @Version: 1.0
 * @Description:
 */
object Spark04_RDD_Operator_Action {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("operator")
    val sc = new SparkContext(sparkConf)

    /*val rdd: RDD[Int] = sc.makeRDD(List(1, 1, 1, 4), 2)
    val intToLong: collection.Map[Int, Long] = rdd.countByValue()
    println(intToLong)*/

    val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("a", 2), ("a", 3)))
    val stringToLong: collection.Map[String, Long] = rdd.countByKey()
    println(stringToLong)

    sc.stop()
  }
}
