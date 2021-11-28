package com.guigu.jedis;/**
 * Project: redisDemo
 * Package: com.guigu.jedis
 * Version: 1.0
 * Created by ljy on 2021-11-20 20:56
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @ClassName JedisDemo01
 * @Author: ljy on 2021-11-20 20:56
 * @Version: 1.0
 * @Description: jedisDemo練習
 */
public class JedisOperatorDemo {
    public static void main(String[] args) {
        //创建对象
        Jedis jedis = new Jedis("192.168.75.137",6379);
        String ping = jedis.ping();
        System.out.println(ping);
        jedis.close();
    }
    private Jedis jedis;

    @Before
    public void connect(){
         jedis = new Jedis("192.168.75.137",6379);
    }

    /**
     * key的操作
     */
    @Test
    public void keyOperater(){
        //设置值
        jedis.set("k1","v1");

        //获取所有的key
        Set<String> keys = jedis.keys("*");
        for (String key:keys){
            System.out.println(key);
        }

        //获取某个key的值
        System.out.println(jedis.get("k1"));

        //获取某个key的有效时间
        System.out.println(jedis.ttl("k1"));

        //设置某个key的有效值
        jedis.expire("k1",5);

        //判断某个值是否存在
        System.out.println(jedis.exists("k1"));
    }

    /**
     * 争对String批量操作
     */
    @Test
    public void stringBatchOpe(){
        //插入多个值
        jedis.mset("str1","v1","str2","v2");

        //获取set中的多个值
        System.out.println("获取set中的多个值:"+jedis.mget("str1","str2"));
    }

    /**
     * 操作list集合
     */
    @Test
    public void listOpe(){
        //添加值
        jedis.lpush("key1","lucy","mary","jack");

        //获取所有值
        List<String> values = jedis.lrange("key1", 0, -1);
        System.out.println("list的所有值"+values);
    }

    /**
     * set操作
     */
    @Test
    public void setDemo(){
        //添加值
        jedis.sadd("name","lucy","jack");

        //获取值
        Set<String> name = jedis.smembers("name");
        System.out.println("set的值"+name);

        //删除值更加key
        jedis.srem("name","jack");
        System.out.println("set的值"+jedis.smembers("name"));
    }

    /**
     * hash操作
     */
    @Test
    public void hashDemo(){
        //添加hash值
        jedis.hset("users", "age", "20");

        //获取hash值
        String hget = jedis.hget("users", "age");
        System.out.println("hash获取的值"+hget);

        //新建一个map使用hmset存储
        HashMap<String, String> map = new HashMap<>();
        map.put("telphone","18143776073");
        map.put("address","atguigu");
        map.put("email","abc@163.com");
        jedis.hmset("hash2",map);

        //获取多个hash值
        List<String> result = jedis.hmget("hash2", "telphone", "address");
        for (String element:result){
            System.out.println(element);
        }
    }

    /**
     * zset操作
     */
    @Test
    public void zsetDemo(){
        jedis.zadd("zset01", 100d, "z3");
        jedis.zadd("zset01", 90d, "l4");
        jedis.zadd("zset01", 80d, "w5");
        jedis.zadd("zset01", 70d, "z6");

        //获取所有值
        Set<String> zrange = jedis.zrange("zset01", 0, -1);

        for (String e : zrange) {
            System.out.println(e);
        }

    }

    @After
    public void closeConnect(){
        jedis.close();
    }
}
