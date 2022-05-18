package com.itguigu.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import java.text.SimpleDateFormat
import java.util.Date

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2022-1-5 9:56
 */

/**
 * @ClassName Spark06_RDD_Operator_Transform_Test
 * @Author: ljy on 2022-1-5 9:56
 * @Version: 1.0
 * @Description：
 * 从服务器日志数据 apache.log 中获取每个时间段访问量。
 */
object Spark06_RDD_Operator_Transform_Test {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("operatorlog")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[String] = sc.textFile("data/apache.log")
    val timeRDD: RDD[(String, Iterable[(String, Int)])] = rdd.map(
      line => {
        val datas: Array[String] = line.split(" ")
        val time: String = datas(3)
        val sdf = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss")
        val date: Date = sdf.parse(time)
        val sdf1 = new SimpleDateFormat("HH")
        val hour: String = sdf1.format(date)
        (hour, 1)
      }
    ).groupBy(_._1)
    timeRDD.map{
        case(hour,iter) =>{
        (hour,iter.size)
      }
    }.collect.foreach(println)

    sc.stop()
  }
}
