package com.itguigu.wc

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.wc
 * Version: 1.0
 * Created by ljy on 2021-11-27 21:34
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2021-11-27 21:34
 * @Version: 1.0
 * @Description:字符统计
 */
object WordCount {
  def main(args: Array[String]): Unit = {
    //TODO 建立和spark框架的连接
    val conf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext()

    //TODO 执行业务
    sc.stop()
  }
}
