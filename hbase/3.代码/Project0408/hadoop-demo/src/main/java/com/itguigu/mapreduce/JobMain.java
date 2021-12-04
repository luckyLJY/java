package com.itguigu.mapreduce;/**
 * Project: Project0408
 * Package: com.itguigu.mapreduce
 * Version: 1.0
 * Created by ljy on 2021-11-18 15:59
 */


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @ClassName JobMain
 * @Author: ljy on 2021-11-18 15:59
 * @Version: 1.0
 * @Description: 单词统计主程序
 */
public class JobMain extends Configured implements Tool {
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(super.getConf(), JobMain.class.getSimpleName());
        //打包到集群上面运行时间，必须要添加以下配置，指定程序的main函数
        job.setJarByClass(JobMain.class);
        //1. 读入输入文件解析成key，value对
        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job,new Path("hdfs://node01:8020/wordcount"));

        //2. 设置我们的mapper类
        job.setMapperClass(WordCountMapper.class);
        //设置我们的map阶段完成之后的输出类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        //第三步，第四步，第五步，第六步，省略:分区、排序、规约、分组

        //设置分区类
        job.setPartitionerClass(MyPartitioner.class);

        //设置reduce的个数
        job.setNumReduceTasks(2);

        //第七步：设置我们的reduce类
        job.setReducerClass(WordCountReducer.class);
        //设置我们reduce阶段完成之后的输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        //第八步：设置输出类以及输出路径
        job.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job,
                new Path("hdfs://node01:8020/wordcount_out"));
        boolean b = job.waitForCompletion(true);
        return b?0:1;

    }

    public static void main(String[] args) throws Exception{
        Configuration configuration = new Configuration();
        Tool tool = new JobMain();

        int run = ToolRunner.run(configuration, tool, args);
        System.exit(run);
    }
}
