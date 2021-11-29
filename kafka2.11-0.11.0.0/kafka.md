---
typora-root-url: img_kafka
---

# 第1章 kafka概述

## 1.1 定义

kafka是一个分布式的基于发布/订阅模式的消息队列，主要应用于大数据实时处理领域。

## 1.2 消息队列

### 1.2.1传统消息队列的应用场景

<img src="mq传统应用场景.png" alt="MQ传统应用场景" style="zoom:60%;" />

**使用消息队列的好处**

1. 解耦

   ​	允许你独立的扩展或修改两边的处理过程，只要确保他们遵守同样的接口约束。

2. 可恢复性

   ​	系统的一部分组件失效时，不会影响到整个系统，消息队列降低了进程间的耦合度，所以即使一个处理消息的进程挂掉，加入队列的消息仍然可以在系统恢复后被处理。

3. 缓冲

   ​	有助于控制和优化数据流经过系统的速度，解决生产消息和消费消息的处理速度不一致的情况。（生产大于消费）

4. 灵活性 & 峰值处理能力

   ​	在访问量剧增的情况下，应用仍然需要继续发挥作用，但是这样的突发流量并不常见。如果为能处理这类峰值访问为标准来投入资源随时待命无疑是巨大的浪费。使用消息队列能够使关键组件顶住突发访问压力。而不会因为突发的超负荷的请求而完全崩溃。

5. 异步通信

   ​	很多时候，用户不想也不需要立即处理异步消息。消息队列提供了异步处理机制，允许用户把一个消息放入一个消息队列，但并不立即处理它，想向队列中放入多少消息都可以，等需要处理时再处理。

### 1.2.2 消息队列的两种模式

1.  **点对点模式**（一对一，消费者主动拉取数据，消息收到后消息消除）

   ​	消息生产者生产消息发送到Queue中，然后消息从Queue中取出并且消费消息。消息被消费后，queue中不再存储。所以消息消费者不可能消费到已经被消费的消息。Queue支持多个消费者，但是对一个消息而言，只会有一个消费者可以消费。

   <img src="C:\Users\ljy\Desktop\2021-9-2\点对点.png" alt="一对一" style="zoom:60%;" />

2. **发布/订阅模式**（一对到，消费者消费数据之后不会清除消息，有时效：消费者主动，队列主动）

   ​	消息生产者（发布）将消息发布到topic中，同时有多消息消费者(订阅)消费该消息。和点对点方式不同，发布到topic的消息会被所有订阅者消费

   <img src=".\一对多.png" style="zoom:60%;" />

### 1.3  Kafka基础架构

<img src="kafka架构.png" alt="**Kafka架构**" style="zoom:60%;" />



7天  磁盘

- Producer: 消息生产者，就是向kafka broker发消息的客户端；
- Consumer: 消息消费者，向kafka broker取消息的客户端；
- Consumer Group(CG): 消费者组，由多个consumer组成，消费者组内每个消费者负责消费不同分区的数据，一个分区只能由一个组内消费者消费；消费者组之间互不影响。所有的消费者都属于某个消费者组，即消费者组是逻辑上的一个订阅者
- Broker: 一台kafka服务器就是一个broker。一个集群由多个broker组成。一个broker可以容纳多个topic.
- Topic: 可以理解为一个队列，生产者和消费者面向的都是一个topic
- Partition: 为了实现扩展性，一个非常大的topic可以分布到多个broker（即服务器）上，一个topic可以分为多个partition,每个partition是一个有序队列；
- Replica: 副本，为保证集群中的某个节点发生故障时，该节点的partition数据不丢失，且kafka任然能够继续工作，kafka提供了副本机制，一个topic的每个分区都有若干个副本，一个leader和若干个follower
- leader: 每个分区多个副本的“主”，生产者发送数据的对象，以及消费者数据的对象是leader
- follower：每个分区多个副本的"从",实时从leader中同步数据，保持和leader数据的同步。leader发生故障时，某个follower会成为新的leader.

# 第2章  Kafka快速入门

## 2.1 安装部署

### 2.1.1 集群规划

hadoop   zk   kafka

### 2.1.2 jar包下载

kafka安装包下载

### 2.1.3 集群部署

- 解压安装包

  tar	-zxvf	安装包 	-C	安装目录(/opt/moudle)

- 修改解压后的文件名称

  mv  oldname 	newname

- 在安装目录/kafka/目录下创建logs文件夹

  mkdir logs

