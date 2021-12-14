package com.itguigu;/**
 * Project: Project0408
 * Package: com.itguigu
 * Version: 1.0
 * Created by ljy on 2021-12-14 15:07
 */

/**
 * @ClassName HBasePoolUtils
 * @Author: ljy on 2021-12-14 15:07
 * @Version: 1.0
 * @Description：hbase的线程池
 */
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;


public class HBasePoolUtils {
    private static Configuration conf = null;
    // 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
    private static ExecutorService executor = null;
    private static Connection conn = null;

    static {
        try {
            conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", "hadoop-80-01,hadoop-80-01,hadoop-80-01,hadoop-80-01,hadoop-80-01");
            conf.set("hbase.zookeeper.property.clientPort", "2181");
            conf.set("hbase.defaults.for.version.skip", "true");
            executor = Executors.newFixedThreadPool(20);
            conn = ConnectionFactory.createConnection(conf, executor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn() {
        return conn;
    }
    /**
     * 查询操作
     * @tableName 表名
     * @index 索引
     * @author zhouzp
     * @date 2019年1月8日
     */
    @SuppressWarnings({ "unchecked"})
    public static Map<String, Object> queryData(String tableName, String index) {
        Table table = null;
        try {
            table = conn.getTable(TableName.valueOf(tableName));
            //Get get = new Get(MD5Util.encodeMD5(index).getBytes());
            Get get = new Get(index.getBytes());
            Result rst = table.get(get);
            byte[] value = rst.getValue("fbase".getBytes(), "data".getBytes());
            if (value != null && value.length > 0) {
                return null; //(Map<String, Object>) JsonUtil.toObject(new String(value), Map.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (table != null)
                    table.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}