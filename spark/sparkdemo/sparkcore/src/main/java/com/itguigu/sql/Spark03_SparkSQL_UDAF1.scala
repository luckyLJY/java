package com.itguigu.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.{Aggregator, MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, LongType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Encoder, Encoders, Row, SparkSession, functions}

/**
 * Project: sparkdemo
 * Package: com.itguigu.sql
 * Version: 1.0
 * Created by ljy on 2022-5-15 21:06
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-5-15 21:06
 * @Version: 1.0
 * @Description:
 */
object Spark03_SparkSQL_UDAF1 {
  def main(args: Array[String]): Unit = {
    //TODO 创建sparkSQL环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("sparkSQL")
    val spark: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()

    //TODO 逻辑运算
    val df: DataFrame = spark.read.json("data/user.json")
    df.createOrReplaceTempView("user")

    spark.udf.register("ageAvg",functions.udaf(new MyAvgUDAF()))

    spark.sql("select ageAvg(age) from user").show

    //TODO 关闭环境
    spark.close()

  }

  /**
   * 自定义聚合函数类：计算年龄的平均值
   * 1.org.apache.spark.sql.expressions.Aggregator,定义泛型
   *    IN:输入的数据类型 Long
   *    BUF:缓冲区的数据类型 Buff
   *    OUT:输出的数据类型 Long
   *
   * 2. 重新方法(6)
   */
  case class Buff(var total:Long,var count:Long)
  class MyAvgUDAF extends Aggregator[Long, Buff, Long]{
    //z & zero :初始值或零值
    override def zero: Buff = {
      Buff(0L,0L)
    }

    //根据输入的数据更新缓存区的数据
    override def reduce(buff: Buff, in: Long): Buff = {
      buff.total = buff.total + in
      buff.count = buff.count +1
      buff
    }

    //合并缓存区
    override def merge(buff1: Buff, buff2: Buff): Buff = {

      buff1.total = buff1.total+buff2.total
      buff1.count = buff1.count + buff2.count
      buff1
    }

    override def finish(buff: Buff): Long = {
      buff.total/buff.count
    }

    override def bufferEncoder: Encoder[Buff] = Encoders.product

    override def outputEncoder: Encoder[Long] = Encoders.scalaLong
  }
}
