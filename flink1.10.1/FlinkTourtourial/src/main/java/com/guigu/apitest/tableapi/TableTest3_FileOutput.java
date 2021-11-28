package com.guigu.apitest.tableapi;/**
 * Project: FlinkTourtourial
 * Package: com.guigu.apitest.tableapi
 * Version: 1.0
 * Created by ljy on 2021-10-24 21:11
 */

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.descriptors.Csv;
import org.apache.flink.table.descriptors.FileSystem;
import org.apache.flink.table.descriptors.Schema;

/**
 * @ClassName TableTest3_FileOutput
 * @Author: ljy on 2021-10-24 21:11
 * @Version: 1.0
 * @Description
 */
public class TableTest3_FileOutput {
    public static void main(String[] args) throws Exception {
        //1. 创建环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

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

        //聚合统计 无法写入文件sink
        Table aggTable = inputTable.groupBy("id")
                .select("id, id.count as count, temp.avg as avgTemp");

        //3.2 SQL 不支持写入文件的sink
        tableEnv.sqlQuery("select id, temp from inputTable where id = 'sensor_6'");
        Table sqlAggTable = tableEnv.sqlQuery("select id, count(id) as cnt, avg(temp) as avgTemp from inputTable group by id");

        //4. 输出到文件注册输出表
        String outputPath = "F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\out.txt";
        tableEnv.connect(new FileSystem().path(outputPath))
                .withFormat(new Csv())
                .withSchema(new Schema()
                        .field("id", DataTypes.STRING())
                        .field("cnt", DataTypes.BIGINT())
                        .field("temperature",DataTypes.DOUBLE())
                )
                .createTemporaryTable("outputTable");

        //resultTable.insertInto("outputTable");
        aggTable.insertInto("outputTable");

        env.execute();
    }
}
