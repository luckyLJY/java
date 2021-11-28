package com.atguigu.producer;/**
 * Project: Project0408
 * Package: com.atguigu.producer
 * Version: 1.0
 * Created by ljy on 2021-11-10 16:26
 */

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * @ClassName CallBackProducer
 * @Author: ljy on 2021-11-10 16:26
 * @Version: 1.0
 * @Description 带返回信息的生产者
 */
public class CallBackProducer {
    public static void main(String[] args) throws Exception {

        //1. 创建kafka生产者的配置信息
        Properties props = new Properties();

        //2.指定连接的kafka的集群
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.75.100:9092");

        //3.序列化
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        //4.创建生成者对象
        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        //5.发送数据
        for (int i=0;i<10;i++){
            Future<RecordMetadata> first = producer.send(
                    //指定了分区，及key;
                    //Version:1.1 key,value
                    new ProducerRecord<String, String>("first", 0,"atiguigu","atiguigu--" + i),

                    new Callback() {

                        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                            if (e == null) {
                                System.out.println(recordMetadata.partition() + "--" + recordMetadata.offset());
                            }
                        }
                    });
        }

        //6.关闭资源
        producer.close();
    }
}
