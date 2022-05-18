package com.itguigu.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2021-12-30 16:06
 */

/**
 * @ClassName Spark02_RDD_Operator_Transform
 * @Author: ljy on 2021-12-30 16:06
 * @Version: 1.0
 * @Description：
 * mapPartitions：以分区为单位进行数据装换操作
 *    但是会将整个分区的数据加载到内存进行引用
 *    如果处理完的数据是不会被释放掉，存在对象的引用
 *    在内存较小，数据量较大的场合下，容易出现内存溢出
 */
object Spark02_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("mapPartitions")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    val mapRdd: RDD[Int] = rdd.mapPartitions(
      iter => {
        println(">>>>>>>>>>>>")
        iter.map(_ * 2)
      }
    )
    mapRdd.collect().foreach(println)

    sc.stop()
  }
}
