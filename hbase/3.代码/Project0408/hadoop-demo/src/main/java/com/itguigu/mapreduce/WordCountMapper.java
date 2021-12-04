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
 * knowledge point:
 *  Mapper接口：
 *      KEYIN: k1的类型    行偏移量 LongWriteable
 *      VALUEIN:v1的类型   一行的文本数据 TEXT
 *      KEYOUT：k1的类型    每个单词    TEXT
 *      VALUEOUT:v2的类型  固定值1    LongWriteable
 */
public class WordCountMapper extends Mapper<LongWritable,Text, Text,LongWritable> {
    /**
     * @param key k1
     * @param value v1
     * @param context 上下文对象
     * @throws IOException
     * @throws InterruptedException
     * @TODO:
     *  将k1 v1 转为k2 v2
     *  k1 v1 : 0       hello,world
     *  k2 v2 : hello   1
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] split = line.split(",");
        for(String word:split){
            context.write(new Text(word),new LongWritable(1));
        }
    }
}
