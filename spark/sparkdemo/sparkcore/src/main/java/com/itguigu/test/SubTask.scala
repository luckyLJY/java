package com.itguigu.test

/**
 * Project: sparkdemo
 * Package: com.itguigu.test
 * Version: 1.0
 * Created by ljy on 2021-12-31 22:31
 */

/**
 * @ClassName SubTask
 * @Author: ljy on 2021-12-31 22:31
 * @Version: 1.0
 * @Description:
 * @Version:3.0子任务
 */
class SubTask extends Serializable {
  var datas:List[Int]=_
  var logic:(Int)=>Int=_

  def compute()={
    datas.map(logic)
  }
}
