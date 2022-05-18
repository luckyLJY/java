package com.itguigu.core.acc

import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.acc
 * Version: 1.0
 * Created by ljy on 2022-4-26 20:56
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-4-26 20:56
 * @Version: 1.0
 * @Description:
 */
object Spark03_Acc {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("Acc")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

    //获取系统累加器
    //Spark默认就提供了简单数据聚合的累加器
    //获取系统累加器
    val sumAcc: LongAccumulator = sc.longAccumulator("sum")

    //sc.doubleAccumulator
    //sc.collectionAccumulator

    val mapRDD: RDD[Int] = rdd.map(
      num => {
        //使用累加器
        sumAcc.add(num)
        num
      }
    )

    //获取累加器的值
    //少加：转换算子中调用累加器，如果没有行动算子的话，那么不会执行
    //多加：转换算子中调用累加器，如果没有行动算子的话，那么不会执行
    //一般情况下，累加器会放置在行动算子进行操作
    mapRDD.collect()
    mapRDD.collect()
    //获取累加器的值
    println(sumAcc.value)
    sc.stop()
  }
}
