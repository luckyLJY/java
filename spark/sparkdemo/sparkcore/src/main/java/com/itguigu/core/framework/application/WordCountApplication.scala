package com.itguigu.core.framework.application

import com.itguigu.core.framework.common.TApplication
import com.itguigu.core.framework.controller.WordCountController

/**
 * Project: sparkdemo
 * Package: com.itguigu.framework.application
 * Version: 1.0
 * Created by ljy on 2022-5-4 17:00
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-5-4 17:00
 * @Version: 1.0
 * @Description:
 */
object WordCountApplication extends App with TApplication{

  //启动应用程序
  start(){
    val controller =  new WordCountController()
    controller.dispatch()
  }
}
