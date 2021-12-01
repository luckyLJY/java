package com.guigu.apitest.state;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.contrib.streaming.state.RocksDBStateBackend;
import org.apache.flink.runtime.executiongraph.restart.RestartStrategy;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.runtime.state.memory.MemoryStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author ljy
 * @date 2021-10-13 17:09
 * @description 状态管理
 * 完善：2021-10-17
 *
 *
 */


/**
 * checkpoint的基本属性
 */
/*

StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//env.getConfig().disableSysoutLogging();
//每 30 秒触发一次 checkpoint，checkpoint 时间应该远小于（该值 + MinPauseBetweenCheckpoints），否则程序会一直做checkpoint，影响数据处理速度
env.enableCheckpointing(30000); // create a checkpoint every 30 seconds

// set mode to exactly-once (this is the default)
// flink 框架内保证 EXACTLY_ONCE
env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);

// make sure 30 s of progress happen between checkpoints
// 两个 checkpoints之间最少有 30s 间隔（上一个checkpoint完成到下一个checkpoint开始，默认为0，这里建议设置为非0值）
env.getCheckpointConfig().setMinPauseBetweenCheckpoints(30000);

// checkpoints have to complete within one minute, or are discarded
// checkpoint 超时时间（默认 60 s）
env.getCheckpointConfig().setCheckpointTimeout(600000);

// allow only one checkpoint to be in progress at the same time
// 同时只有一个checkpoint运行（默认）
env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);

// enable externalized checkpoints which are retained after job cancellation
// 取消作业时是否保留 checkpoint (默认不保留)
env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);

// checkpoint失败时 task 是否失败( 默认 true， checkpoint失败时，task会失败)
env.getCheckpointConfig().setFailOnCheckpointingErrors(true);

// 对 FsStateBackend 刷出去的文件进行文件压缩，减小 checkpoint 体积
env.getConfig().setUseSnapshotCompression(true);
*/


public class StateTest4_FaultTolerance {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //1.状态后端配置 可以在配置文件中配置
        env.setStateBackend(new MemoryStateBackend());
        env.setStateBackend(new FsStateBackend(""));
        //env.setStateBackend(new FsStateBackend("hdfs:///namenode/flink/checkpoints"));
        
		//env.setStateBackend(new RocksDBStateBackend("hdfs:///namenode/flink/checkpoints",ture));存储到rockDB中
		env.setStateBackend(new RocksDBStateBackend(""));

        //2.检查点设置参数：时间、模式：精确一次，至少一次
        env.enableCheckpointing(300);

        //高级选项
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);//检查点模式
        //超时时间
        env.getCheckpointConfig().setCheckpointTimeout(60000);
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(2);//最大同时的检查点,前一个检查点未完成，后面的检查点到来
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(100L);//前一次检查点保存完成，到下一个检查点触发的时间；与上一个配置有关
        env.getCheckpointConfig().setPreferCheckpointForRecovery(true);//使用检查点作为恢复点true
        env.getCheckpointConfig().setTolerableCheckpointFailureNumber(0);//检查失败容忍次数

        //3.重启策略配置
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(3,10000));//固定延迟重启
        //失败率重启:10分钟重启3次，每次间隔1分钟
        env.setRestartStrategy(RestartStrategies.failureRateRestart(3, Time.minutes(10),Time.minutes(1) ));



        env.execute();
    }
}
