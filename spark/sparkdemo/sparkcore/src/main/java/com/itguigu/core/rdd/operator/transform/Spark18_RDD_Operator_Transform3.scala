package com.itguigu.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2022-1-6 11:31
 */

/**
 * @ClassName Spark18_RDD_Operator_Transform3
 * @Author: ljy on 2022-1-6 11:31
 * @Version: 1.0
 * @Description:
 * 使用aggregateByKey进行求相同key的平均值
 */
object Spark18_RDD_Operator_Transform3 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)


    // TODO 算子 - (Key - Value类型)

    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3),
      ("b", 4), ("b", 5), ("a", 6)
    ),2)
    // aggregateByKey最终的返回数据结果应该和初始值的类型保持一致
    // 获取相同key的数据的平均值 => (a,3),(b,4)
    val newRDD: RDD[(String, (Int, Int))] = rdd.aggregateByKey((0, 0))(
      (t, v) => {
        (t._1 + v, t._2 + 1)
      },
      (t1, t2) => {
        (t1._1 + t2._1, t1._2 + t2._2)
      }
    )

    val resultRDD: RDD[(String, Int)] = newRDD.mapValues {
      case (num, cnt) => {
        num / cnt
      }
    }

    resultRDD.collect().foreach(println)


    sc.stop()
  }
}
