package com.itguigu.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2021-12-30 17:59
 */

/**
 * @ClassName Spark04_RDD_Operator_Transform2
 * @Author: ljy on 2021-12-30 17:59
 * @Version: 1.0
 * @Description:
 * flatmap:多维数组
 * 扁平化映射
 */
object Spark04_RDD_Operator_Transform2 {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("MulArray")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Any] = sc.makeRDD(List(List(1, 2), 3, List(4, 5)))
    val flatRDD: RDD[Any] = rdd.flatMap(

      data => {
        data match {
          case list: List[_] => list
          case dat => List(dat)
        }
      }
    )

    flatRDD.collect().foreach(println)

    sc.stop()
  }
}
