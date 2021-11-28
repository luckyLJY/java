package com.atguigu.interceptor;/**
 * Project: Project0408
 * Package: com.atguigu.interceptor
 * Version: 1.0
 * Created by ljy on 2021-11-11 15:51
 */

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @ClassName CounterInterceptor
 * @Author: ljy on 2021-11-11 15:51
 * @Version: 1.0
 * @Description: 统计消息成功或失败
 */
public class CounterInterceptor implements ProducerInterceptor<String, String> {
    private int errorCounter =0 ;
    private int successCounter = 0;

    public void configure(Map<String, ?> configs) {

    }

    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        return record;
    }

    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

        // 统计成功和失败的次数
        if (exception == null) {
            successCounter++;
        } else {
            errorCounter++;
        }

    }

    public void close() {
        // 保存结果
        System.out.println("Successful sent: " + successCounter);
        System.out.println("Failed sent: " + errorCounter);
    }


}
