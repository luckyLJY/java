package com.macro.mall.tiny.work;/**
 * Project: mall-tiny-rabbit
 * Package: com.macro.mall.tiny.work
 * Version: 1.0
 * Created by ljy on 2022-5-5 15:35
 */

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName WorkRabbitConfig
 * @Author: ljy on 2022-5-5 15:35
 * @Version: 1.0
 * @Description:
 */
@Configuration
public class WorkRabbitConfig {

    @Bean
    public Queue workQueue(){
        return new Queue("work.hello");
    }

    @Bean
    public WorkReceiver workReceiver1(){
        return new WorkReceiver(1);
    }

    @Bean
    public WorkReceiver workReceiver2(){
        return new WorkReceiver(2);
    }

    @Bean
    public WorkSender workSender(){
        return new WorkSender();
    }
}
