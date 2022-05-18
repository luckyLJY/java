package com.itguigu.core.req

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.req
 * Version: 1.0
 * Created by ljy on 2022-5-1 13:18
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-5-1 13:18
 * @Version: 1.0
 * @Description:
 */
object Spark05_Req2_HotCategoryTop10SessionAnalysis {
  def main(args: Array[String]): Unit = {

    // TODO : Top10热门品类
    val sparConf = new SparkConf().setMaster("local[*]").setAppName("HotCategoryTop10Analysis")
    val sc = new SparkContext(sparConf)

    val actionRDD = sc.textFile("data/user_visit_action.txt")
    actionRDD.cache()
    val top10Ids: Array[String] = top10Category(actionRDD)

    //1. 过滤原始数据，保留点击和前10品类ID
    val filterActon: RDD[String] = actionRDD.filter(
      action => {
        val datas: Array[String] = action.split("_")
        if (datas(6) != "-1") {
          top10Ids.contains(datas(6))
        } else {
          false
        }
      }
    )

    //2. 根据品类ID和sessionid进行点击量的统计
    val reduceRDD: RDD[((String, String), Int)] = filterActon.map(
      action => {
        val datas: Array[String] = action.split("_")
        ((datas(6), datas(2)), 1)
      }
    ).reduceByKey(_ + _)

    //3. 将统计的结果进行结构的转换
    // (（ 品类ID，sessionId ）,sum) => ( 品类ID，（sessionId, sum） )
    val mapRDD= reduceRDD.map {
      case ((cid, sid), sum) => {
        (cid, (sid, sum))
      }
    }

    //4. 相同的品类进行分组
    val groupRDD: RDD[(String, Iterable[(String, Int)])] = mapRDD.groupByKey()


    // 5. 将分组后的数据进行点击量的排序，取前10名
    val resultRDD: RDD[(String, List[(String, Int)])] = groupRDD.mapValues(
      iter => {
        iter.toList.sortBy(_._2)(Ordering.Int.reverse).take(10)
      }
    )

    resultRDD.collect().foreach(println)

    sc.stop()
  }

  def top10Category(actionRDD:RDD[String]) = {

    val flatRDD: RDD[(String, (Int, Int, Int))] = actionRDD.flatMap(
      action => {
        val datas: Array[String] = action.split("_")
        if (datas(6) != "-1") {
          //点击场合
          List((datas(6), (1, 0, 0)))
        } else if (datas(8) != "null") {
          //下单场合
          val ids: Array[String] = datas(8).split(",")
          ids.map(id => (id, (0, 1, 0)))
        } else if (datas(10) != "null") {
          //支付场合
          val ids: Array[String] = datas(10).split(",")
          ids.map(id => (id, (0, 0, 1)))
        } else {
          Nil
        }
      }
    )


    val analysisRDD = flatRDD.reduceByKey(
      (t1, t2) => {
        ( t1._1+t2._1, t1._2 + t2._2, t1._3 + t2._3 )
      }
    )

    analysisRDD.sortBy(_._2, false).take(10).map(_._1)
  }
}
