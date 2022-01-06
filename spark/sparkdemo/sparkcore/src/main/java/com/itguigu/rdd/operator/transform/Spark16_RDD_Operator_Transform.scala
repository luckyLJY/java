package com.itguigu.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2022-1-6 10:40
 */

/**
 * @ClassName:Spark16_RDD_Operator_Transform
 * @Author: ljy on 2022-1-6 10:40
 * @Version: 1.0
 * @Description:
 * groupByKey
 */
object Spark16_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    //TODO 算子-（Key-Value类型）

    val rdd: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("a", 3), ("b", 4)
    ))

    //groupByKey:将数据源中的数据，相同Key的数据分在一个组中，形成一个对偶数组
    //    元组中第一个元素就是key
    //    元组中第二个元素就是相同key的value的集合
    val groupRDD: RDD[(String, Iterable[Int])] = rdd.groupByKey()

    groupRDD.collect().foreach(println)

    sc.stop()
  }
}
