package com.itguigu.mapreduce_flowcount_sort_partitioner;/**
 * Project: Project0408
 * Package: com.itguigu.mapreduce_flowcount_sort_partitioner
 * Version: 1.0
 * Created by ljy on 2021-12-15 21:18
 */

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;


/**
 * @ClassName FlowPartition
 * @Author: ljy on 2021-12-15 21:18
 * @Version: 1.0
 * @Description：分区
 */
public class FlowPartition extends Partitioner<Text,FlowBean> {
    @Override
    public int getPartition(Text text, FlowBean flowBean, int i) {
        String line = text.toString();
        if (line.startsWith("135")){
            return 0;
        }else if(line.startsWith("136")){
            return 1;
        }else if(line.startsWith("137")){
            return 2;
        }else{
            return 3;
        }
    }
}
