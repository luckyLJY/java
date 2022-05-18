package com.itguigu.core.acc

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

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
object Spark05_Bc {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("Acc")
    val sc = new SparkContext(sparkConf)

    val rdd1: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("c", 3)
    ))

//    val rdd2: RDD[(String, Int)] = sc.makeRDD(List(
//      ("a", 4), ("b", 5), ("c", 6)
//    ))

    //join会导致数据量集合增长，并且会影响shuffle的性能，不推荐使用
//    val joinRDD: RDD[(String, (Int, Int))] = rdd1.join(rdd2)
//    joinRDD.collect().foreach(println)
    // (a, 1),    (b, 2),    (c, 3)
    // (a, (1,4)),(b, (2,5)),(c, (3,6))


    val map = mutable.Map(("a", 4),("b", 5),("c", 6))

    rdd1.map{
      case (w, c) => {
        val l: Int = map.getOrElse(w, 0)
        (w,(c,l))
      }
    }.collect().foreach(println)

    sc.stop()
  }

}
