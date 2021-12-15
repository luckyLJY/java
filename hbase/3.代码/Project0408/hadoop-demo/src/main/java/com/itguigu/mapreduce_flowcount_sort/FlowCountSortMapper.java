package com.itguigu.mapreduce_flowcount_sort;/**
 * Project: Project0408
 * Package: com.itguigu.mapreduce_flowcount_sort
 * Version: 1.0
 * Created by ljy on 2021-12-15 19:58
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @ClassName FlowCountSortMapper
 * @Author: ljy on 2021-12-15 19:58
 * @Version: 1.0
 * @Description:排序
 */
public class FlowCountSortMapper extends Mapper<LongWritable,Text,FlowBean, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        FlowBean flowBean = new FlowBean();
        String[] split = value.toString().split("\t");
        //获取手机号为v2
        String phoneNum = split[0];

        //获取其他流量字段k2
        flowBean.setUpFlow(Integer.parseInt(split[1]));
        flowBean.setUpCountFlow(Integer.parseInt(split[2]));
        flowBean.setDownFlow(Integer.parseInt(split[3]));
        flowBean.setDownCountFlow(Integer.parseInt(split[4]));

        context.write(flowBean,new Text(phoneNum));
    }
}
