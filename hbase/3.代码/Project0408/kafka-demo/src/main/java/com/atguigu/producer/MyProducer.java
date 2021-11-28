package com.atguigu.producer;/**
 * Project: Project0408
 * Package: com.atguigu.producer
 * Version: 1.0
 * Created by ljy on 2021-11-10 16:26
 */

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @ClassName MyProducer
 * @Author: ljy on 2021-11-10 16:26
 * @Version: 1.0
 * @Description： 不带返回信息的生产者带返回信息的生产者
 */
public class MyProducer {
    public static void main(String[] args) throws Exception {

        //1. 创建kafka生产者的配置信息
        Properties props = new Properties();

        //2.指定连接的kafka的集群
        //props.put("bootstrap.servers","192.168.75.100:9092");
        //@Version:1.1 ProducerConfig配置参数类
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.75.100:9092");

        //3.ACK应答级别
        props.put("acks","all");

        //4.重试次数
        props.put("retries",1);

        //5.批次大小16k
        props.put("batch.size",16384);

        //6.等待时间
        props.put("linger.ms",1);

        //7.RecordAccumulator 32M缓冲区大小
        props.put("buffer.memory", 33554432);

        //8.key, value的序列化类
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        //9.创建生成者对象
        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        //10.发送数据
        for (int i=0;i<10;i++){
            producer.send(new ProducerRecord<String, String>("first","aa--"+i));
            //Version:1.3   producer.send(new ProducerRecord<String, String>("first","aa--"+i)).get()为同步发送
        }

        //11.关闭资源
        producer.close();
    }
}
