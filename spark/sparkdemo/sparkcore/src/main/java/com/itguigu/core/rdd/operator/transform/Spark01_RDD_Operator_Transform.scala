package com.itguigu.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2021-12-30 15:42
 */

/**
 * @ClassName Spark01_RDD_Operator_Transform
 * @Author: ljy on 2021-12-30 15:42
 * @Version: 1.0
 * @Description：
 * mapfunction
 */
object Spark01_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    //TODO 准备环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    def  mapFunction(num:Int)={
      num * 2
    }

    val mapRDD: RDD[Int] = rdd.map(mapFunction)

    mapRDD.collect().foreach(println)

    sc.stop()
  }
}
