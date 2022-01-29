package com.itguigu.rdd.operator.action

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.action
 * Version: 1.0
 * Created by ljy on 2022-1-29 8:49
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-1-29 8:49
 * @Version: 1.0
 * @Description:
 * collect行动算子
 */
object Spark01_RDD_Operator_Action {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

    /**
     * TODO: 行动算子
     *  所谓行动算子，其实就是触发作业(Job)执行的方法
     *  底层代码调用的是环境对象的runJob方法
     *  底层代码中会创建ActiveJob,并提交执行。
     */
    rdd.collect()

    rdd.foreach(println)

    sc.stop()
  }
}
