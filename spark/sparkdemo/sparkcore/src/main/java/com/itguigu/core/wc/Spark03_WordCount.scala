package com.itguigu.core.wc

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable


/**
 * Project: sparkdemo
 * Package: com.itguigu.wc
 * Version: 1.0
 * Created by ljy on 2021-12-30 8:26
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2021-12-30 8:26
 * @Version: 1.0
 * @Description:wordCount的计算方法列举练习
 *
 */
object Spark03_WordCount {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    val sc = new SparkContext(sparkConf)


    wordCount91011(sc);


    sc.stop()
  }

  /**groupBy
   * 1. 使用makeRDD，参数：List；创建一个rdd
   * 2. 使用flatMap分词
   * 3. 使用groupby分组
   * 4. 使用mapValue转换数据结构并累加
   * @return 返回值为一个[]集合
   */
  def wordcount1(sc:SparkContext):  RDD[(String, Int)]={
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val group: RDD[(String, Iterable[String])] = words.groupBy(word => word)
    val wordCount: RDD[(String, Int)] = group.mapValues(itr => itr.size)
     wordCount
  }

  /**groupByKey
   * 1. 使用makeRDD，参数：List；创建一个rdd
   * 2. 使用flatmap分词
   * 3. 使用map转换数据结构
   * 4. 使用groupByKey进行分组，并转换数据结构
   * 5. 使用mapValue进行累加
   * @param sc
   * @return
   */
  def wordCount2(sc:SparkContext): RDD[(String,Int)]={
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordOne: RDD[(String, Int)] = words.map((_, 1))
    val group: RDD[(String, Iterable[Int])] = wordOne.groupByKey()
    val wordCount: RDD[(String, Int)] = group.mapValues(iter => iter.size)
    wordCount
  }

  /**reduceByKey
   * 1. 使用makeRDD，参数：List；创建一个rdd
   * 2. 使用flatmap分词
   * 3. 使用map转换数据结构
   * 4. 使用reduceByKey进行累加数据
   * @param sc
   * @return 返回值为一个[]集合
   */
  def wordCount3(sc:SparkContext): RDD[(String,Int)]={
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words: RDD[String] = rdd.flatMap(_.split(" "))
    val wordOne: RDD[(String, Int)] = words.map((_, 1))
    val wordCount: RDD[(String, Int)] = wordOne.reduceByKey(_ + _)
    wordCount
  }

  /**aggregateByKey
   * 1. 使用makeRDD，参数：List；创建一个rdd
   * 2. 使用flatmap分词
   * 3. 使用map转换数据结构
   * 4. 使用aggregateByKey进行累加数据
   * @param sc
   * @return 返回值为一个[]集合
   */

  def wordCount4(sc:SparkContext):RDD[(String,Int)]={
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words: RDD[String] = rdd.flatMap(_.split(" "))
    val wordOne: RDD[(String, Int)] = words.map((_, 1))
    val wordCount: RDD[(String, Int)] = wordOne.aggregateByKey(0)(_ + _, _ + _)
    wordCount
  }

  /**foldByKey
   * 1. 使用makeRDD，参数：List；创建一个rdd
   * 2. 使用flatmap分词
   * 3. 使用map转换数据结构
   * 4. 使用foldByKey聚合
   * @param sc
   * @return 返回值为一个[]集合
   */
  def wordCount5(sc:SparkContext):RDD[(String,Int)]={
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words: RDD[String] = rdd.flatMap(_.split(" "))
    val wordOne: RDD[(String, Int)] = words.map((_, 1))
    val wordCount: RDD[(String, Int)] = wordOne.foldByKey(0)(_ + _)
    wordCount
  }

  /**combineByKey
   * 1. 使用makeRDD，参数：List；创建一个rdd
   * 2. 使用flatmap分词
   * 3. 使用map转换数据结构
   * 4. 使用combineByKey聚合
   * @param sc
   * @return 返回值为一个[]集合
   */
  def wordCount6(sc:SparkContext)={
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words: RDD[String] = rdd.flatMap(_.split(" "))
    val wordOne: RDD[(String, Int)] = words.map((_, 1))
    val wordCount: RDD[(String, Int)] = wordOne.combineByKey(
      v => v,
      (x: Int, y) => x + y,
      (x: Int, y: Int) => x + y
    )
    wordCount
  }

  /**countByKey
   * 1. 使用makeRDD，参数：List；创建一个rdd
   * 2. 使用flatmap分词
   * 3. 使用map转换数据结构
   * 4. 使用countByKey聚合
   * @param sc
   * @return 返回值为一个Map集合
   */
  def wordCount7(sc:SparkContext)={
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words: RDD[String] = rdd.flatMap(_.split(" "))
    val wordOne: RDD[(String, Int)] = words.map((_, 1))
    val wordCount: collection.Map[String, Long] = wordOne.countByKey()
    wordCount
  }

  /**countByValue
   * 1. 使用makeRDD，参数：List；创建一个rdd
   * 2. 使用flatmap分词
   * 3. 使用map转换数据结构
   * 4. 使用countByKey聚合
   * @param sc
   * @return 返回值为一个Map集合
   */
  def wordCount8(sc:SparkContext)={
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words: RDD[String] = rdd.flatMap(_.split(" "))
    val wordCount: collection.Map[String, Long] = words.countByValue()
    wordCount
  }

  /**reduce,aggregate,fold
   * 1.
   * @param sc
   */
  def wordCount91011(sc:SparkContext)={
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words: RDD[String] = rdd.flatMap(_.split(" "))
    //【(word,count),(word,count)】
    //word = >Map[(word,1)]
    val mapWord = words.map(
      word => {
        mutable.Map[String, Long]((word,1))
      }
    )
    val wordCount: mutable.Map[String, Long] = mapWord.reduce(
      (map1, map2) => {
        map2.foreach {
          case (word, count) => {
            val newCount = map1.getOrElse(word, count) + count
            map1.update(word, newCount)
          }
        }
        map1
      }
    )
    print(wordCount)
  }

}
