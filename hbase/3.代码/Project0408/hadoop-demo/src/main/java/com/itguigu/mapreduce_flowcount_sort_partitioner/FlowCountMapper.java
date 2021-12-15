package com.itguigu.mapreduce_flowcount_sort_partitioner;/**
 * Project: Project0408
 * Package: com.itguigu.mapreduce_flowcount
 * Version: 1.0
 * Created by ljy on 2021-12-14 20:49
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @ClassName FlowCountMapper
 * @Author: ljy on 2021-12-14 20:49
 * @Version: 1.0
 * @Description:流量统计Mapper统计
 */
public class FlowCountMapper extends Mapper<LongWritable, Text,Text, FlowBean> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String[] split = value.toString().split("\t");
        //手机号k2
        String phonNum = split[1];
        //流量字段
        FlowBean flowBean = new FlowBean();
        flowBean.setUpFlow(Integer.parseInt(split[6]));
        flowBean.setDownFlow(Integer.parseInt(split[7]));
        flowBean.setUpCountFlow(Integer.parseInt(split[8]));
        flowBean.setDownCountFlow(Integer.parseInt(split[9]));

        //写入上下文v2
        context.write(new Text(phonNum),flowBean);
    }
}
