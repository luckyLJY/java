package com.itguigu.rdd.builder

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.builder
 * Version: 1.0
 * Created by ljy on 2021-12-30 11:57
 */

/**
 * @ClassName Spark02_RDD_File_Par
 * @Author: ljy on 2021-12-30 11:57
 * @Version: 1.0
 * @Description
 * TODO 设定分区的从外部文件创建RDD
 */
object Spark02_RDD_File_Par {

  def main(args: Array[String]): Unit = {
    //TODO 准备环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    //TODO 创建RDD
    /**
     * textFile可以将文件作为数据处理的数据源，默认也可以设定分区
     *      minPartitions:最小分区数量
     *      math.min(defaultParallelism,2)
     *  val rdd = sc.textFile("datas/1.rxt")
     *    如果不想使用默认的分区数量，可以通过第二个参数指定分区数
     *    spark读取文件，底层其实使用的就是hadoop的读取方式
     *    分区数据量的计算方式：
     *      totalSize = 7
     *      goalSize = 7 / 2 = 3(byte)
     */
    val rdd: RDD[String] = sc.textFile("input/1.txt", 2)

    //TODO 存储到output目录
    rdd.saveAsTextFile("output")

    //TODO 关闭环境
    sc.stop()
  }
}
