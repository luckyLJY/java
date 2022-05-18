package com.macro.mall.tiny.simple;/**
 * Project: mall-tiny-rabbit
 * Package: com.macro.mall.tiny.simple
 * Version: 1.0
 * Created by ljy on 2022-5-5 14:38
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName SimpleSender
 * @Author: ljy on 2022-5-5 14:38
 * @Version: 1.0
 * @Description:
 */
public class SimpleSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleSender.class);

    @Autowired
    private RabbitTemplate template;

    private static final String queueName="simple.hello";

    public void send() {
        String message = "Hello World!";
        this.template.convertAndSend(queueName, message);
        LOGGER.info(" [x] Sent '{}'", message);
    }

}