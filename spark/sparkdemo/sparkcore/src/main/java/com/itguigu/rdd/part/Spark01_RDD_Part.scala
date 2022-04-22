package com.itguigu.rdd.part

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.part
 * Version: 1.0
 * Created by ljy on 2022-4-17 10:35
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-4-17 10:35
 * @Version: 1.0
 * @Description:
 */
object Spark01_RDD_Part {

  def main(args: Array[String]): Unit = {
    val  sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(
      ("nba", "xxxxxxxxx"),
      ("cba", "xxxxxxxxx"),
      ("wnba", "xxxxxxxxx"),
      ("nba", "xxxxxxxxx"),
    ),3)
    val partRdd: RDD[(String, String)] = rdd.partitionBy(new MyPartitioner)

    partRdd.saveAsTextFile("output")
    sc.stop()
  }

  /**
   * 自定义分区器
   * 1. 继承Partitioner
   * 2. 重写方法
   */
  class MyPartitioner extends Partitioner{
    //分区数量
    override def numPartitions: Int = 3

    //根据数据的key值返回数据所在的分区索引(从0开始)
    override def getPartition(key: Any): Int = {
      key match {
        case "nba" => 0
        case "wnba" => 1
        case _ => 2
      }
    }
  }
}
