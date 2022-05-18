package com.itguigu.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2022-1-6 10:32
 */

/**
 * @ClassName Spark15_RDD_Operator_Transform
 * @Author: ljy on 2022-1-6 10:32
 * @Version: 1.0
 * @Description:
 * 可以将数据按照相同的 Key 对 Value 进行聚合
 */
object Spark15_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    //TODO 算子-（Key-Value类型）
    val rdd: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("a", 3), ("b", 4)
    ))

    //reduceByKey：相同的key的数据进行value数据进行聚合
    // scala语言中一般的聚合操作都是两两聚合，spark基于scala开发的，所以它的聚合也是两两聚合
    // 【1，2，3】
    // 【3，3】
    // 【6】
    // reduceByKey中如果key的数据只有一个，是不会参与运算的。
    val reduceRDD: RDD[(String, Int)] = rdd.reduceByKey(
      (x: Int, y: Int) => {
        println(s"x=${x},y=${y}")
        x + y
      }
    )

    reduceRDD.collect().foreach(println)

    sc.stop()
  }
}
