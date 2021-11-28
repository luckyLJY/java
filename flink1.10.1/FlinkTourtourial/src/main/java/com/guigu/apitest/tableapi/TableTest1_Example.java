package com.guigu.apitest.tableapi;/**
 * Project: FlinkTourtourial
 * Package: com.guigu.apitest.tableapi
 * Version: 1.0
 * Created by ljy on 2021-10-24 13:09
 */

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

import javax.xml.crypto.Data;

/**
 * @ClassName TableTest1_Example
 * @Author: ljy on 2021-10-24 13:09
 * @Version: 1.0
 * @Description
 */
public class TableTest1_Example {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //1.读取数据
        DataStream<String> inputStream = env.readTextFile("F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\sensor.txt");

        //2.转换成POJO
        DataStream<SensorReading> dataStream = inputStream.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String s) throws Exception {
                String[] fields = s.split(",");
                return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
            }
        });

        //3. 创建表环境
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        //4. 基于流创建一张表
        Table dataTable = tableEnv.fromDataStream(dataStream);

        //5.1 基于table API进行操作
        Table resultTable = dataTable.select("id, temperature")
                .where("id = 'sensor_1'");

        //5.2 执行sql 操作
        tableEnv.createTemporaryView("sensor", dataTable);
        String sql = "select id, temperature from sensor where id = 'sensor_1'";
        Table resultSqlTable = tableEnv.sqlQuery(sql);

        //6. 输出结果
        tableEnv.toAppendStream(resultTable, Row.class).print("table api");
        tableEnv.toAppendStream(resultSqlTable,Row.class).print("sql api");

        env.execute();
    }
}