- 修改配置文件

  cd config 	vi server.properties

  config 下的server.properties

  ```properties
  # broker的全局唯一编号，不能重复
  broker_id=0
  # 删除topic功能使能
  delete.topic.enable=true
  # kafka运行日志存放的路径 
  log.dirs=/opt/moudle/kafka/logs(数据暂存和日志)（最后为data）
  log.retention.hours=168(数据保存时间)
  # 配置连接zookeeper集群地址
  zookeeper.connect=hadoop102:2181,hadoop103:2181,hadoop104:2181
  ```

- 配置环境变量

  ​	sudo  vi 	/etc/profile

  ​	#KAFKA_HOME

  ​	export KAFKA_HOME=/export/servers/kafka_2.12-0.10.2.0

  ​	export PATH=$PATH:$KAFKA_HOME/bin

  ​	source	/etc/profile

- 分发安装包

  ​	xsync	kafka/(分发之后记得配置其他机器的环境变量)

- 分别在hadoop103和hadoop104上修改配置文件/opt/module/kafka/config/server.properties中的borker.id=1、broker.id=2（不能重复） 启动zk

- 启动集群

  依次在hadoop102、hadoop103、hadoop104节点上启动kafka

  bin/kafka-server-start.sh	-daemon config/server.properties

  群起脚本

  ```shell
  #!/bin/bash
  
  case $1 in
  "start"){
  	
  	for i in node01 node02 node03
  	do
  		echo "********************$i***************"
  		ssh $i "/export/servers/kafka_2.12-0.10.2.0/bin/kafka-server-start.sh	-daemon /export/servers/kafka_2.12-0.10.2.0/config/server.properties"
  	done
  };;
  
  
  "stop"){
  	
  	for i in node01 node02 node03
  	do
  		echo "********************$i***************"
  		ssh $i "/export/servers/kafka_2.12-0.10.2.0/bin/kafka-server-stop.sh /export/servers/kafka_2.12-0.10.2.0/config/server.properties"
  	done
  };;
  esac
  ```

  ​	chmod 777 kk.sh

  ​	脚本启动 kk.sh start 	xcall.sh jps关闭

  ## 2.2 Kafka命令行操作

  1.  查看当前服务器中的所有topic

     ​	bin/kafka-topics.sh --list	--zookeeper	hadoop102:2181	

  2.  创建topic

     ​	bin/kafka-topics.sh 	 --create  --zookeeper	hadoop102:2181 	 --topic first  --partitions 2   --relication-factor   2 --topic test

     选项说明：

     --topic 定义topic名

     --relication-factor 定义副本数

     --partitions: 定义分区数

  3.  删除topic

     ​	bin/kafka-topics.sh  --delete	--zookeeper	hadoop102:2181    --topic first

     ​	需要server.properties中设置delete.topic.enable=true否则只是标记删除

  4. 发送消息

     ​	bin/kafka-console-producer.sh --broker-list hadoop102:9092  --topic  first

  5.  消费消息

     ./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning

     会把主题中以往所有数据都读取出来

  6.  查看某个Topic的详情

     ​	bin/kafka-topics.sh 	--zookeeper	hadoop102:2181   --describe --topic frist

  7. 修改分区数

     ​	bin/kafka-topics.sh 	--zookeeper	hadoop102:2181   --alter --topic first  --partitons 6
     
     --kafka操作前的认证
     
     cd /home/app/bdap/sharakta_cl/conf/archive_cluster_auth
     
     kinit -k -t hds.keytab hds
     
     
     
     (非工作环境)
     
     --创建topic
     
     ```shell
     ./kafka-topics.sh --create --boostrap-server node01:9092 --replication-factor 1 --patitions 1 --topic test
     ```
     
     ​	--create： 指定创建topic动作
     
     ​	--topic：指定新建topic的名称
     
     ​	--zookeeper： 指定kafka连接zk的连接url，该值和server.properties文件中的配置项{zookeeper.connect}一样
     
     ​	--config：指定当前topic上有效的参数值，
     
     ​	--partitions：指定当前创建的kafka分区数量，默认为1个
     
     ​	--replication-factor：指定每个分区的复制因子个数，默认1个
     
     --查看topic
     
     ```shell
     ./kafka-topics.sh --list --boostrap-server localhost:9092
     ```
     
     --发送消息
     
     ```shell
     ./kafka-console-producer.sh --broker-list localhost:9092 -topic test
     ```
     
     --消费消息
     
     ```shell
     ./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning
     ```
     
     --删除topic
     
     ```shell
     ./kafka-topic --delete --boostrap-server localhost:9092 --topic test
     ```
     
     (工作环境)
     
     --创建消息队列
     
     ```shell
     sh  /home/app/ficlient/Kafka/kafka/bin/kafka-topics.sh --create --zookeeper 10.6.92.36:24002,10.6.92.37:24002,10.6.92.38:24002/kafka --replication-factor 3 --partitions 4 --topic test10
     ```
     
     --查看消息分区信息
     
     ```shell
     sh /home/app/ficlient/Kafka/kafka/bin/kafka-topics.sh --descibe --zookeeper 10.6.92.36:24002,10.6.92.37:24002,10.6.92.38:24002/kafka --toppic test10
     ```
     
     --发送消息
     
     ```shell
     sh  /home/app/ficlient/Kafka/kafka/bin/kafka-console-producer.sh --borker-list 10.6.92.36:24005,10.6.92.37:24005,10.6.92.38:24005/kafka --topic test10
     ```
     
     --消费消息
     
     ```shell
     sh  /home/app/ficlient/Kafka/kafka/bin/kafka-console-consumer.sh --zookeeper 10.6.92.36:24002,10.6.92.37:24002,10.6.92.38:24002/kafka --topic test10 --from-beginning
     ```
     
     --删除消息
     
     ```shell
     sh  /home/app/ficlient/Kafka/kafka/bin/kafka-topics.sh --delete --zookeeper 10.6.92.36:24002,10.6.92.37:24002,10.6.92.38:24002/kafka --topic test10
     ```
     
     
     
     
     
     
     
     
     
     

