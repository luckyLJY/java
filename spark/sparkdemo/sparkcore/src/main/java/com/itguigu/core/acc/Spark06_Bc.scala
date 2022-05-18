package com.itguigu.core.acc

import org.apache.spark.broadcast.Broadcast
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
object Spark06_Bc {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("Acc")
    val sc = new SparkContext(sparkConf)

    /*val rdd1 = sc.makeRDD(List(
      ("a", 1),("b", 2),("c", 3)
    ))
    val map = mutable.Map(("a", 4),("b", 5),("c", 6))

    //封装广播变量
    val bc: Broadcast[mutable.Map[String, Int]] = sc.broadcast(map)

    rdd1.map{
      case (w,c) =>{
        //方法广播变量
        val l: Int = bc.value.getOrElse(w, 0)
        (w,(c,l))
      }
    }.collect().foreach(println)
    */
    val rdd1 = sc.makeRDD(List( ("a",1), ("b", 2), ("c", 3), ("d", 4) ),4)
    val list = List( ("a",4), ("b", 5), ("c", 6), ("d", 7) )
    //声明广播变量
    val broadcast: Broadcast[List[(String, Int)]] = sc.broadcast(list)
    val resultRDD: RDD[(String, (Int, Int))] = rdd1.map {
      case (key, num) => {
        var num2 = 0
        //使用广播
        for ((k, v) <- broadcast.value) {
          if (k == key) {
            num2 = v
          }
        }
        (key, (num, num2))
      }
    }

    resultRDD.collect().foreach(println)
    sc.stop()
  }

}
