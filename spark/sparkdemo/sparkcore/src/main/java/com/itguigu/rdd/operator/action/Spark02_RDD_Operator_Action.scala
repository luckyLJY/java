package com.itguigu.rdd.operator.action

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.action
 * Version: 1.0
 * Created by ljy on 2022-1-29 8:59
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-1-29 8:59
 * @Version: 1.0
 * @Description:
 * 行动算子介绍
 */
object Spark02_RDD_Operator_Action {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

    //TODO: 行动算子

    //reduce
    /*val i: Int = rdd.reduce(_ + _)
    println(i)*/

    //collect:方法会将不同分区的数据按照分区顺序采集到Driver端内存中，形成数组
    /*val ints: Array[Int] = rdd.collect()
    println(ints.mkString(","))*/

    //count: 数据源中数据的个数
    /*val cnt: Long = rdd.count()
    println(cnt)*/

    //first:获取数据源中数据的第一个
    /*val first: Int = rdd.first()
    println(first)*/

    //take:获取N个数据
    /*val ints: Array[Int] = rdd.take(3)
    println(ints.mkString(","))*/

    //takeOrdered:数据排序后，取N个数据
    val rdd1: RDD[Int] = sc.makeRDD(List(4, 3, 2, 1))
    val ints1: Array[Int] = rdd1.takeOrdered(3)
    println(ints1.mkString(","))

    sc.stop()
  }
}
