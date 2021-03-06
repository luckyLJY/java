## 数据推送

- ​    需要设置批量数据的属性：isBatchListener;
- 逐条消费时需要配置list，需要配置内

#### 消息操作命令

```powershell
Kafka命令
--创建topic
    ./kafka-topics.sh --create --boostrap-server localhost:9092 --replication-factor 1 --patitions 1 --topic test
        --create： 指定创建topic动作
        --topic：指定新建topic的名称
        --zookeeper： 指定kafka连接zk的连接url，该值和server.properties文件中的配置项{zookeeper.connect}一样
        --config：指定当前topic上有效的参数值，
        --partitions：指定当前创建的kafka分区数量，默认为1个
        --replication-factor：指定每个分区的复制因子个数，默认1个

```

#### 管理

```powershell
## 创建topic（4个分区，2个副本）
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 2 --partitions 4 --topic test

### kafka版本 >= 2.2
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test

## 分区扩容
### kafka版本 < 2.2
bin/kafka-topics.sh --zookeeper localhost:2181 --alter --topic topic1 --partitions 2

### kafka版本 >= 2.2
bin/kafka-topics.sh --bootstrap-server broker_host:port --alter --topic topic1 --partitions 2

## 删除topic
bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic test

## 修改my-group组mytopic主题的偏移量
bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my-group --topic mytopic --rest-offsets --shift-by -10000 --execute
```

#### 查询

```powershell
## 查询集群描述
bin/kafka-topics.sh --describe --zookeeper 127.0.0.1:2181

## 查询集群描述（新）
bin/kafka-topics.sh --bootstrap-server localhost:9092 --topic foo --describe

## topic列表查询
bin/kafka-topics.sh --zookeeper 127.0.0.1:2181 --list

## topic列表查询（支持0.9版本+）
bin/kafka-topics.sh --list --bootstrap-server localhost:9092

## 消费者列表查询（存储在zk中的）
bin/kafka-consumer-groups.sh --zookeeper localhost:2181 --list

## 消费者列表查询（支持0.9版本+）
bin/kafka-consumer-groups.sh --new-consumer --bootstrap-server localhost:9092 --list

## 消费者列表查询（支持0.10版本+）
bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list

## 显示某个消费组的消费详情（仅支持offset存储在zookeeper上的）
bin/kafka-run-class.sh kafka.tools.ConsumerOffsetChecker --zookeeper localhost:2181 --group test

## 显示某个消费组的消费详情（0.9版本 - 0.10.1.0 之前）
bin/kafka-consumer-groups.sh --new-consumer --bootstrap-server localhost:9092 --describe --group test-consumer-group

## 显示某个消费组的消费详情（0.10.1.0版本+）
bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-group

```

#### 发送和消费

```powershell
## 生产者
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test

## 消费者
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test

## 生产者（支持0.9版本+）
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test --producer.config config/producer.properties

## 消费者（支持0.9版本+）
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --new-consumer --from-beginning --consumer.config config/consumer.properties

## 消费者（最新）
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning --consumer.config config/consumer.properties


## kafka-verifiable-consumer.sh（消费者事件，例如：offset提交等）
bin/kafka-verifiable-consumer.sh --broker-list localhost:9092 --topic test --group-id groupName

## 高级点的用法
bin/kafka-simple-consumer-shell.sh --brist localhost:9092 --topic test --partition 0 --offset 1234  --max-messages 10

```

#### 切换leader

```powershell
# kafka版本 <= 2.4
> bin/kafka-preferred-replica-election.sh --zookeeper zk_host:port/chroot

# kafka新版本
> bin/kafka-preferred-replica-election.sh --bootstrap-server broker_host:port

```

#### kafka自带压测命令

```powershell
bin/kafka-producer-perf-test.sh --topic test --num-records 100 --record-size 1 --throughput 100  --producer-props bootstrap.servers=localhost:9092

```

#### kafka持续发送消息

```powershell
持续发送消息到指定的topic中，且每条发送的消息都会有响应信息：
kafka-verifiable-producer.sh --broker-list $(hostname -i):9092 --topic test --max-messages 100000

```

#### zookeeper-shell.sh

```powershell
如果kafka集群的zk配置了chroot路径，那么需要加上/path。
bin/zookeeper-shell.sh localhost:2181[/path]
ls /brokers/ids
get /brokers/ids/0

```

<br/>

#### 迁移分区

##### 1.创建规则json

```powershell
cat > increase-replication-factor.json <<EOF
{"version":1, "partitions":[
{"topic":"__consumer_offsets","partition":0,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":1,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":2,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":3,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":4,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":5,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":6,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":7,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":8,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":9,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":10,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":11,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":12,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":13,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":14,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":15,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":16,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":17,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":18,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":19,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":20,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":21,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":22,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":23,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":24,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":25,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":26,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":27,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":28,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":29,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":30,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":31,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":32,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":33,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":34,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":35,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":36,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":37,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":38,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":39,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":40,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":41,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":42,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":43,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":44,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":45,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":46,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":47,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":48,"replicas":[0,1]},
{"topic":"__consumer_offsets","partition":49,"replicas":[0,1]}]
}
EOF

```

##### 2.执行

```powershell
bin/kafka-reassign-partitions.sh --zookeeper localhost:2181 --reassignment-json-file increase-replication-factor.json --execute

```

##### 3.验证

```powershell
bin/kafka-reassign-partitions.sh --zookeeper localhost:2181 --reassignment-json-file increase-replication-factor.json --verify

```
#### kafka的数据的分区
第一种分区策略：给定了分区号，直接将数据发送到指定的分区里面去；  
第二种分区策略：没有给定分区号，给定数据的key值，通过key取上hashCode进行分区；  
第三种分区策略：既没有给定分区号，也没有给定key值，直接轮循进行分区；  
第四种分区策略：自定义分区；  

    producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)));
    //kafka的第一种分区方式，如果给定了分区号，那么就直接将数据发送到指定的分区号里面去
    //producer.send(new ProducerRecord<String, String>("test",2,"helloworld",i+""));
    //kafka的第二种分区策略，没有给定分区号，给定了数据的key，那么就通过key取hashcode，将数据均匀的发送到三台机器里面去
    //注意如果实际工作当中，要通过key取上hashcode来进行分区，那么就一定要 保证key的变化，否则，数据就会全部去往一个分区里面
    producer.send(new ProducerRecord<String, String>("test",i+"",i+""));
    //kafka的第三种分区策略，既没有给定分区号，也没有给定数据的key值，那么就会按照轮循的方式进行数的发送
    producer.send(new ProducerRecord<String, String>("test",i+""));
    //kafka的第四种分区策略，自定义分区类，实现我们数据的分区

### hive
