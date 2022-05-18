package com.macro.mall.tiny.simple;/**
 * Project: mall-tiny-rabbit
 * Package: com.macro.mall.tiny.simple
 * Version: 1.0
 * Created by ljy on 2022-5-5 14:33
 */

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName SimpleRabbitConfig
 * @Author: ljy on 2022-5-5 14:33
 * @Version: 1.0
 * @Description:
 */
@Configuration
public class SimpleRabbitConfig {

    @Bean
    public Queue hello() {
        return new Queue("simple.hello");
    }

    @Bean
    public SimpleSender simpleSender(){
        return new SimpleSender();
    }

    @Bean
    public SimpleReceiver simpleReceiver(){
        return new SimpleReceiver();
    }

}
