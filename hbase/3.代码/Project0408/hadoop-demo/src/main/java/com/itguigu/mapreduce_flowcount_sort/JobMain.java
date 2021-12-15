package com.itguigu.mapreduce_flowcount_sort;/**
 * Project: Project0408
 * Package: com.itguigu.mapreduce_flowcount_sort
 * Version: 1.0
 * Created by ljy on 2021-12-15 20:31
 */


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


/**
 * @ClassName JobMain
 * @Author: ljy on 2021-12-15 20:31
 * @Version: 1.0
 * @Description:主程序
 */
public class JobMain extends Configured implements Tool {



        public int run(String[] strings) throws Exception {
//创建一个任务对象
            Job job = Job.getInstance(super.getConf(),
                    "mapreduce_flowcountsort");
//打包放在集群运行时，需要做一个配置
            job.setJarByClass(JobMain.class);
//第一步:设置读取文件的类: K1 和V1
            job.setInputFormatClass(TextInputFormat.class);
            TextInputFormat.addInputPath(job, new
                    Path("hdfs://node01:8020/out/flowcount_out"));
//第二步：设置Mapper类
            job.setMapperClass(FlowCountSortMapper.class);
//设置Map阶段的输出类型: k2 和V2的类型
            job.setMapOutputKeyClass(FlowBean.class);
            job.setMapOutputValueClass(Text.class);
//第三,四，五，六步采用默认方式(分区，排序，规约，分组)
//第七步 ：设置文的Reducer类
            job.setReducerClass(FlowCountSortReducer.class);
//设置Reduce阶段的输出类型
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(FlowBean.class);
//设置Reduce的个数
//第八步:设置输出类
            job.setOutputFormatClass(TextOutputFormat.class);
//设置输出的路径
            TextOutputFormat.setOutputPath(job, new
                    Path("hdfs://node01:8020/out/flowcountsort_out"));
            boolean b = job.waitForCompletion(true);
            return b ? 0 : 1;

        }

        public static void main(String[] args) throws Exception {
            Configuration configuration = new Configuration();
//启动一个任务
            int run = ToolRunner.run(configuration, new JobMain(), args);
            System.exit(run);
        }

}
