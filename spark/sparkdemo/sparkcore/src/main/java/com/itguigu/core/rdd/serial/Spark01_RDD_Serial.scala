package com.itguigu.core.rdd.serial

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.serial
 * Version: 1.0
 * Created by ljy on 2022-1-29 10:18
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-1-29 10:18
 * @Version: 1.0
 * @Description:
 */
object Spark01_RDD_Serial {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf()
      .setMaster("local[*]")
      .setAppName("serial Word Count")
      .set("spark.serializer","org.apache.spark.serializer.KryoSerializer")
      .registerKryoClasses(Array(classOf[Search]))

    val sc = new SparkContext(sparkConf)

    val rdd: RDD[String] = sc.makeRDD(Array("hello world", "hello spark", "hive", "atguigu"))

    val search = new Search("h")

    //search.getMatch1(rdd).collect().foreach(println)
    search.getMatch2(rdd).collect().foreach(println)
    sc.stop()
  }
  //查询对象
  //类的构造参数其实是类的属性，构造参数需要进行闭包检测，其实就等同于类进行闭包检查
  class Search(query:String) extends Serializable {
    def isMatch(s:String):Boolean={
      s.contains(this.query)
    }

    //函数序列化案例
    def getMatch1 (rdd:RDD[String]):RDD[String]={
      rdd.filter(isMatch)
    }

    //属性序列化案例
    def getMatch2(rdd:RDD[String]):RDD[String]={
      val s = query
      rdd.filter(x => x.contains(s))
    }
  }
}
