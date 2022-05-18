package com.itguigu.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2022-1-5 10:25
 */

/**
 * @ClassName Spark10_RDD_Operator_Transform
 * @Author: ljy on 2022-1-5 10:25
 * @Version: 1.0
 * @Description:
 * 收缩合并分区
 */
object Spark10_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("coalesceRDD")
    val sc = new SparkContext(sparkConf)


    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6), 3)

    // coalesce方法默认情况下不会将分区的数据打乱重新组合
    // 这种情况下的缩减分区可能会导致数据不均衡，出现数据倾斜
    // 如果想要让数据均衡，可以进行shuffle处理
    //val newRDD: RDD[Int] = rdd.coalesce(2)
    val newRDD: RDD[Int] = rdd.coalesce(2,true)
    sc.stop()
  }
}
