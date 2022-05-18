package com.itguigu.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2022-1-6 11:38
 */

/**
 * @ClassName Spark19_RDD_Operator_Transform
 * @Author: ljy on 2022-1-6 11:38
 * @Version: 1.0
 * @Description:
 * 最通用的对 key-value 型 rdd 进行聚集操作的聚集函数（aggregation function）。类似于
aggregate()，combineByKey()允许用户返回值的类型与输入不一致。
 */
object Spark19_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    //TODO 算子-（Key-Value类型)
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3),
      ("b", 4), ("b", 5), ("a", 6)
    ),2)

    //combineByKey:方法需要三个参数
    //第一个参数表示：将相同的Key的第一个数据进行结构的转化，实现操作
    //第二个参数表示：分区内的计算规则
    //第三个参数表示：分区间的计算规则

    val newRDD: RDD[(String, (Int, Int))] = rdd.combineByKey(
      v => (v, 1),
      (t: (Int, Int), v) => {
        (t._1 + v, t._2 + 1)
      },
      (t1: (Int, Int), t2: (Int, Int)) => {
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
