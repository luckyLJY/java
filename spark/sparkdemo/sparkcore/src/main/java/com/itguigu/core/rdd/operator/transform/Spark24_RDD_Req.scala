package com.itguigu.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.transform
 * Version: 1.0
 * Created by ljy on 2022-1-6 14:36
 */

/**
 * @ClassName Spark24_RDD_Req
 * @Author: ljy on 2022-1-6 14:36
 * @Version: 1.0
 * @Description:
 * 统计出每一个省份每个广告被点击数量排行的 Top3
 */
object Spark24_RDD_Req {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // TODO 案例实操

    // 1. 获取原始数据：时间戳，省份，城市，用户，广告
    val dataRDD = sc.textFile("data/agent.log")

    // 2. 将原始数据进行结果转换.方便统计
    //  时间戳，省份，城市，用户，广告
    // =>
    //  （(省份，广告)，1）
    val mapRDD: RDD[((String, String), Int)] = dataRDD.map(
      line => {
        val datas: Array[String] = line.split(" ")
        ((datas(1), datas(4)), 1)
      }
    )

    //3. 将转换机构后的数据，进行分组聚合
    //((省份，广告)，1) =>((省份，广告)，sum)
    val reduceRDD: RDD[((String, String), Int)] = mapRDD.reduceByKey(_ + _)

    //4. 将聚合后的结果进行结构转换
    // ((省份，广告)，sum)=>(省份，（广告，sum）)
    val newMapRDD: RDD[(String, (String, Int))] = reduceRDD.map {
      case ((prv, ad), sum) => {
        (prv, (ad, sum))
      }
    }

    //5. 将转换后的数据根据省份进行分组
    // （省份，[(广告A，sumA),(广告B，sumB)]）
    val groupRDD: RDD[(String, Iterable[(String, Int)])] = newMapRDD.groupByKey()

    //6. 将分组后的数据组内排序，取前三名
    val resultRDD: RDD[(String, List[(String, Int)])] = groupRDD.mapValues(
      iter => {
        iter.toList.sortBy(_._2)(Ordering.Int.reverse).take(3)
      }
    )

    //7.采集数据打印在控制台
    resultRDD.collect().foreach(println)
    sc.stop()
  }
}
