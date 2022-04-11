package com.itguigu.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2022-1-5 10:21
 */

/**
 * @ClassName Spark09_RDD_Operator_Transform
 * @Author: ljy on 2022-1-5 10:21
 * @Version: 1.0
 * @Description:
 * 去重
 */
object Spark09_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("disctinctRDD")
    val sc = new SparkContext(sparkConf)

    //map(x=>(x,null)).reduceByKey((x,_)=>x,numPartitions).map(_._1)
    //(1,null)(3,null),(3,null),(2,null),(4,null)
    //(null,null) =>null
    //(3,null) =>3

    //scala的distinct是使用了hashset
    val rdd: RDD[Int] = sc.makeRDD(List(1, 3, 3, 2, 4))
    val rdd1: RDD[Int] = rdd.distinct()
    rdd1.collect().foreach(println)

    sc.stop()
  }
}
