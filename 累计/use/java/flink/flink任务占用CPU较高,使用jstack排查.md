1. 启动任务，查看CPU占用较高的机器，进入到root用户，grep任务启动时的容器ID  
    ```sh
    ps -ef|grep application_容器ID|grep -v grep|grep -v "bash -c"
    ``` 
2. 打印jstack日志：
    ```sh
    jstack -l 上述使用容器查询到的带有JVM参数的进程号 > /temp/stack2.log
    ```
3. 在另一个窗口su - rcp用户，查看CPU占用最高的进程
    ```sh
    top -Hp 上述使用容器查询到的带有JVM参数的进程号
    ```
   得到占用CPU最大CPU的PID  
4. 将PID转为16进制c2b2   
5. 在/temp/stack2.log日志中搜素c2b2