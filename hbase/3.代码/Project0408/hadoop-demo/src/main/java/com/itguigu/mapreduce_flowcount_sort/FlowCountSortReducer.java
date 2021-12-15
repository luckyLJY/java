package com.itguigu.mapreduce_flowcount_sort;/**
 * Project: Project0408
 * Package: com.itguigu.mapreduce_flowcount_sort
 * Version: 1.0
 * Created by ljy on 2021-12-15 20:28
 */

import org.apache.hadoop.mapreduce.Reducer;

import org.apache.hadoop.io.Text;
import java.io.IOException;

/**
 * @ClassName FlowCountSortReducer
 * @Author: ljy on 2021-12-15 20:28
 * @Version: 1.0
 * @Description:
 */
public class FlowCountSortReducer extends Reducer<FlowBean,Text,Text,FlowBean> {
    @Override
    protected void reduce(FlowBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        for (Text value : values) {
            context.write(value,key);
        }
    }
}
