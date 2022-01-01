package com.itguigu.test

import java.io.{InputStream, ObjectInputStream}
import java.net.{ServerSocket, Socket}

/**
 * Project: sparkdemo
 * Package: com.itguigu.test
 * Version: 1.0
 * Created by ljy on 2021-12-31 21:05
 */

/**
 * @ClassName Executor
 * @Author: ljy on 2021-12-31 21:05
 * @Version: 1.0
 * @Description
 *
 * @Version:2.0
 *
 */
object Executor1 {
  def main(args: Array[String]): Unit = {

    //启动服务器，接受数据
    val serer = new ServerSocket(8888)
    println("服务器启动，等待接受数据")

    //等待客户端连接
    val client: Socket = serer.accept()
    val in: InputStream = client.getInputStream
    //@Version:2.0
    val objIn = new ObjectInputStream(in)
    /*val task: Task = objIn.readObject().asInstanceOf[Task]
    val ints: List[Int] = task.compute()
    println("计算节点结果：" + ints)
    objIn.close()
    client.close()
    serer.close()*/

    //@Version:3.0
    val task: SubTask = objIn.readObject().asInstanceOf[SubTask]
    val ints: List[Int] = task.compute()
    println("计算节点[8888]计算结果为：" + ints)
    objIn.close()
    client.close()
    serer.close()

    //@Version:1.0
    /*val i: Int = in.read()
    println("服务端接受到客户端的数据" + i)
    in.close()
    client.close()
    serer.close()*/
  }
}
