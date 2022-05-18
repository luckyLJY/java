package com.itguigu.core.test

/**
 * Project: sparkdemo
 * Package: com.itguigu.test
 * Version: 1.0
 * Created by ljy on 2021-12-31 21:28
 */

/**
 * @ClassName Task
 * @Author: ljy on 2021-12-31 21:28
 * @Version: 1.0
 * @Description:
 * 类比了数据结构
 */
class Task extends Serializable {
  //@Version:2.0
  /*  val datas = List(1,2,3,4)
    val logic :(Int)=>Int = _*2

  def compute()={
    datas.map(logic)
  }*/

  //@Version:3.0
  val datas = List(1,2,3,4)
  val logic:(Int)=>Int=_*2
}
