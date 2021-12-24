package com.guigu.apitest.state;/**
 * Project: FlinkTourtourial
 * Package: com.guigu.apitest.state
 * Version: 1.0
 * Created by ljy on 2021-12-24 14:47
 */

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @ClassName Fraud_Detection
 * @Author: ljy on 2021-12-24 14:47
 * @Version: 1.0
 * @Description:欺诈检测（官网案例）
 *  Transaction：交易类
 *  Alert：警报类
 *  小额交易1分钟类检测到大额交易告警
 */
public class Fraud_Detection {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Transaction> transactions = env
                .addSource(new TransactionSource())
                .name("transactions");

        DataStream<Alert> alerts = transactions
                .keyBy(Transaction::getAccountId)
                .process(new FraudDetector())
                .name("fraud-detector");

        alerts
                .addSink(new AlertSink())
                .name("send-alerts");

        env.execute("Fraud Detection");
    }

}
