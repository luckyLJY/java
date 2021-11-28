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
 */
public class StateTest4_FaultTolerance {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //1.状态后端配置 可以在配置文件中配置
        env.setStateBackend(new MemoryStateBackend());
        env.setStateBackend(new FsStateBackend(""));
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
