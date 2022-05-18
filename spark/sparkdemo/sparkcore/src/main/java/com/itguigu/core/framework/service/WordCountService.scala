package com.itguigu.core.framework.service

import com.itguigu.core.framework.common.TService
import com.itguigu.core.framework.dao.WordCountDao
import org.apache.spark.rdd.RDD

/**
 * Project: sparkdemo
 * Package: com.itguigu.framework.service
 * Version: 1.0
 * Created by ljy on 2022-5-4 16:52
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-5-4 16:52
 * @Version: 1.0
 * @Description:
 * 服务层
 */
class WordCountService extends TService{

  private val wordCountDao = new WordCountDao()

  //数据分析
  override def dataAnalysis() = {
    val lines: RDD[String] = wordCountDao.readFile("data/word.txt")
    val words: RDD[String] = lines.flatMap(_.split(" "))
    val wordToOne: RDD[(String, Int)] = words.map(word => (word, 1))
    val wordToSum: RDD[(String, Int)] = wordToOne.reduceByKey(_ + _)
    val array: Array[(String, Int)] = wordToSum.collect()
    array
  }
}
