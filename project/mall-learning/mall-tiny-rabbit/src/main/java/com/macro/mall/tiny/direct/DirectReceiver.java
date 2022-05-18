package com.macro.mall.tiny.direct;/**
 * Project: mall-tiny-rabbit
 * Package: com.macro.mall.tiny.direct
 * Version: 1.0
 * Created by ljy on 2022-5-6 8:37
 */

import cn.hutool.core.thread.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

/**
 * @ClassName DirectReceiver
 * @Author: ljy on 2022-5-6 8:37
 * @Version: 1.0
 * @Description:
 */
public class DirectReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(DirectReceiver.class);

    @RabbitListener(queues = "#{directQueue1.name}")
    public void receive1(String in){
        receive(in,1);
    }

    @RabbitListener(queues = "#{directQueue2.name}")
    public void receive2(String in){
        receive(in,2);
    }

    private void receive(String in,int receive){
        StopWatch watch = new StopWatch();
        watch.start();
        LOGGER.info("instance {} [x] Received '{}'",receive,in);
        doWork(in);
        watch.stop();
        LOGGER.info("instance {} [x] Done in {}s",receive,watch.getTotalTimeSeconds());
    }

    private void doWork(String in){
        for (char ch : in.toCharArray()) {
            if (ch == '.'){
                ThreadUtil.sleep(1000);
            }
        }
    }
}
