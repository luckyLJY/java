package com.itguigu.core.rdd.io

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.io
 * Version: 1.0
 * Created by ljy on 2022-4-17 10:55
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-4-17 10:55
 * @Version: 1.0
 * @Description:
 */
object Spark01_RDD_IO_Load {

  def main(args: Array[String]): Unit = {
    val sparConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(sparConf)

    val rdd = sc.textFile("output1")
    println(rdd.collect().mkString(","))

    val rdd1 = sc.objectFile[(String, Int)]("output2")
    println(rdd1.collect().mkString(","))

    val rdd2 = sc.sequenceFile[String, Int]("output3")
    println(rdd2.collect().mkString(","))

    sc.stop()
  }
}