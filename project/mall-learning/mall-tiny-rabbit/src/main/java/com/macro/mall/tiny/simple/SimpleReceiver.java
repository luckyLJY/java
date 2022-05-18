package com.macro.mall.tiny.simple;/**
 * Project: mall-tiny-rabbit
 * Package: com.macro.mall.tiny.simple
 * Version: 1.0
 * Created by ljy on 2022-5-5 14:42
 */

import com.mysql.cj.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName SimpleReceiver
 * @Author: ljy on 2022-5-5 14:42
 * @Version: 1.0
 * @Description:
 */
@RabbitListener(queues = "simple.hello")
public class SimpleReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleReceiver.class);

    @RabbitHandler
    public void receive(String in) {
        LOGGER.info(" [x] Received '{}'", in);
    }

}

