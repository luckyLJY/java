package com.itguigu.mapreduce;/**
 * Project: Project0408
 * Package: com.itguigu.mapreduce
 * Version: 1.0
 * Created by ljy on 2021-12-4 16:02
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Partitioner;

import org.apache.hadoop.io.Text;

/**
 * @ClassName MyPartitioner
 * @Author: ljy on 2021-12-4 16:02
 * @Version: 1.0
 * @Description:
 *  1. 继承Partitioner
 *  2. 指定k2 v3类型：Text,LongWriteable
 *  3. 重新getPatition
 *      text: k2
 *      longWritable: v2
 *      i : reduce个数
 *  需求：
 *      单词长度》=5进入第一个分区-->的第一个reduceTask，reduce编号为0
 *      单词长度《5进入第二个分区-->的第二个reduceTask，reduce编号为1
 */
public class MyPartitioner extends Partitioner<Text, LongWritable> {
    @Override
    public int getPartition(Text text, LongWritable  longWritable, int i) {
        if(text.toString().length() >= 5){
            return 0;
        }else {
            return 1;
        }
    }
}
