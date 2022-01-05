package com.itguigu.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2022-1-5 9:45
 */

/**
 * @ClassName Spark06_RDD_Operator_Transform
 * @Author: ljy on 2022-1-5 9:45
 * @Version: 1.0
 * @Description:
 *
 */
object Spark06_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {

    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("GroupyRDD")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    def groupFunction(num:Int)={
      num % 2
    }

    val groupRDD: RDD[(Int, Iterable[Int])] = rdd.groupBy(groupFunction)
    groupRDD.collect().foreach(println)

    sc.stop()
  }
}
