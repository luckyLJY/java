package com.itguigu.combiner.mapreduce;/**
 * Project: Project0408
 * Package: com.itguigu.combiner.mapreduce
 * Version: 1.0
 * Created by ljy on 2021-12-12 21:31
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @ClassName MyContiner
 * @Author: ljy on 2021-12-12 21:31
 * @Version: 1.0
 * @Description:MapReducer的规约
 */
public class MyContiner extends Reducer<Text, LongWritable, Text, LongWritable> {
    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        long count = 0;
        for(LongWritable value :values){
            count += value.get();
        }
        context.write(key,new LongWritable(count));
    }
}
