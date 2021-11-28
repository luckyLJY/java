package com.atguigu.interceptor;/**
 * Project: Project0408
 * Package: com.atguigu.interceptor
 * Version: 1.0
 * Created by ljy on 2021-11-11 16:01
 */


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName InterceptorProducer
 * @Author: ljy on 2021-11-11 16:01
 * @Version: 1.0
 * @Description: 拦截器生成者
 */
public class InterceptorProducer {
    public static void main(String[] args) throws Exception {
        // 1 设置配置信息
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.75.100:9092");
        props.put("acks", "all");
        props.put("retries", 3);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        //构建拦截器链
        List<String> interceptors = new ArrayList<String>();
        interceptors.add("com.atguigu.interceptor.TimeInterceptor");
        interceptors.add("com.atguigu.interceptor.CounterInterceptor");
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,interceptors);

        String topic = "first";
        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        // 3 发送消息
        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, "message" + i);
            producer.send(record);
        }
        // 4 一定要关闭 producer，这样才会调用 interceptor 的 close 方法
        producer.close();
    }


}
