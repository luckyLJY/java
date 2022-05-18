package com.macro.mall.tiny.work;/**
 * Project: mall-tiny-rabbit
 * Package: com.macro.mall.tiny.work
 * Version: 1.0
 * Created by ljy on 2022-5-5 15:18
 */


import cn.hutool.core.thread.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;


/**
 * @ClassName WorkReceiver
 * @Author: ljy on 2022-5-5 15:18
 * @Version: 1.0
 * @Description:
 */
@RabbitListener(queues = "work.hello")
public class WorkReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkReceiver.class);

    private final int instance;

    public WorkReceiver(int i) {
        this.instance = i;
    }

    @RabbitHandler
    public void receive(String in){
        StopWatch watch = new StopWatch();
        watch.start();
        LOGGER.info("instance {} [x] Received '{}'",this.instance,in);
        doWork(in);
        watch.stop();
        LOGGER.info("instance {} [x] Done in {}s", this.instance, watch.getTotalTimeSeconds());
    }

    private void doWork(String in){
        for (char ch:in.toCharArray()){
            if (ch == '.'){
                ThreadUtil.sleep(1000);
            }
        }
    }
}
