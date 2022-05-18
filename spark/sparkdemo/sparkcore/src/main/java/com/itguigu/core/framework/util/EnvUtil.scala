package com.itguigu.core.framework.util

import org.apache.spark.SparkContext

/**
 * Project: sparkdemo
 * Package: com.itguigu.framework.util
 * Version: 1.0
 * Created by ljy on 2022-5-4 16:40
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-5-4 16:40
 * @Version: 1.0
 * @Description:
 */
object EnvUtil {

  private val scLocal = new ThreadLocal[SparkContext]

  def put(sc:SparkContext) ={
    scLocal.set(sc)
  }

  def take(): SparkContext ={
    scLocal.get()
  }

  def clear(): Unit = {
    scLocal.remove()
  }
}
