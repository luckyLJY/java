package com.macro.mall.tiny.component;/**
 * Project: mall-tiny-delay
 * Package: com.macro.mall.tiny.component
 * Version: 1.0
 * Created by ljy on 2022-4-27 8:32
 */

import com.macro.mall.tiny.dto.QueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName CancelOrderSender
 * @Author: ljy on 2022-4-27 8:32
 * @Version: 1.0
 * @Description:取消订单消息的发出者
 */
@Component
public class CancelOrderSender {
    private static Logger LOGGER  = LoggerFactory.getLogger(CancelOrderSender.class);
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(Long orderId,final long delayTimes){
        //给延迟队列发送消息
        amqpTemplate.convertAndSend(QueueEnum.QUEUE_ORDER_PLUGIN_CANCEL.getExchange(),QueueEnum.QUEUE_ORDER_PLUGIN_CANCEL.getRouteKey(),orderId,new MessagePostProcessor(){
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //给消息设置延迟毫秒值
                message.getMessageProperties().setHeader("x-delay",delayTimes);
                return message;
            }
        });
        LOGGER.info("send delay message orderId:{}",orderId);
    }
}