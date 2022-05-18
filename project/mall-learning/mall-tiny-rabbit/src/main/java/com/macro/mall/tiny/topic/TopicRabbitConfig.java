package com.macro.mall.tiny.topic;/**
 * Project: mall-tiny-rabbit
 * Package: com.macro.mall.tiny.topic
 * Version: 1.0
 * Created by ljy on 2022-5-6 14:45
 */

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName TopicRabbitConfig
 * @Author: ljy on 2022-5-6 14:45
 * @Version: 1.0
 * @Description:
 */
@Configuration
public class TopicRabbitConfig {

    @Bean
    public TopicExchange topic(){
        return new TopicExchange("exchange.topic");
    }

    @Bean
    public Queue topicQueue1(){
        return new AnonymousQueue();
    }

    @Bean
    public Queue topicQueue2(){
        return new AnonymousQueue();
    }

    @Bean
    public Binding topicBinding1a(TopicExchange topic,Queue topicQueue1){
        return BindingBuilder.bind(topicQueue1).to(topic).with("*.range.*");
    }

    @Bean
    public Binding topicBinding1b(TopicExchange topic,Queue topicQueue1){
        return BindingBuilder.bind(topicQueue1).to(topic).with("*.*.rabbit");
    }

    @Bean
    public Binding topicBinding2a(TopicExchange topic,Queue topicQueue2){
        return BindingBuilder.bind(topicQueue2).to(topic).with("lazy.#");
    }

    @Bean
    public TopicReceiver topicReceiver(){
        return new TopicReceiver();
    }

    @Bean
    public TopicSender topicSender(){
        return new TopicSender();
    }
}
