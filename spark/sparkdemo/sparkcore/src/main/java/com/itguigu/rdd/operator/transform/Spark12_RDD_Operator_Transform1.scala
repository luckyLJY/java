package com.itguigu.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2022-1-5 10:43
 */

/**
 * @ClassName Spark12_RDD_Operator_Transform1
 * @Author: ljy on 2022-1-5 10:43
 * @Version: 1.0
 * @Description:
 * 根据指定的规则排序
 */
object Spark12_RDD_Operator_Transform1 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // TODO 算子 - sortBy
    val rdd = sc.makeRDD(List(("1", 1), ("11", 2), ("2", 3)), 2)
    //rdd.sortBy(t=>t._1)
    // sortBy方法可以根据指定的规则对数据源中的数据进行排序，默认为升序，第二个参数可以改变排序的方式
    // sortBy默认情况下，不会改变分区。但是中间存在shuffle操作
    val newRDD: RDD[(String, Int)] = rdd.sortBy(t => t._1.toInt, false)

    newRDD.collect().foreach(println)

    sc.stop()
  }
}
