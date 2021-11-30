package com.guigu.jedis;/**
 * Project: jedisDemo
 * Package: com.guigu.jedis
 * Version: 1.0
 * Created by ljy on 2021-11-30 21:40
 */

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * @ClassName
 * @Author: ljy on 2021-11-30 21:40
 * @Version: 1.0
 * @Description
 */
public class RedisClusterdDemo {
    public static void main(String[] args) {

        //创建对象
        HostAndPort hostAndPort = new HostAndPort("192.168.75.137", 6379);
        JedisCluster jedisCluster = new JedisCluster(hostAndPort);

        //进行操作
        jedisCluster.set("b1","value1");

        String value = jedisCluster.get("b1");
        System.out.println("value"+value);

        jedisCluster.close();

    }
}
