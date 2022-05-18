package com.macro.mall.tiny.work;/**
 * Project: mall-tiny-rabbit
 * Package: com.macro.mall.tiny.work
 * Version: 1.0
 * Created by ljy on 2022-5-5 15:13
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName WorkSender
 * @Author: ljy on 2022-5-5 15:13
 * @Version: 1.0
 * @Description:
 */
public class WorkSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkSender.class);

    @Autowired
    private RabbitTemplate template;

    private static final String queueName = "work.hello";

    public   void send(int index){
        StringBuilder builder = new StringBuilder("Hello");
        int limitIndex = index % 3 +1;
        for (int i=0;i<limitIndex;i++){
            builder.append('.');
        }
        builder.append(index+1);
        String message = builder.toString();
        template.convertAndSend(queueName,message);
        LOGGER.info(" [x] Sent '{}'",message);
    }
}
