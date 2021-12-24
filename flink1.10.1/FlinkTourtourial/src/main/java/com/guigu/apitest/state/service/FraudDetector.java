package com.guigu.apitest.state.service;
/**
 * Project: FlinkTourtourial
 * Package: com.guigu.apitest.state.service
 * Version: 1.0
 * Created by ljy on 2021-12-24 14:51
 */

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.hadoop.conf.Configuration;

/**
 * @ClassName FraudDetector
 * @Author: ljy on 2021-12-24 14:51
 * @Version: 1.0
 * @Description:欺诈检测器
 */
public class FraudDetector extends KeyedProcessFunction<Long, Transaction, Alert> {
    private static final long serialVersionUID = 1L;

    //设置小额
    private static final double SMALL_AMOUNT = 1.00;
    //设置大额
    private static final double LARGE_AMOUNT = 500.00;
    //定时时长
    private static final long ONE_MINUTE = 60 * 1000;
    //遇到小额设置转态为true
    private transient ValueState<Boolean> flagState;
    //定时器状态
    private transient ValueState<Long> timerState;

    @Override
    public void open(Configuration parameters) {
        ValueStateDescriptor<Boolean> flagDescriptor = new ValueStateDescriptor<>(
                "flag",
                Types.BOOLEAN);
        flagState = getRuntimeContext().getState(flagDescriptor);

        ValueStateDescriptor<Long> timerDescriptor = new ValueStateDescriptor<>(
                "timer-state",
                Types.LONG);
        timerState = getRuntimeContext().getState(timerDescriptor);

    }


    @Override
    public void processElement(
            Transaction transaction,
            Context context,
            Collector<Alert> collector) throws Exception {
        //获取当前最后一笔最小交易状态
        Boolean lastTransactionWasSmall = flagState.value();

        /**
         * 1.lastTransactionWasSmall不为null
         *  1.1交易金额>大额值
         *      1.1.1产生告警
         *      1.2.2清空小额状态
         * 2.交易额<小额值
         *    2.1小额状态值为true
         *    2.2设置定时状态
         */

        if (lastTransactionWasSmall !=null){
            if (transaction.getAmount() > LARGE_AMOUNT){
                Alert alert = new Alert();
                //添加警告
                alert.setId(transaction.getAccountId());

                //收集警告
                collector.collect(alert);
            }
            flagState.clear();
        }
        if (transaction.getAmount() <SMALL_AMOUNT){
            flagState.update(true);

            long timer = context.timerService().currentProcessingTime() + ONE_MINUTE;
            context.timerService().registerProcessingTimeTimer(timer);
            timerState.update(timer);
        }
    }

    public void onTimer(long timestamp, KeyedProcessFunction.OnTimerContext ctx, Collector<Alert> out) {


        // remove flag after 1 minute
        timerState.clear();
        flagState.clear();
    }

    private void cleanUp(Context ctx) throws Exception {
        // delete timer
        Long timer = timerState.value();
        ctx.timerService().deleteProcessingTimeTimer(timer);

        // clean up all state
        timerState.clear();
        flagState.clear();
    }
}
