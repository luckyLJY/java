package com.itguigu.core.rdd.builder

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.builder
 * Version: 1.0
 * Created by ljy on 2021-12-30 11:23
 */

/**
 * @ClassName Spark01_RDD_Memory_Par
 * @Author: ljy on 2021-12-30 11:23
 * @Version: 1.0
 * @Description:
 * TODO:从集合（内存）中创建 RDD
 * 分区处理
 */
object Spark01_RDD_Memory_Par {
  def main(args: Array[String]): Unit = {
    //TODO:准备环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    //TODO:设置分区
    sparkConf.set("spark.default.parallelism","5")
    val sc = new SparkContext(sparkConf)

    /**
     * TODO:创建RDD
     * 使用makeRDD方法
     * RDD的并行度 & 分区
     * makeRdd方法可以传递第二个参数，这个参数表示分区的数量，
     * 第二个参数可以不传递，默认使用defaultParallelism（默认并行度）
     *    scheduler.conf.getInt("spark.default.parallelism", totalCores)
     *    spark在默认情况下，从配置对象中获取配置参数：spark.default.parallelism
     *    如果获取不到，使用totalCores属性，这个属性取值为当前运行环境的最大可用核数
     * 使用
     */
      //val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4)，2) 指定分区
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

    //将处理的数据保存成分区文件
    rdd.saveAsTextFile("ouput")

    //TODO 关闭环境
    sc.stop()

  }

}
