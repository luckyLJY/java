package com.guigu.apitest.tableapi;/**
 * Project: FlinkTourtourial
 * Package: com.guigu.apitest.tableapi
 * Version: 1.0
 * Created by ljy on 2021-10-24 21:39
 */

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.descriptors.Csv;
import org.apache.flink.table.descriptors.Kafka;
import org.apache.flink.table.descriptors.Schema;

/**
 * @ClassName TableTest4_KafkaPipeLine
 * @Author: ljy on 2021-10-24 21:39
 * @Version: 1.0
 * @Description
 */
public class TableTest4_KafkaPipeLine {
    public static void main(String[] args) throws Exception {
        //1、 创建环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        //2、 连接kafka
        tableEnv.connect(new Kafka()
                .version("0.11")
                .topic("sensor")
                .property("zookeeper.connect","localhost:2181")
                .property("bootstrap.servers","localhost:9092")
        )
                .withFormat(new Csv())
                .withSchema(new Schema()
                        .field("id", DataTypes.STRING())
                        .field("timestamp", DataTypes.BIGINT())
                        .field("temp", DataTypes.DOUBLE())
                )
                .createTemporaryTable("inputTable");
        // 3. 查询转换
        // 简单转换
        Table sensorTable = tableEnv.from("inputTable");
        Table resultTable = sensorTable.select("id, temp")
                .filter("id === 'sensor_6'");

        // 聚合统计
        Table aggTable = sensorTable.groupBy("id")
                .select("id, id.count as count, temp.avg as avgTemp");

        // 4. 建立kafka连接，输出到不同的topic下
        tableEnv.connect(new Kafka()
                .version("0.11")
                .topic("sinktest")
                .property("zookeeper.connect", "localhost:2181")
                .property("bootstrap.servers", "localhost:9092")
        )
                .withFormat(new Csv())
                .withSchema(new Schema()
                                .field("id", DataTypes.STRING())
//                        .field("timestamp", DataTypes.BIGINT())
                                .field("temp", DataTypes.DOUBLE())
                )
                .createTemporaryTable("outputTable");

        resultTable.insertInto("outputTable");

        env.execute();
    }
}
