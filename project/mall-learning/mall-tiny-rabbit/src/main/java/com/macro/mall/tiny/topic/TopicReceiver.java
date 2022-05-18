package com.macro.mall.tiny.topic;/**
 * Project: mall-tiny-rabbit
 * Package: com.macro.mall.tiny.topic
 * Version: 1.0
 * Created by ljy on 2022-5-6 10:31
 */

import cn.hutool.core.thread.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

/**
 * @ClassName TopicReceiver
 * @Author: ljy on 2022-5-6 10:31
 * @Version: 1.0
 * @Description:
 */
public class TopicReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopicReceiver.class);

    @RabbitListener(queues = "#{topicQueue1.name}")
    public void receive1 (String in){
        receive(in,1);
    }

    @RabbitListener(queues = "#{topicQueue2.name}")
    public void receive2(String in){
        receive(in,2);
    }

    public void receive(String in,int receiver){
        StopWatch watch = new StopWatch();
        watch.start();
        LOGGER.info("instace {} [x] Received '{}'",receiver,in);
        doWork(in);
        watch.stop();
        LOGGER.info("instance {} [x] Done in {}s",receiver,watch.getTotalTimeSeconds());

    }

    private void doWork(String in){
        for (char ch : in.toCharArray()) {
            if (ch == '.'){
                ThreadUtil.sleep(1000);
            }
        }
    }
}
