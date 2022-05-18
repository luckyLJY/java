package com.itguigu.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.json4s.scalap.scalasig.ScalaSigEntryParsers.index


/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2021-12-30 17:23
 */

/**
 * @ClassName Spark03_RDD_Operator_Transform1
 * @Author: ljy on 2021-12-30 17:23
 * @Version: 1.0
 * @Description:
 *
 */
object Spark03_RDD_Operator_Transform1 {
  def main(args: Array[String]): Unit = {

    val sparkConf: SparkConf = new SparkConf().setAppName("mapPartitionsWithIndex").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

    val mpiRDD = rdd.mapPartitionsWithIndex(
      (index, iter) => {
        // 1,   2,    3,   4
        //(0,1)(2,2),(4,3),(6,4)
        iter.map(
          num => {
            (index, num)
          }
        )
      }
    )
  mpiRDD.collect().foreach(println)

    sc.stop()
  }
}
