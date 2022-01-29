package com.itguigu.rdd.operator.action

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.action
 * Version: 1.0
 * Created by ljy on 2022-1-29 9:31
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-1-29 9:31
 * @Version: 1.0
 * @Description:
 */
object Spark05_RDD_Operator_Action {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("operator")
    val sc = new SparkContext(sparkConf)

    //val rdd: RDD[Int] = sc.makeRDD(List(1, 1, 1, 4), 2)
    //rdd.saveAsTextFile("output")
    //rdd.saveAsObjectFile("output1")

    val rdd: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("a", 3)
    ))
    //saveAsSequenceFile方法要求数据的格式必须为key-value类型
    rdd.saveAsSequenceFile("output2")

    sc.stop()

  }
}
