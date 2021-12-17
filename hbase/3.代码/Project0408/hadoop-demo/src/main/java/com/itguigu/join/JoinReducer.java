package com.itguigu.join;/**
 * Project: Project0408
 * Package: com.itguigu.join
 * Version: 1.0
 * Created by ljy on 2021-12-16 20:53
 */

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @ClassName JoinReducer
 * @Author: ljy on 2021-12-16 20:53
 * @Version: 1.0
 * @Description:jion的reducer
 */
public class JoinReducer extends Reducer<Text,Text,Text,Text> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        String first ="";
        String second ="";
        for (Text value : values) {
            if (value.toString().startsWith("p")){
                first = value.toString();
            }else {
                second = value.toString();
            }
        }
        if (first.equals("")){
            context.write(key,new Text("NULL"+"\t"+second));
        }else {
            context.write(key, new Text(first + "\t" + second));
        }
    }
}
