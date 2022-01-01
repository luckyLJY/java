package com.itguigu.test

import java.io.{ObjectOutputStream, OutputStream}
import java.net.Socket






/**
 * Project: sparkdemo
 * Package: com.itguigu.test
 * Version: 1.0
 * Created by ljy on 2021-12-30 10:49
 */

/**
 * @ClassName Driver
 * @Author: ljy on 2021-12-30 10:49
 * @Version: 1.0
 * @Description:服务器
 * @Version:2.0增加task
 * @Version:3.0双subtask
 */
object Driver {
  def main(args: Array[String]): Unit = {
    //连接服务器
    val client1 = new Socket("localhost",9999)
    //@Version:3.0
    val client2 = new Socket("localhost", 8888)

    val task = new Task()

    val out1: OutputStream = client1.getOutputStream
    val objOut1 = new ObjectOutputStream(out1)

    val subTask = new SubTask()
    subTask.logic = task.logic
    subTask.datas = task.datas.take(2)

    objOut1.writeObject(subTask)
    objOut1.flush()
    objOut1.close()
    client1.close()

    val out2: OutputStream = client2.getOutputStream
    val objOut2 = new ObjectOutputStream(out2)

    val subTask1 = new SubTask()
    subTask1.logic = task.logic
    subTask1.datas = task.datas.takeRight(2)

    objOut2.writeObject(subTask1)
    objOut2.flush()
    objOut2.close()
    client2.close()
    println("客户端数据发送完毕")
    //@Version:2.0
    /*val out: OutputStream = client1.getOutputStream
    val objOut = new ObjectOutputStream(out)
    val task = new Task()*/

    /*out.write(2)
    out.flush()
    out.close()
    client1.close()*/

    //@Version:2.0
    /*objOut.writeObject(task)
    objOut.flush()
    objOut.close()
    client1.close()
    println("客户端数据发送完毕")
*/


  }
}
