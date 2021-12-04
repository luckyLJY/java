package com.itguigu.mapreduce_sort;/**
 * Project: Project0408
 * Package: com.itguigu.mapreduce_sort
 * Version: 1.0
 * Created by ljy on 2021-12-4 18:23
 */

import com.itguigu.mapreduce_sort.bean.PairWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @ClassName JobMain
 * @Author: ljy on 2021-12-4 18:23
 * @Version: 1.0
 * @Description:
 * 1.继承Configured实现Tool
 * 2.编写main方法
 *      启动任务：ToolRunner.run
 *      退出System.exit
 * 3.实现方法：run
 *      1.创建任务对象Job.getInstance
 *      2.打包放在集群运行时，需要一个配置job.setJarByClass
 *      3.读取文件的类及位置：job.setInputFormClass\ TextInputFormat
 *      4.设置mapper
 *      5.设置k2,v3的类型
 *      6.设置reducer
 *      7.设置k3,v3
 *      8.设置输出类
 *      9、设置输出路径
 *      10、任务成功结束
 */
public class JobMain extends Configured implements Tool {

    /**
     *
     * @param strings
     * @return
     * @throws Exception
     */
    public int run(String[] strings) throws Exception {

        /**
         * 3.实现方法：run
         *      1.创建任务对象Job.getInstance
         *      2.打包放在集群运行时，需要一个配置job.setJarByClass
         *      3.读取文件的类及位置：job.setInputFormClass\ TextInputFormat
         *      4.设置mapper
         *      5.设置k2,v2的类型
         *      6.设置reducer
         *      7.设置k3,v3
         *      8.设置输出类
         *      9、设置输出路径
         *      10、任务成功结束
         */
        Job job = Job.getInstance(super.getConf(),JobMain.class.getSimpleName());

        job.setJarByClass(com.itguigu.mapreduce_sort.JobMain.class);

        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job, new Path("hdfs://node01:8020/input/sort"));

        job.setMapperClass(SortMapper.class);
        job.setMapOutputKeyClass(PairWritable.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(SortReducer.class);
        job.setOutputKeyClass(PairWritable.class);
        job.setOutputKeyClass(NullWritable.class);

        job.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job,new Path("hdfs://node01:8020/out/sort"));

        boolean b = job.waitForCompletion(true);
        return b?0:1;
    }

    public static void main(String[] args) throws  Exception{
        Configuration configuration = new Configuration();
        ToolRunner.run(configuration,new JobMain(),args);
    }
}
