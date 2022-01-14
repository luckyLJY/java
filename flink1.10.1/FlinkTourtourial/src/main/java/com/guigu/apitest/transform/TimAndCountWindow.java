package com.guigu.apitest.transform;/**
 * Project: FlinkTourtourial
 * Package: com.guigu.apitest.transform
 * Version: 1.0
 * Created by ljy on 2021-12-9 8:26
 */

import akka.stream.impl.ReducerState;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.state.ReducingState;
import org.apache.flink.api.common.state.ReducingStateDescriptor;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.util.stream.Stream;

/**
 * @ClassName TimeAndCountWindow
 * @Author: ljy on 2021-12-9 8:26
 * @Version: 1.0
 * @Description:时间计数窗口
 */
public class TimAndCountWindow {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //默认为事件处理事件
        //env.setStreamTimeCharacteristic();
        /**
         * 1.DataStream inputStream = env.addSource();
         * 2.DataStream afterMapStream = inputStream.map();
         * 3.afterMapStream.timeWindowAll(Time.seconds(windowTime))
         *      .trigger(new TimeTriger[TimeWindow](batchcount))
         *      .apply();
         */
        env.execute();
    }
}

/**
 * 知识点
 * OnElement ：每个数据进入窗口都会触发。
 * OnEventTime ：根据接入窗口的EventTime进行触发操做
 * OnProcessTime ： 根据接入窗口的ProcessTime进行触发操做
 * Clear ： 执行窗口及状态数据的清除方法。
 *
 * 窗口触发方法返回结果的类型：
 * CONTINUE ： 不进行操做，等待。
 * FIRE ： 触发计算且数据保留。
 * PRUGE ： 窗口内部数据清除且不触发计算。
 * FIRE_AND_PURGE : 触发计算并清除对应的数据
 */
class TimeTriger extends Trigger<Long, TimeWindow>{

    //创建
    private ReducingState<Long> countState;
    private ReducingStateDescriptor<Long> reducingStateDescriptor = new ReducingStateDescriptor<Long>("count", new Sum(),Long.class);


    @Override
    public TriggerResult onElement(Long maxcount, long timestamp, TimeWindow window, TriggerContext ctx) throws Exception {
        countState = ctx.getPartitionedState(reducingStateDescriptor) ;
         countState.add(1L);
         if (countState.get() >= maxcount || timestamp >= window.getEnd()){
             clear(window,ctx);
             return TriggerResult.FIRE_AND_PURGE;//触发计算并清除数据
         }else {
             return TriggerResult.CONTINUE;//不进行操作，等待
         }
    }

    
   /* @Override
    public TriggerResult onProcessingTime(long time, TimeWindow window, TriggerContext ctx) throws Exception {
        if(time >= window.getEnd()){
           return TriggerResult.CONTINUE;
        }else {
            clear(window,ctx);
            return TriggerResult.FIRE_AND_PURGE;//触发计算并清除数据
        }
    }

    @Override
    public TriggerResult onEventTime(long time, TimeWindow window, TriggerContext ctx) throws Exception {
        if(time >= window.getEnd()){
            return TriggerResult.CONTINUE;
        }else {
            clear(window,ctx);
            return TriggerResult.FIRE_AND_PURGE;//触发计算并清除数据
        }
    }*/

    @Override
    public void clear(TimeWindow window, TriggerContext ctx) throws Exception {
        countState = ctx.getPartitionedState(reducingStateDescriptor) ;
        countState.clear();
        countState.add(0L);
    }
}

class Sum implements ReduceFunction<Long>{
    @Override
    public Long reduce(Long value1, Long value2) throws Exception {
        return value1+value2;
    }
}
