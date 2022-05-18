package com.itguigu.core.rdd.builder

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.builder
 * Version: 1.0
 * Created by ljy on 2021-12-30 14:23
 */

/**
 * @ClassName Spark02_RDD_File_Par1
 * @Author: ljy on 2021-12-30 14:23
 * @Version: 1.0
 * @Description:
 * TODO 数据分区的分配
 */
object Spark02_RDD_File_Par1 {

  def main(args: Array[String]): Unit = {
    //TODO 准备环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    /**
     * TODO  创建RDD
     * TODO  数据分区的分配
     * 1. 数据以行尾单位进行读取
     *    spark读取文件，采用的是hadoop的方式读取：一行一行读取
     * 2. 数据读取时偏移量为单位，偏移量不会被重复读取
     */
    val rdd: RDD[String] = sc.textFile("data/1.txt", 2)

    rdd.saveAsTextFile("output")

    sc.stop()
  }


}
