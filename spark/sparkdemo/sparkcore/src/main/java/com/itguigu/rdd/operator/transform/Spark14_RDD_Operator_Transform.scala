package com.itguigu.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2022-1-6 10:11
 */

/**
 * @ClassName Spark14_RDD_Operator_Transform
 * @Author: ljy on 2022-1-6 10:11
 * @Version: 1.0
 * @Description:
 * 将数据按照指定 Partitioner 重新进行分区。Spark 默认的分区器是 HashPartitioner
 */
object Spark14_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    //TODO 算子-（Key-Vlaue类型）
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    val mapRDD: RDD[(Int, Int)] = rdd.map((_, 1))
    // RDD => PairRDDFunctions
    //隐式转换(二次编译)

    //partitionBy根据指定分区规则对数据进行重分区
    val newRDD: RDD[(Int, Int)] = mapRDD.partitionBy(new HashPartitioner(2))
    newRDD.partitionBy(new HashPartitioner(2))

    newRDD.saveAsTextFile("output")

    sc.stop()
  }
}
