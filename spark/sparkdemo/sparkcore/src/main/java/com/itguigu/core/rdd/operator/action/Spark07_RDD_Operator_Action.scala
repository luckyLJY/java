package com.itguigu.core.rdd.operator.action

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project: sparkdemo
 * Package: com.itguigu.rdd.operator.action
 * Version: 1.0
 * Created by ljy on 2022-1-29 10:13
 */

/**
 * @ClassName ${CLASSNAME}
 * @Author: ljy on 2022-1-29 10:13
 * @Version: 1.0
 * @Description:
 */
object Spark07_RDD_Operator_Action {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("operator")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List[Int]())

    val user = new User()
    // SparkException: Task not serializable
    // NotSerializableException: com.atguigu.bigdata.spark.core.rdd.operator.action.Spark07_RDD_Operator_Action$User

    // RDD算子中传递的函数是会包含闭包操作，那么就会进行检测功能
    // 闭包检测
    rdd.foreach(
      num => {
        println("age = " + (user.age + num))
      }
    )

    sc.stop()

  }
  // 样例类在编译时，会自动混入序列化特质（实现可序列化接口）
  class User extends Serializable{
    var age :Int = 30
  }
}
