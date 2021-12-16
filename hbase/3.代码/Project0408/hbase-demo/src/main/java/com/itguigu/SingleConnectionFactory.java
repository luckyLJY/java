package com.itguigu;/**
 * Project: Project0408
 * Package: com.itguigu
 * Version: 1.0
 * Created by ljy on 2021-12-16 17:47
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

/**
 * @ClassName SingleConnectionFactory
 * @Author: ljy on 2021-12-16 17:47
 * @Version: 1.0
 * @Description:hbase单例工程连接
 */
public class SingleConnectionFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleConnectionFactory.class);

    private volatile static Connection connection;

    private SingleConnectionFactory() {}

    public static Connection getConnection(Configuration configuration) {
        if (connection == null) {
            synchronized (SingleConnectionFactory.class) {
                if (connection == null) {
                    try {
                        connection = ConnectionFactory.createConnection(configuration);
                        LOGGER.info("the connection of HBase is created successfully.");
                    } catch (IOException e) {
                        LOGGER.error("the connection of HBase is created failed.");
                       // throw new HBaseSdkConnectionException(e);
                    }
                }
            }
        }
        return connection;
    }
}

