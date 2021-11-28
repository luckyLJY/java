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
 * @ClassName TimeInterceptor
 * @Author: ljy on 2021-11-11 15:51
 * @Version: 1.0
 * @Description：添加时间戳
 */
public class TimeInterceptor implements ProducerInterceptor<String, String> {

    public void configure(Map<String, ?> configs) {

    }

    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {

        //创建一个record,把时间戳写入消息体的最前面
        return new ProducerRecord<String, String>(record.topic(), record.partition(),
                record.timestamp(), record.key(), System.currentTimeMillis()+","
                + record.value().toString());
    }

    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

    }

    public void close() {

    }


}
