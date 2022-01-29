package com.itguigu.rdd.operator.action

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.action
 * Version: 1.0
 * Created by ljy on 2022-1-29 9:14
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-1-29 9:14
 * @Version: 1.0
 * @Description:
 */
object Spark03_RDD_Operator_Action {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("operator")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    //TODO: 行动算子

    //10+13+17=40
    //aggregateByKey:初始值会参与分区内计算
    //aggregate:初始值会参与分区内计算，并且和参与分区间计算
    //val result: Int = rdd.aggregate(10)(_ + _, _ + _)

    val result: Int = rdd.fold(10)(_ + _)
    println(result)

    sc.stop()
  }
}
