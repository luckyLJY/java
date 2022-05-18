package com.macro.mall.tiny.direct;/**
 * Project: mall-tiny-rabbit
 * Package: com.macro.mall.tiny.direct
 * Version: 1.0
 * Created by ljy on 2022-5-6 8:47
 */

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName DirectRabbitConfig
 * @Author: ljy on 2022-5-6 8:47
 * @Version: 1.0
 * @Description:
 */
@Configuration
public class DirectRabbitConfig {

    @Bean
    public DirectExchange direct(){
        return new DirectExchange("exchange.direct");
    }

    @Bean
    public Queue directQueue1(){
        return new AnonymousQueue();
    }

    @Bean
    public Queue directQueue2(){
        return new AnonymousQueue();
    }

    @Bean
    public Binding directBinding1a(DirectExchange direct, Queue directQueue1){
        return BindingBuilder.bind(directQueue1).to(direct).with("orange");
    }

    @Bean
    public Binding directBinding1b(DirectExchange direct,Queue directQueue1){
        return BindingBuilder.bind(directQueue1).to(direct).with("black");
    }

    @Bean
    public Binding directBinding2a(DirectExchange direct,Queue directQueue2){
        return BindingBuilder.bind(directQueue2).to(direct).with("green");
    }

    @Bean
    public Binding directBinding2b(DirectExchange direct, Queue directQueue2){
        return BindingBuilder.bind(directQueue2).to(direct).with("black");
    }

    @Bean
    public DirectReceiver receiver(){
        return new DirectReceiver();
    }

    @Bean
    public DirectSender directSender(){
        return new DirectSender();
    }
}
