package com.itguigu.core.rdd.builder

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.builder
 * Version: 1.0
 * Created by ljy on 2021-12-30 11:42
 */

/**
 * @ClassName Spark01_RDD_Memory
 * @Author: ljy on 2021-12-30 11:42
 * @Version: 1.0
 * @Description：
 * TODO:从集合（内存）中创建 RDD
 * 将内存中的数据
 * 使用makeRDD及parallelize创建RDD
 * 然后计算
 */
object Spark01_RDD_Memory {
  def main(args: Array[String]): Unit = {
    //TODO 准备环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    //TODO 创建内存数据作为输入
    val seq: Seq[Int] = Seq[Int](1, 2, 3, 4)

    //TODO 通过parallelize：并行创建RDD
    //val rdd: RDD[Int] = sc.parallelize(seq)
    // makeRDD方法在底层实现时其实就是调用了rdd对象的parallelize方法。
    val rdd: RDD[Int] = sc.makeRDD(seq)

    rdd.collect().foreach(println)

    // TODO 关闭环境
    sc.stop()
  }
}
