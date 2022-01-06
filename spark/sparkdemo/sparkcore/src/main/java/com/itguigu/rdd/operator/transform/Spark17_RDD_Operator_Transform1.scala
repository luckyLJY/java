package com.itguigu.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2022-1-6 11:14
 */

/**
 * @ClassName Spark17_RDD_Operator_Transform1
 * @Author: ljy on 2022-1-6 11:14
 * @Version: 1.0
 * @Description
 *
 */
object Spark17_RDD_Operator_Transform1 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    //TODO 算子-（Key-Value类型）
    val rdd: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3),
      ("b", 4), ("b", 5), ("a", 6)
    ))
    // (a,【1,2】), (a, 【3，4】)
    // (a, 2), (a, 4)
    // (a, 6)

    //aggregateByKey存在函数柯里化，有两个参数化列表
    //第一个参数列表，需要传递一个参数，表示初始值
    //    主要用于当碰见第一个key的时候，和value进行分区内计算
    //第二个参数列表需要传递2个参数
    //    第一个参数表示分区内计算规则
    //    第二个参数表示分区间计算规则

    //math.min(x,y)
    //math.max(x,y)
    //?
    rdd.aggregateByKey(5)(
      (x,y) => math.max(x, y),
      (x,y) => x + y
    ).collect.foreach(println)

    rdd.aggregateByKey(0)(
      (x,y) => x+y,
      (x,y) => x+y
    ).collect.foreach(println)

    rdd.aggregateByKey(0)(_+_,_+_).collect.foreach(println)

    sc.stop()
  }
}
