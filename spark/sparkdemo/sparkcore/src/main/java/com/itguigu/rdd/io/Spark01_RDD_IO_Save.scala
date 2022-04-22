package com.itguigu.rdd.io

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.io
 * Version: 1.0
 * Created by ljy on 2022-4-17 10:52
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-4-17 10:52
 * @Version: 1.0
 * @Description:
 */
object Spark01_RDD_IO_Save {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(sparkConf)


    val rdd = sc.makeRDD(
      List(
        ("a", 1),
        ("b", 2),
        ("c", 3)
      )
    )

    rdd.saveAsTextFile("output1")
    rdd.saveAsObjectFile("output2")
    rdd.saveAsSequenceFile("output3")

    sc.stop()
  }

}
