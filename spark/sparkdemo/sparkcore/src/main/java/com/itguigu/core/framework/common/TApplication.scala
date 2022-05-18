package com.itguigu.core.framework.common

import com.itguigu.core.framework.util.EnvUtil
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.framework.common
 * Version: 1.0
 * Created by ljy on 2022-5-4 16:43
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-5-4 16:43
 * @Version: 1.0
 * @Description:
 */
trait TApplication {

  def start(master:String="local[*]",app:String ="Application")(op: => Unit): Unit ={
    val sparConf: SparkConf = new SparkConf().setMaster(master).setAppName(app)
    val sc = new SparkContext(sparConf)
    EnvUtil.put(sc)

    try {
      op
    }catch {
      case  ex => println(ex.getMessage)
    }

    //TODO 关闭连接
    sc.stop()
    EnvUtil.clear()
  }
}
