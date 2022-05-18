package com.itguigu.core.framework.controller

import com.itguigu.core.framework.common.TController
import com.itguigu.core.framework.service.WordCountService

/**
 * Project: sparkdemo
 * Package: com.itguigu.framework.controller
 * Version: 1.0
 * Created by ljy on 2022-5-4 16:57
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-5-4 16:57
 * @Version: 1.0
 * @Description:
 */
//控制层
class WordCountController extends TController{

  private val wordCountService = new WordCountService()

  //调度
  override def dispatch(): Unit = {
    //执行业务操作
    val array = wordCountService.dataAnalysis()
    array.foreach(println)
  }
}
