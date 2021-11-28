package com.guigu.apitest.sink;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

public class SinkTest4_Mysql {
    public static void main(String[] args)throws Exception {


        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //从文件读取数据
        DataStream<String> inputStream = env.readTextFile("F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\sensor.txt");

        //转换成SensorReading类型
        DataStream<SensorReading > dataStream = inputStream.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String value) throws Exception {
                String[] fields = value.split(",");
                return new SensorReading(fields[0],new Long(fields[1]),new Double(fields[2]));
            }
        });

        //
        dataStream.addSink(new MyJdbcSink());

        env.execute();
    }

    public static  class  MyJdbcSink extends RichSinkFunction<SensorReading> {

        //声明连接和预编译语句
        Connection connection = null;
        PreparedStatement insertStmt = null;
        PreparedStatement updateStmt = null;

        @Override
        public void open(Configuration parameters) throws Exception {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test","root","123456");
            insertStmt = connection.prepareStatement("insert into sensor_temp(ID, temp) values (?,?)");
            updateStmt = connection.prepareStatement("update sensor_temp set temp =? where ID =?");
        }

        //每来一条数据，调用连接，执行sql
        @Override
        public void invoke(SensorReading value, Context context) throws Exception {
            //直接执行更新语句，如果没有更新那么就插入
            updateStmt.setDouble(1,value.getTemperature());
            updateStmt.setString(2,value.getId());
            updateStmt.execute();

            if(updateStmt.getUpdateCount() == 0){
                insertStmt.setString(1,value.getId());
                insertStmt.setDouble(2,value.getTemperature());
                insertStmt.execute();
            }
        }

        @Override
        public void close() throws Exception {
            insertStmt.close();
            updateStmt.close();
            connection.close();
        }
    }
}
