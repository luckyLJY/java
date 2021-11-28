package com.guigu.apitest.tableapi;/**
 * Project: FlinkTourtourial
 * Package: com.guigu.apitest.tableapi
 * Version: 1.0
 * Created by ljy on 2021-10-24 16:31
 */

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.descriptors.Csv;
import org.apache.flink.table.descriptors.FileSystem;
import org.apache.flink.table.descriptors.Schema;
import org.apache.flink.types.Row;

/**
 * @ClassName TableTest2_CommonApi
 * @Author: ljy on 2021-10-24 16:31
 * @Version: 1.0
 * @Description
 */
public class TableTest2_CommonApi {
    public static void main(String[] args) throws  Exception{
        /**
         * 1. 创建环境
         * 1.1。 基于老版本的planner的流处理
         * 1.2. 基于老版本的planner的批处理
         * 1.3. 基于Blink的流处理
         * 1.4. 基于Blink的批处理
         */
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        EnvironmentSettings oldStreamSettings = EnvironmentSettings.newInstance()
                .useOldPlanner()
                .inStreamingMode()
                .build();
        StreamTableEnvironment oldStreamTableEnv = StreamTableEnvironment.create(env, oldStreamSettings);

        ExecutionEnvironment batchEnv = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment oldBatchTableEnv = BatchTableEnvironment.create(batchEnv);

        EnvironmentSettings blinkStreamSettings = EnvironmentSettings.newInstance()
                .useBlinkPlanner()
                .inStreamingMode()
                .build();
        StreamTableEnvironment blinkStreamTableEnv = StreamTableEnvironment.create(env, blinkStreamSettings);

        EnvironmentSettings blinkBatchSettings = EnvironmentSettings.newInstance()
                .useBlinkPlanner()
                .inBatchMode()
                .build();
        TableEnvironment blinkBatchTableEnv = TableEnvironment.create(blinkBatchSettings);

        //2. 表的创建：连接外部系统，读取数据
        String filePath = "F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\sensor.txt";
        tableEnv.connect(new FileSystem().path(filePath))
                .withFormat(new Csv())
                .withSchema(new Schema()
                        .field("id", DataTypes.STRING())
                        .field("timestamp",DataTypes.BIGINT())
                        .field("temp", DataTypes.DOUBLE())
                )
                .createTemporaryTable("inputTable");
        Table inputTable = tableEnv.from("inputTable");

        //3. 查询转换
        //3.1 Table api
        //简单转换
        Table resultTable = inputTable.select("id, temp")
                .filter("id === 'sensor_6'");

        //聚合统计
        Table aggTable = inputTable.groupBy("id")
                .select("id, id.count as count, temp.avg as avgTemp");

        //3.2 SQL
        tableEnv.sqlQuery("select id, temp from inputTable where id = 'sensor_6'");
        Table sqlAggTable = tableEnv.sqlQuery("select id, count(id) as cnt, avg(temp) as avgTemp from inputTable group by id");

        //打印输出
        tableEnv.toAppendStream(resultTable, Row.class).print("result");
        tableEnv.toRetractStream(aggTable, Row.class).print("agg");
        tableEnv.toRetractStream(sqlAggTable, Row.class).print("sqlagg");

        env.execute();
    }
}
