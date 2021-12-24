package com.itguigu.wc

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.wc
 * Version: 1.0
 * Created by ljy on 2021-12-24 9:08
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2021-12-24 9:08
 * @Version: 1.0
 * @Description:单词统计
 *  流程：
 *   1.读取文件
 *   2.使用flatMap分词
 *   3.使用groupBy分组
 *   4.使用map数据转换
 *   5.使用collect读入内存在控制台打印
 *   6.关闭连接
 */
object Spark01_WordCount {
  def main(args: Array[String]): Unit = {
    //Application
    //TODO: 建立和spark框架的连接
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    
    //TODO:读取文件：获取一行行数据
    val lines:RDD[String] = sc.textFile("input/word.txt")

    //TODO:将一行数据进行拆分，形成一个个单词，扁平化：将整体拆分成个体
    val words: RDD[String] = lines.flatMap(_.split(","))

    /*
    * TODO:
    *  将数据进行分组，便于统计
    *  (hello,hello),(world,world)
    * */
    val wordGroup:RDD[(String,Iterable[String])] = words.groupBy(word => word)
    /*
    * TODO:
    *   对分组后的数据进行转换
    * (hello,hello)(world,world)
    * (hello,2),(world,2)
    * */
    val wordToCount = wordGroup.map {
      case (word, list) => {
        (word, list.size)
      }
    }

    /*
    * TODO: 将转换结果采集到控制台打印出来
    * */
    val array:Array[(String,Int)] = wordToCount.collect()
    array.foreach(println)

    //TODO:关闭连接
    sc.stop()
  }
}
