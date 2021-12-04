package com.itguigu.mapreduce_sort;/**
 * Project: Project0408
 * Package: com.itguigu.mapreduce_sort
 * Version: 1.0
 * Created by ljy on 2021-12-4 17:51
 */

import com.itguigu.mapreduce_sort.bean.PairWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import javax.xml.soap.Text;
import java.io.IOException;

/**
 * @ClassName SortReducer
 * @Author: ljy on 2021-12-4 17:51
 * @Version: 1.0
 * @Description:
 * 1.继承reducer
 *  k2 PairWriable
 *  v2 Text
 *  k3 PairWriable
 *  v3 Nullwriteable
 * 2. 重写reduce
 *  将k2,v2写入k3,v3
 *  为了输出相同key，需要遍历v2
 */
public class SortReducer extends Reducer<PairWritable, Text,PairWritable, NullWritable> {

    /**
     *
     * @param key
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     * 2. 重写reduce
     *  将k2,v2写入k3,v3
     *  为了输出相同key，需要遍历v2
     */
    @Override
    protected void reduce(PairWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for (Text value : values) {
            context.write(key,NullWritable.get());
        }
    }
}
