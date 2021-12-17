package com.itguigu.join;/**
 * Project: Project0408
 * Package: com.itguigu.join
 * Version: 1.0
 * Created by ljy on 2021-12-16 20:51
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @ClassName JoinMapper
 * @Author: ljy on 2021-12-16 20:51
 * @Version: 1.0
 * @Description:join操作mapper
 */
public class JoinMapper extends Mapper<LongWritable, Text,Text,Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

         //1. 判断数据来自那个文件
        FileSplit fileSplit = (FileSplit) context.getInputSplit();

        String filename = fileSplit.getPath().getName();

        if (filename.equals("orders.txt")){
            String[] strings = value.toString().split(",");
            context.write(new Text(strings[2]),value);
        }else{
            //获取pid
            String[] split = value.toString().split(",");
            context.write(new Text(split[0]),value);
        }

    }
}
