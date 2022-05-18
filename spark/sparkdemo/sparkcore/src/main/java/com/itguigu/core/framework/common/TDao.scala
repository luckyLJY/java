package com.itguigu.core.framework.common

import com.itguigu.core.framework.util.EnvUtil

/**
 * Project: sparkdemo
 * Package: com.itguigu.framework.common
 * Version: 1.0
 * Created by ljy on 2022-5-4 16:48
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-5-4 16:48
 * @Version: 1.0
 * @Description:
 */
trait TDao {
    def readFile(path:String) ={
      EnvUtil.take().textFile(path)
    }
}
