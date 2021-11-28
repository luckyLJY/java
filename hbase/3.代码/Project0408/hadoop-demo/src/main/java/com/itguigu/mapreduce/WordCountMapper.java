package com.itguigu.mapreduce;/**
 * Project: Project0408
 * Package: com.itguigu.mapreduce
 * Version: 1.0
 * Created by ljy on 2021-11-18 15:00
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @ClassName WordCountMapper
 * @Author: ljy on 2021-11-18 15:00
 * @Version: 1.0
 * @Description: 单词个数的map操作
 */
public class WordCountMapper extends Mapper<LongWritable,Text, Text,LongWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] split = line.split(",");
        for(String word:split){
            context.write(new Text(word),new LongWritable(1));
        }
    }
}
