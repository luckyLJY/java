package com.itguigu;/**
 * Project: Project0408
 * Package: com.itguigu
 * Version: 1.0
 * Created by ljy on 2021-12-16 17:49
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MultipleConnectionFactory
 * @Author: ljy on 2021-12-16 17:49
 * @Version: 1.0
 * @Description：多例连接方法
 */
public class MultipleConnectionFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(MultipleConnectionFactory.class);

    private volatile static Map<String, Connection> connectionMap;

    private MultipleConnectionFactory() {

    }

    public static Connection getConnection(Configuration configuration) {
        String cluster = configuration.get(HConstants.ZOOKEEPER_QUORUM);

        if (connectionMap == null || !connectionMap.containsKey(cluster)) {
            synchronized (MultipleConnectionFactory.class) {
                if (connectionMap == null || !connectionMap.containsKey(cluster)) {
                    try {
                        if (connectionMap == null) {
                            connectionMap = new HashMap<String, Connection>(2);
                        }
                        if (!connectionMap.containsKey(cluster)) {
                            Connection connection = ConnectionFactory.createConnection(configuration);
                            LOGGER.info("the connection of HBase cluster [{}] is created successfully.", cluster);
                            connectionMap.put(cluster, connection);
                        }
                    } catch (IOException e) {
                        LOGGER.error("the connection of HBase is created failed.");
                      //  throw new HBaseSdkConnectionException(e);
                    }
                }
            }
        }
        return connectionMap.get(cluster);
    }
}

