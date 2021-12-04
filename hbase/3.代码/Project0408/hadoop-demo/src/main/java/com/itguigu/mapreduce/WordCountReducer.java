package com.itguigu.mapreduce;/**
 * Project: Project0408
 * Package: com.itguigu.mapreduce
 * Version: 1.0
 * Created by ljy on 2021-11-18 15:51
 */


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @ClassName WordCountReducer
 * @Author: ljy on 2021-11-18 15:51
 * @Version: 1.0
 * @Description: 单词统计reduce操作
 * KEYIN K2 Text 每个单词
 * VALUEIN V2 LongWritable 集合中的泛型类型
 * KEYOUT K3  Text 每个单词
 * VALUEOUT V3 LongWritable 每个单词出现的次数
 */
public class WordCountReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

    /**
     * 自定义reduce逻辑
     * 所有的key都是我们的单词，所有的values都是我们单词出现的次数
     * @param key k2
     * @param values 集合
     * @param context 上下文对象
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        long count = 0;
        for(LongWritable value :values){
            count += value.get();
        }
        context.write(key,new LongWritable(count));
    }
}
