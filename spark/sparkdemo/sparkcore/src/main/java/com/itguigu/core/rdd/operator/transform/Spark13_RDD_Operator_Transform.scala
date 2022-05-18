package com.itguigu.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2022-1-6 9:43
 */

/**
 * @ClassName Spark13_RDD_Operator_Transform
 * @Author: ljy on 2022-1-6 9:43
 * @Version: 1.0
 * @Description：
 * 集合操作
 */
object Spark13_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OpreatorCollection")
    val sc = new SparkContext(sparkConf)

    //交集、并集、差集要求两个数据源数据类类型保持一致
    //拉链操作两个数据源的类型可以不一致

    val rdd1: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    val rdd2: RDD[Int] = sc.makeRDD(List(3, 4, 5, 6))
    val rdd7: RDD[String] = sc.makeRDD(List("3", "4", "5", "6"))

    //交集：[3,4]
    val rdd3: RDD[Int] = rdd1.intersection(rdd2)
    println("交集:"+rdd3.collect().mkString(","))

    //并集：[1,2,3,4,5,6]
    val rdd4: RDD[Int] = rdd1.union(rdd2)
    println("并集:"+rdd4.collect().mkString(","))

    //差集：[1,2]
    val rdd5: RDD[Int] = rdd1.subtract(rdd2)
    println("差集:"+rdd5.collect().mkString(","))

    //拉链：[1-3,2-4,3-5,4-6]
    val rdd6: RDD[(Int, Int)] = rdd1.zip(rdd2)
    val rdd8: RDD[(Int, String)] = rdd1.zip(rdd7)
    println("拉链:"+rdd8.collect().mkString(","))

    sc.stop()
  }
}
