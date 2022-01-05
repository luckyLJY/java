package com.itguigu.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2022-1-5 9:51
 */

/**
 * @ClassName Spark06_RDD_Operator_Transform1
 * @Author: ljy on 2022-1-5 9:51
 * @Version: 1.0
 * @Description:
 * 将 List("Hello", "hive", "hbase", "Hadoop")根据单词首写字母进行分组。
 * 分区与分组没有关系
 */
object Spark06_RDD_Operator_Transform1 {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("GroupbyFirstC")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[String] = sc.makeRDD(List("Hello", "hive", "hbase", "Hadoop"), 2)
    val groupRDD: RDD[(Char, Iterable[String])] = rdd.groupBy(_.charAt(0))

    groupRDD.collect().foreach(println)

    sc.stop()
  }
}
