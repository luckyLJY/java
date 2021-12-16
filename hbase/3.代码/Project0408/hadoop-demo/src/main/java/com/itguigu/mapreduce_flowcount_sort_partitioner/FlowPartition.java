package com.itguigu.mapreduce_flowcount_sort_partitioner;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class FlowPartition extends Partitioner<Text,FlowBean> {
    @Override
    public int getPartition(Text text, FlowBean flowBean, int i) {
        //判断手机号以哪个数字开头然后返回不同的分区编号
        if(text.toString().startsWith("135")){
            return  0;
        }else  if(text.toString().startsWith("136")){
            return  1;
        }else  if(text.toString().startsWith("137")){
            return  2;
        }else{
            return  3;
        }
    }
}
