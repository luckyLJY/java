package com.itguigu.mapreduce_flowcount_sort_partitioner;/**
 * Project: Project0408
 * Package: com.itguigu.mapreduce_flowcount
 * Version: 1.0
 * Created by ljy on 2021-12-14 20:50
 */

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @ClassName FlowCountReducer
 * @Author: ljy on 2021-12-14 20:50
 * @Version: 1.0
 * @Description:流量统计reduce操作
 */
public class FlowCountReducer extends Reducer<Text, FlowBean,Text, FlowBean> {



    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        FlowBean flowBean = new FlowBean();

        Integer upFlow = 0;
        Integer downFlow = 0;
        Integer upCountFlow = 0;
        Integer downCountFlow = 0;

        for (FlowBean value : values) {
            upFlow += value.getUpFlow();
            downFlow += value.getDownFlow();
            upCountFlow += value.getUpCountFlow();
            downCountFlow += value.getDownCountFlow();
        }

        flowBean.setUpFlow(upFlow);
        flowBean.setDownFlow(downFlow);
        flowBean.setUpCountFlow(upCountFlow);
        flowBean.setDownCountFlow(downCountFlow);

        context.write(key,flowBean);
    }
}