# 第3章 Kafka架构深入

## 3.1 Kafka 工作流程及文件存储机制

![](Kafka工作流程.png)

   				topic是逻辑上的概念，而patition是物理上的概念，每个partition对应一个log文件，该log文件中存储的就是producer生产的数据。Producer生产的数据会被不断追加到该log文件末端，且每条数据都有自己的offset。消费者组中的每个消费者，都会实时记录自己消费到了那个offset，以便出错恢复时，从上次的位置继续消费。

![kafka文件存储机制](kafka文件存储机制.png)

​		由于生产者生产的消息会不断追加到log文件末尾，为防止Log文件过大导致数据定位效率低下，KafKa采取分片和索引机制，将每个partition分成多个segment，每个segment对应两个文件--“.index”文件和".log"文件。这些文件位于一个文件夹下，该文件夹的命名规则为：topic名称+分区序号。例如，first这个topic有三个分区，则其对应的文件夹为first-0,first-1,first-2

![](存储文件.png)

​		index和log文件以当前segment的第一条消息的offset命名，下图为index文件和log文件的结构示意图。

![](文件存储示意图.png)

​			“index”文件存储大量索引信息，“log”文件存储大量数据，索引文件中的元数据指向对应数据文件中message的物理偏移地址。

# 第四章 实际应用补充

kafka-0.10.1.X版本之前: auto.offset.reset 的值为smallest,和,largest.(offest保存在zk中)

kafka-0.10.1.X版本之后: auto.offset.reset 的值更改为:earliest,latest,和none (offest保存在kafka的一个特殊的topic名为:__consumer_offsets里面)

## 命令

### 主题

#### 查看主题

```shell
kafka-topics.sh --zookeeper ip:port --list
```

#### 新建主题:test1

```shell
kafka-topics.sh --zookeeper ip:port --create --replication-factor 2 --partitions 2 --topic test1
```

#### 查看主题详情

```shell
kafka-topics.sh --zookeeper ip:port --describe topic tes1
```



### 生产者

#### 生产者：test1

```shell
kafka-console-producer.sh --broker--list ip:port --topic test1
```

### 消费者

### 消费消息

#### 从头消费

```shell
kafka-console-consumer.sh --topic test1 --bootstrap-server ip:port --from-beginning
```



#### 消费者组

##### 指定消费者组为group1消费-主题为test1的消息

```shell
kafka-console-consumer.sh --topic test1 --bootstrap-server ip:port --group group1
```

##### 查看kafka消费者组

```shell
kafka-consumer-groups.sh --bootstrap-server ip:port --list
```

##### 查看某个消费组的消费情况

```shell
kafka-console-groups.sh --bootstrap-server ip:port --describe --group group1
```

##### 消费消费者组的消息

```shell
kafka-consumer-groups.sh --bootstrap-server ip:port --describe -group group1
```

### 分区

#### 添加分区

```shell
kafka-topic.sh --zookeeper ip:port --alter --paritions 6 --topic test1
```

#### 查看不同分区(主题（I)的offsets

```shell
kafka-run-class.sh kafka-tools.GetOffsetShell --broker-list ip:port --topic test --time -1
```

#### 修改kafka的偏移量（增加积压100000）

```shell
kafka-consumer-groups.sh --bootstrap-server ip:port -group group1 --topic test1 --rest-offsets --shift-by -100000 --execute
```

