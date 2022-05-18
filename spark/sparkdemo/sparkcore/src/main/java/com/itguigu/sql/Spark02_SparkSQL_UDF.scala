package com.itguigu.sql

import com.itguigu.sql.Spark01_SparkSQL_Basic.User
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
 * Project: sparkdemo
 * Package: com.itguigu.sql
 * Version: 1.0
 * Created by ljy on 2022-5-15 20:36
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-5-15 20:36
 * @Version: 1.0
 * @Description:
 */
object Spark02_SparkSQL_UDF {
  def main(args: Array[String]): Unit = {

    //TODO: 创建SparkSQL的运行环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("sparkSQL")
    val spark: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._
    //TODO: 执行逻辑操作

    val df: DataFrame = spark.read.json("data/user.json")
    df.createOrReplaceTempView("user")

    spark.udf.register("prefixName",(name:String) => {
      "Name:" + name
    })

    spark.sql("select age, prefixName(username) from user").show


    //TODO: 关闭环境
    spark.close()
  }
}
