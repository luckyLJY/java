package com.itguigu.core.acc

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.acc
 * Version: 1.0
 * Created by ljy on 2022-4-26 20:48
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-4-26 20:48
 * @Version: 1.0
 * @Description:
 */
object Spark01_Acc {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("Acc")
    val sc = new SparkContext(sparkConf)

    //reduce：分区内计算，分区间计算
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    //val i: Int = rdd.reduce(_ + _)
    //println(i)

    var sum =0
    rdd.foreach(
      {
        num =>{
          sum += num
        }
      }
    )
    println("sum = "+ sum)
    sc.stop()
  }

}
