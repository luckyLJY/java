package com.itguigu.mapreduce_sort;/**
 * Project: Project0408
 * Package: com.itguigu.mapreduce_sort
 * Version: 1.0
 * Created by ljy on 2021-12-4 17:51
 */

import com.itguigu.mapreduce_sort.bean.PairWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassName SortMapper
 * @Author: ljy on 2021-12-4 17:51
 * @Version: 1.0
 * @Description:
 * 1. 继承Mapper
 *          k1 LongWritable
 *          v1  Text
 *          k2 ParirWriable
 *          v2 Text
 * 2.重写mapper方法
 *      1.切割v1的Text
 *      2.将切割的数据封装到PariWriable，作为k2
 *      3.k2,v2写入上下文
 */
public class SortMapper extends Mapper<LongWritable, Text, PairWritable, Text> {

    /**
     *
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     * 2.重写mapper方法
     *      1.切割v1的Text
     *      2.将切割的数据封装到PariWriable，作为k2
     *      3.k2,v2写入上下文
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] split = value.toString().split("\t");
        PairWritable pairWritable = new PairWritable();
        pairWritable.setFirst(split[0]);
        pairWritable.setSecond(Integer.parseInt(split[1]));

        context.write(pairWritable,value);
    }
}
