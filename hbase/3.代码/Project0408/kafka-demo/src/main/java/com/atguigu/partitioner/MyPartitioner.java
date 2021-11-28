package com.atguigu.partitioner;/**
 * Project: Project0408
 * Package: com.atguigu.partitioner
 * Version: 1.0
 * Created by ljy on 2021-11-10 18:46
 */


import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @ClassName MyPartitioner
 * @Author: ljy on 2021-11-10 18:46
 * @Version: 1.0
 * @Description: 使用哈希值指定分区
 */
public class MyPartitioner implements Partitioner {
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {

        //使用key的hash取模可用分区
        return 1;
    }

    public void close() {

    }

    public void configure(Map<String, ?> map) {

    }
}
