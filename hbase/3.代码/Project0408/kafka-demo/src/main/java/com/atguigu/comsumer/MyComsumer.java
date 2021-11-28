package com.atguigu.comsumer;/**
 * Project: Project0408
 * Package: com.atguigu.comsumer
 * Version: 1.0
 * Created by ljy on 2021-11-11 14:32
 */

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;


import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName MyComsumer
 * @Author: ljy on 2021-11-11 14:32
 * @Version: 1.0
 * @Version: 1.1 如何消费已存在的之前数据--from-beginning
 * @Version: 1.2 自动提交为false:重复消费之前的消费数据 1.10条数据，有发了10条数据；2. 1+发的10条数据
 * @Version: 1.3 手动提交
 *              同步commitSync  提交时间短：缺数据，时间长：数据重复
 *              异步commitAsync 提交时间短：缺数据，时间长：数据重复
 *            解决：自定义存储
 * @Description: 消费者
 */
public class MyComsumer {
    public static void main(String[] args) {

        //消费者配置
        Properties props = new Properties();
        //配置集群新
        props.put("bootstrap.servers", "192.168.75.100:9092");
        //消费者组
       // props.put("group.id", "test");
        //Version:1.1 修改消费者组
        props.put("group.id", "bigdata");

        //自动提交
        //props.put("enable.auto.commit", "true");
        //Version:1.2 自动提交为：false
        //Version:1.3
        props.put("enable.auto.commit", "false");
        //提交延迟
        //Version:1.2 自动提交为：true时生效
        //Version:1.3
        ///props.put("auto.commit.interval.ms", "1000");

        //反序列化
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");

        //Version:1.1: 重置消费者的offset
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        //创建Consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        //创建主题
        consumer.subscribe(Arrays.asList("first"));
        while (true) {
            //拿取数据延迟
            ConsumerRecords<String, String> records =
                    consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());

            //Version1.3 1.同步提交，当前线程会阻塞直到 offset 提交成功
            consumer.commitSync();

            //Version1.3 2.异步提交
            consumer.commitAsync(new OffsetCommitCallback() {

                public void onComplete(Map<TopicPartition,
                                        OffsetAndMetadata> offsets, Exception exception) {
                    if (exception != null) {
                        System.err.println("Commit failed for" +
                                offsets);
                    }
                }
            });

        }
    }
}

